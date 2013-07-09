package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

final class Sheep {
    
    private static final float GRAVITY = 20.0f;
    
    private static final float MIN_JUMP_SPEED = 3.0f;
    private static final float MAX_JUMP_SPEED = 6.0f;
    
    private static final float MIN_HORIZONTAL_SPEED = 0.5f;
    private static final float MAX_HORIZONTAL_SPEED = 2.0f;
    
    private static final float ROTATION_MULTIPLIER = 1.0f;
    
    private final Sprite mSprite;
    
    private final Vector2 mPosition;
    private final Vector2 mSize;
    private final Vector2 mSpeed;
    
    private float mMaxPositionX;
    private float mJumpSpeed;
    private float mHorizontalSpeed;
    
    private boolean mIsLeftDirection;
    private boolean mIsFlippedSprite;
    
    private float mRiseHeight;
    
    public Sheep(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SHEEP_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mPosition = new Vector2();
        mSize = new Vector2(mSprite.getWidth(), mSprite.getHeight());
        mSpeed = new Vector2();
        
        mSprite.setSize(mSize.x, mSize.y);
        GameUtils.setSpriteOriginCenter(mSprite);
        
        mMaxPositionX = GameArea.GAME_AREA_WIDTH - mSize.x;
        
        mIsFlippedSprite = false;
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mPosition.x = MathUtils.random(GameUtils.EPSILON, mMaxPositionX - GameUtils.EPSILON);
        mPosition.y = mRiseHeight;
        
        mJumpSpeed = MathUtils.random(MIN_JUMP_SPEED, MAX_JUMP_SPEED);
        mHorizontalSpeed = MathUtils.random(MIN_HORIZONTAL_SPEED, MAX_HORIZONTAL_SPEED);
        
        mIsLeftDirection = MathUtils.randomBoolean();
        if (isInitialFlipNeeded()) {
            flipSprite();
        }
        
        mSpeed.x = mIsLeftDirection ? -mHorizontalSpeed : mHorizontalSpeed;
        mSpeed.y = mJumpSpeed;
    }
    
    public void update(float delta) {
        mPosition.x += mSpeed.x * delta;
        mPosition.y += mSpeed.y * delta;
        
        if (mPosition.x <= 0.0f) {
            mPosition.x = GameUtils.EPSILON;
            mIsLeftDirection = false;
            flipSprite();
        } else if (mPosition.x >= mMaxPositionX) {
            mPosition.x = mMaxPositionX - GameUtils.EPSILON;
            mIsLeftDirection = true;
            flipSprite();
        }
        
        mSpeed.x = mIsLeftDirection ? -mHorizontalSpeed : mHorizontalSpeed;
        
        if (mPosition.y <= mRiseHeight) {
            mPosition.y = mRiseHeight;
            mSpeed.y = mJumpSpeed;
        } else {
            mSpeed.y = Math.max(mSpeed.y - GRAVITY * delta, -mJumpSpeed);
        }
        
        float rotation = mSpeed.y * ROTATION_MULTIPLIER;
        if (mIsLeftDirection) {
            rotation = -rotation;
        }
        mSprite.setRotation(rotation);
    }
    
    public void render(SpriteBatch batch) {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mSprite.draw(batch);
    }
    
    private void flipSprite() {
        mSprite.flip(true, false);
        mIsFlippedSprite = !mIsFlippedSprite;
    }
    
    private boolean isInitialFlipNeeded() {
        return !(mIsLeftDirection ^ mIsFlippedSprite);
    }
}
 