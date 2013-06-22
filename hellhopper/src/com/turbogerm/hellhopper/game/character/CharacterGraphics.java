package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.ColorInterpolator;

final class CharacterGraphics {
    
    private static final float BODY_OFFSET_X = 0.05f;
    private static final float BODY_OFFSET_Y = 0.0f;
    private static final float BODY_WIDTH = 0.9f;
    private static final float BODY_HEIGHT = 0.8f;
    private static final float HEAD_OFFSET_X = 0.0f;
    private static final float HEAD_OFFSET_Y = 0.35f;
    private static final float HEAD_WIDTH = 1.0f;
    private static final float HEAD_HEIGHT = 1.15f;
    private static final float EYES_OFFSET_X = 0.075f;
    private static final float EYES_OFFSET_Y = 0.65f;
    private static final float EYES_WIDTH = 0.85f;
    private static final float EYES_HEIGHT = 0.45f;
    
    private static final Color DEFAULT_BODY_COLOR;
    private static final Color DEFAULT_HEAD_COLOR;
    private static final Color DEFAULT_EYES_COLOR;
    private static final Color ENEMY_DEATH_COLOR;
    private static final Color FIRE_DEATH_COLOR;
    
    private final Sprite mBodySprite;
    private final Sprite mHeadSprite;
    private final Sprite[] mEyesSprites;
    
    private final Color mBodyColor;
    private final Color mHeadColor;
    
    private final ColorInterpolator mColorInterpolator;
    
    private final BlinkingStateMachine mBlinkingStateMachine;
    
    static {
        DEFAULT_BODY_COLOR = new Color(0.14f, 0.36f, 0.43f, 1.0f);
        //DEFAULT_BODY_COLOR = new Color(1.0f, 0.0f, 0.0f, 1.0f);
        DEFAULT_HEAD_COLOR = new Color(0.57f, 0.74f, 0.79f, 1.0f);
        DEFAULT_EYES_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
        ENEMY_DEATH_COLOR = Color.RED;
        FIRE_DEATH_COLOR = Color.BLACK;
    }
    
    public CharacterGraphics(AssetManager assetManager) {
        
        Texture bodyTexture = assetManager.get(ResourceNames.CHARACTER_BODY_TEXTURE);
        mBodySprite = new Sprite(bodyTexture);
        mBodySprite.setSize(BODY_WIDTH, BODY_HEIGHT);
        
        Texture headTexture = assetManager.get(ResourceNames.CHARACTER_HEAD_TEXTURE);
        mHeadSprite = new Sprite(headTexture);
        mHeadSprite.setSize(HEAD_WIDTH, HEAD_HEIGHT);
        
        mEyesSprites = new Sprite[ResourceNames.CHARACTER_EYES_TEXTURE_COUNT];
        for (int i = 0; i < mEyesSprites.length; i++) {
            mEyesSprites[i] = new Sprite((Texture) assetManager.get(ResourceNames.getCharacterEyesTexture(i)));
            mEyesSprites[i].setSize(EYES_WIDTH, EYES_HEIGHT);
            mEyesSprites[i].setColor(DEFAULT_EYES_COLOR);
        }
        
        mBodyColor = new Color();
        mHeadColor = new Color();
        
        mColorInterpolator = new ColorInterpolator();
        
        mBlinkingStateMachine = new BlinkingStateMachine();
    }
    
    public void reset() {
        mBodyColor.set(DEFAULT_BODY_COLOR);
        mHeadColor.set(DEFAULT_HEAD_COLOR);
    }
    
    public void update(float delta) {
        mBlinkingStateMachine.update(delta);
    }
    
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        
//        if (isDying()) {
//            Color deathColor = mIsDyingFromEnemy ? ENEMY_DEATH_COLOR : FIRE_DEATH_COLOR;
//            float t = MathUtils.clamp(mDyingElapsed / DYING_ANIMATION_DURATION, 0.0f, 1.0f);
//            mBodyColor.set(mColorInterpolator.interpolateColor(DEFAULT_BODY_COLOR, deathColor, t));
//            mHeadColor.set(mColorInterpolator.interpolateColor(DEFAULT_BODY_COLOR, deathColor, t));
//        }
        
        mBodySprite.setColor(mBodyColor);
        mHeadSprite.setColor(mHeadColor);
        
        mBodySprite.setPosition(characterPosition.x + BODY_OFFSET_X, characterPosition.y + BODY_OFFSET_Y);
        mBodySprite.draw(batch);
        
        mHeadSprite.setPosition(characterPosition.x + HEAD_OFFSET_X, characterPosition.y + HEAD_OFFSET_Y);
        mHeadSprite.draw(batch);
        
        int eyesSpriteIndex = mBlinkingStateMachine.getTextureIndex();
        mEyesSprites[eyesSpriteIndex].setPosition(
                characterPosition.x + EYES_OFFSET_X, characterPosition.y + EYES_OFFSET_Y);
        mEyesSprites[eyesSpriteIndex].draw(batch);
    }
    
    private static class BlinkingStateMachine {
        
        private static final int OPEN = 0;
        private static final int CLOSED = 1;
        private static final int DOUBLE_BLINK_OPEN = 2;
        private static final int DOUBLE_BLINK_CLOSED_1 = 3;
        private static final int DOUBLE_BLINK_CLOSED_2 = 4;
        //private static final int STATE_COUNT = 5;
        
        private static final float MIN_OPEN_DURATION = 2.0f;
        private static final float MAX_OPEN_DURATION = 5.0f;
        private static final float CLOSED_DURATION = 0.2f;
        private static final float DOUBLE_BLINK_OPEN_DURATION = 0.2f;
        private static final float DOUBLE_BLINK_CLOSED_DURATION = 0.1f;
        
        private static final float DOUBLE_BLINK_CHANCE = 0.3f;
        
        private int mCurrentState;
        private float mCurrentStateDuration;
        private float mCurrentStateElapsed;
        
        public BlinkingStateMachine() {
            mCurrentState = OPEN;
            mCurrentStateElapsed = 0.0f;
            updateStateDuration();
        }
        
        public void update(float delta) {
            mCurrentStateElapsed += delta;
            
            if (mCurrentStateElapsed >= mCurrentStateDuration) {
                mCurrentStateElapsed -= mCurrentStateDuration;
                changeState();
                updateStateDuration();
            }
        }
        
        public int getTextureIndex() {
            if (mCurrentState == OPEN || mCurrentState == DOUBLE_BLINK_OPEN) {
                return 0;
            } else {
                return 1;
            }
        }
        
        private void changeState() {
            mCurrentState = getNextState(mCurrentState);
        }
        
        private static int getNextState(int currentState) {
            switch (currentState) {
                case OPEN:
                    if (isDoubleBlink()) {
                        return DOUBLE_BLINK_CLOSED_1;
                    } else {
                        return CLOSED;
                    }
                    
                case CLOSED:
                    return OPEN;
                    
                case DOUBLE_BLINK_OPEN:
                    return DOUBLE_BLINK_CLOSED_2;
                    
                case DOUBLE_BLINK_CLOSED_1:
                    return DOUBLE_BLINK_OPEN;
                    
                case DOUBLE_BLINK_CLOSED_2:
                    return OPEN;
                
                default:
                    return OPEN;
            }
        }
        
        private void updateStateDuration() {
            mCurrentStateDuration = getStateDuration(mCurrentState);
        }
        
        private static float getStateDuration(int state) {
            switch (state) {
                case OPEN:
                    return MathUtils.random(MIN_OPEN_DURATION, MAX_OPEN_DURATION);
                    
                case CLOSED:
                    return CLOSED_DURATION;
                    
                case DOUBLE_BLINK_OPEN:
                    return DOUBLE_BLINK_OPEN_DURATION;
                    
                case DOUBLE_BLINK_CLOSED_1:
                case DOUBLE_BLINK_CLOSED_2:
                    return DOUBLE_BLINK_CLOSED_DURATION;
                
                default:
                    return 0.0f;
            }
        }
        
        private static boolean isDoubleBlink() {
            return MathUtils.random() < DOUBLE_BLINK_CHANCE;
        }
    }
}
