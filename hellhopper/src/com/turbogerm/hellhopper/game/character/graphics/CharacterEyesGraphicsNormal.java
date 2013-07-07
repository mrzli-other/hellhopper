package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class CharacterEyesGraphicsNormal extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.075f;
    private static final float OFFSET_Y = 0.65f;
    private static final float WIDTH = 0.85f;
    private static final float HEIGHT = 0.45f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite[] mSprites;
    
    private final BlinkingStateMachine mBlinkingStateMachine;
    
    static {
        DEFAULT_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    public CharacterEyesGraphicsNormal(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        
        mSprites = new Sprite[ResourceNames.CHARACTER_EYES_NORMAL_IMAGE_COUNT];
        for (int i = 0; i < mSprites.length; i++) {
            mSprites[i] = atlas.createSprite(ResourceNames.getCharacterEyesNormalImageName(i));
            mSprites[i].setSize(WIDTH, HEIGHT);
            mSprites[i].setColor(DEFAULT_COLOR);
        }
        
        mBlinkingStateMachine = new BlinkingStateMachine();
    }
    
    @Override
    public void reset() {
    }
    
    @Override
    public void update(float delta) {
        mBlinkingStateMachine.update(delta);
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        int eyesSpriteIndex = mBlinkingStateMachine.getTextureIndex();
        mSprites[eyesSpriteIndex].setPosition(
                characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprites[eyesSpriteIndex].draw(batch);
    }
    
    private static class BlinkingStateMachine {
        
        private static final int OPEN = 0;
        private static final int CLOSED = 1;
        private static final int DOUBLE_BLINK_OPEN = 2;
        private static final int DOUBLE_BLINK_CLOSED_1 = 3;
        private static final int DOUBLE_BLINK_CLOSED_2 = 4;
        // private static final int STATE_COUNT = 5;
        
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
