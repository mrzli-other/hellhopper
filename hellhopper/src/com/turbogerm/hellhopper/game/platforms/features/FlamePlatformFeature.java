package com.turbogerm.hellhopper.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorInterpolator;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.dataaccess.PlatformFeatureData;
import com.turbogerm.hellhopper.game.CollisionEffects;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class FlamePlatformFeature extends PlatformFeatureBase {
    
    private static final float RENDER_PRECEDENCE = 2.0f;
    private static final float CONTACT_PRECEDENCE = 2.0f;
    
    private static final float FIRE_FRAME_DURATION = 0.15f;
    private static final float FIRE_SPRITE_WIDTH = 2.0f;
    private static final float FIRE_SPRITE_HEIGHT = 0.6f;
    
    private static final Color FLAME_COLOR;
    private static final Color DORMANT_COLOR;
    
    private final Animation mFireAnimation;
    private float mFireAnimationTime = 0.0f;
    
    private final FlameStateMachine mFlameStateMachine;
    private boolean mIsFlameActive;
    private float mFirstCycleCountdown;
    
    private final ColorInterpolator mColorInterpolator;
    
    static {
        FLAME_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
        DORMANT_COLOR = new Color(FLAME_COLOR);
        DORMANT_COLOR.mul(0.4f);
        DORMANT_COLOR.a = 1.0f;
    }
    
    public FlamePlatformFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        mFirstCycleCountdown = Float.valueOf(
                featureData.getProperty(PlatformFeatureData.FLAME_CYCLE_OFFSET_PROPERTY));
        float flameDuration = Float.valueOf(
                featureData.getProperty(PlatformFeatureData.FLAME_FLAME_DURATION_PROPERTY));
        float dormantDuration = Float.valueOf(
                featureData.getProperty(PlatformFeatureData.FLAME_DORMANT_DURATION_PROPERTY));
        float transitionDuration = Float.valueOf(
                featureData.getProperty(PlatformFeatureData.FLAME_TRANSITION_DURATION_PROPERTY));
        
        mFlameStateMachine = new FlameStateMachine(flameDuration, dormantDuration, transitionDuration);
        mIsFlameActive = false;
        
        TextureAtlas platformsAtlas = assetManager.get(ResourceNames.PLATFORMS_ATLAS);
        Array<AtlasRegion> fireAtlasRegions = platformsAtlas.findRegions(ResourceNames.PLATFORM_FIRE_IMAGE_NAME);
        mFireAnimation = new Animation(FIRE_FRAME_DURATION, fireAtlasRegions, Animation.LOOP);
        mFireAnimationTime = 0.0f;
        
        mRenderPrecedence = RENDER_PRECEDENCE;
        mContactPrecendence = CONTACT_PRECEDENCE;
        
        mColorInterpolator = new ColorInterpolator();
    }
    
    @Override
    public void update(float delta) {
        if (mFirstCycleCountdown > 0.0f) {
            mFirstCycleCountdown -= delta;
            if (mFirstCycleCountdown >= 0.0f) {
                delta = 0.0f;
            } else {
                delta = -mFirstCycleCountdown;
                mFirstCycleCountdown = 0.0f;
            }
        }
        
        mFlameStateMachine.update(delta);
        
        if (mFlameStateMachine.getCurrentState() == FlameStateMachine.FLAME) {
            if (!mIsFlameActive) {
                mIsFlameActive = true;
                mFireAnimationTime = 0.0f;
            } else {
                mFireAnimationTime += delta;
            }
        } else {
            mIsFlameActive = false;
        }
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 platformPosition, Color color) {
        if (mIsFlameActive) {
            TextureRegion fireAnimationFrame = mFireAnimation.getKeyFrame(mFireAnimationTime);
            batch.draw(fireAnimationFrame,
                    platformPosition.x, platformPosition.y + PlatformData.PLATFORM_HEIGHT,
                    FIRE_SPRITE_WIDTH, FIRE_SPRITE_HEIGHT);
        }
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        Color color = modifier.spriteColor;
        float oldAplha = color.a;
        
        switch (mFlameStateMachine.getCurrentState()) {
            case FlameStateMachine.DORMANT:
                color.set(DORMANT_COLOR);
                break;
            
            case FlameStateMachine.TRANSITION1:
                color.set(mColorInterpolator.interpolateColor(
                        DORMANT_COLOR, FLAME_COLOR, mFlameStateMachine.getStateElapsedFraction()));
                break;
            
            case FlameStateMachine.FLAME:
                color.set(FLAME_COLOR);
                break;
            
            case FlameStateMachine.TRANSITION2:
                color.set(mColorInterpolator.interpolateColor(
                        FLAME_COLOR, DORMANT_COLOR, mFlameStateMachine.getStateElapsedFraction()));
                break;
        }
        
        color.a = oldAplha;
    }
    
    @Override
    public boolean isContact(float relativeCollisionPointX) {
        return mIsFlameActive;
    }
    
    @Override
    public void applyContact(CollisionEffects collisionEffects) {
        collisionEffects.set(CollisionEffects.BURN);
    }
    
    private static class FlameStateMachine {
        
        public static final int DORMANT = 0;
        public static final int TRANSITION1 = 1;
        public static final int FLAME = 2;
        public static final int TRANSITION2 = 3;
        private static final int STATE_COUNT = 4;
        
        private final float[] mStateDurations;
        
        private int mCurrentState;
        private float mCurrentStateElapsed;
        
        public FlameStateMachine(float flameDuration, float dormantDuration, float transitionDuration) {
            mCurrentState = DORMANT;
            mCurrentStateElapsed = 0.0f;
            
            mStateDurations = new float[STATE_COUNT];
            mStateDurations[FLAME] = flameDuration;
            mStateDurations[DORMANT] = dormantDuration;
            mStateDurations[TRANSITION1] = transitionDuration;
            mStateDurations[TRANSITION2] = transitionDuration;
        }
        
        public void update(float delta) {
            mCurrentStateElapsed += delta;
            
            if (mCurrentStateElapsed >= mStateDurations[mCurrentState]) {
                mCurrentStateElapsed -= mStateDurations[mCurrentState];
                mCurrentState = (mCurrentState + 1) % STATE_COUNT;
            }
        }
        
        public int getCurrentState() {
            return mCurrentState;
        }
        
        public float getStateElapsedFraction() {
            return mCurrentStateElapsed / mStateDurations[mCurrentState];
        }
    }
}
