/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

final class FlamePlatform extends PlatformBase {
    
    private static final String FIRE_IMAGE_NAME = "platformfire";
    private static final float FIRE_FRAME_DURATION = 0.15f;
    private static final float FIRE_SPRITE_WIDTH = 2.0f;
    private static final float FIRE_SPRITE_HEIGHT = 0.6f;
    
    private static final float MIN_COLOR_VALUE = 0.4f;
    private static final float MAX_COLOR_VALUE = 1.0f;
    private static final float COLOR_VALUE_RANGE = MAX_COLOR_VALUE - MIN_COLOR_VALUE;
    
    private final Animation mFireAnimation;
    private float mFireAnimationTime = 0.0f;
    
    private final FlameStateMachine mFlameStateMachine;
    private float mColorValue;
    private boolean mIsFlameActive;
    
    public FlamePlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData, platformData.getPlatformPositions(startStep), assetManager);
        
        mFlameStateMachine = new FlameStateMachine();
        mIsFlameActive = false;
        
        TextureAtlas fireAtlas = assetManager.get(ResourceNames.PLATFORM_FIRE_TEXTURE_ATLAS);
        Array<AtlasRegion> fireAtlasRegions = fireAtlas.findRegions(FIRE_IMAGE_NAME);
        mFireAnimation = new Animation(FIRE_FRAME_DURATION, fireAtlasRegions, Animation.LOOP);
        mFireAnimationTime = 0.0f;
    }
    
    @Override
    protected void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        mFlameStateMachine.update(delta);
        mColorValue = getColorValue();
        
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
        
        super.updateImpl(delta, c1, c2, collisionData);
    }
    
    @Override
    protected void renderImpl(SpriteBatch batch, float delta) {
        Vector2 position = getPosition();
        
        if (mIsFlameActive) {
            TextureRegion fireAnimationFrame = mFireAnimation.getKeyFrame(mFireAnimationTime);
            batch.draw(fireAnimationFrame,
                    position.x, position.y + PlatformData.PLATFORM_HEIGHT,
                    FIRE_SPRITE_WIDTH, FIRE_SPRITE_HEIGHT);
        }
        
        mSprite.setColor(mColorValue, mColorValue, mColorValue, 1.0f);
        super.renderImpl(batch, delta);
    }
    
    private float getColorValue() {
        switch (mFlameStateMachine.getCurrentState()) {
            case FlameStateMachine.DORMANT:
                return MIN_COLOR_VALUE;
                
            case FlameStateMachine.TRANSITION1:
                return MIN_COLOR_VALUE + mFlameStateMachine.getStateElapsedFraction() * COLOR_VALUE_RANGE;
                
            case FlameStateMachine.FLAME:
                return MAX_COLOR_VALUE;
                
            case FlameStateMachine.TRANSITION2:
                return MAX_COLOR_VALUE - mFlameStateMachine.getStateElapsedFraction() * COLOR_VALUE_RANGE;
        }
        
        return 0.0f;
    }
    
    private static class FlameStateMachine {
        
        public static final int DORMANT = 0;
        public static final int TRANSITION1 = 1;
        public static final int FLAME = 2;
        public static final int TRANSITION2 = 3;
        private static final int STATE_COUNT = 4;
        
        private static final float[] STATE_DURATIONS;
        
        private int mCurrentState;
        private float mCurrentStateElapsed;
        
        static {
            STATE_DURATIONS = new float[] { 2.0f, 2.0f, 4.0f, 2.0f };
        }
        
        public FlameStateMachine() {
            mCurrentState = DORMANT;
            mCurrentStateElapsed = 0.0f;
        }
        
        public void update(float delta) {
            mCurrentStateElapsed += delta;
            
            if (mCurrentStateElapsed >= STATE_DURATIONS[mCurrentState]) {
                mCurrentStateElapsed -= STATE_DURATIONS[mCurrentState];
                mCurrentState = (mCurrentState + 1) % STATE_COUNT;
            }
        }
        
        public int getCurrentState() {
            return mCurrentState;
        }
        
        public float getStateElapsedFraction() {
            return mCurrentStateElapsed / STATE_DURATIONS[mCurrentState];
        }
    }
}
