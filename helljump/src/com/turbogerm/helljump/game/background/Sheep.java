/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

final class Sheep {
    
    private static final float GRAVITY = 20.0f;
    
    private static final float MIN_JUMP_SPEED = 3.0f;
    private static final float MAX_JUMP_SPEED = 6.0f;
    
    private static final float MIN_HORIZONTAL_SPEED = 0.5f;
    private static final float MAX_HORIZONTAL_SPEED = 2.0f;
    
    private static final float ROTATION_MULTIPLIER = 1.0f;
    
    private final Rectangle mCameraRect;
    
    private final Sprite mSprite;
    
    private final Vector2 mPosition;
    private final Vector2 mSize;
    private final Vector2 mSpeed;
    
    private float mJumpSpeed;
    private float mHorizontalSpeed;
    
    private boolean mIsLeftDirection;
    private boolean mIsFlippedSprite;
    
    private float mRiseHeight;
    
    public Sheep(CameraData cameraData, AssetManager assetManager) {
        
        mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        
        TextureAtlas atlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SHEEP_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mPosition = new Vector2();
        mSize = new Vector2(mSprite.getWidth(), mSprite.getHeight());
        mSpeed = new Vector2();
        
        mSprite.setSize(mSize.x, mSize.y);
        GameUtils.setSpriteOriginCenter(mSprite);
        
        mIsFlippedSprite = false;
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mPosition.x = MathUtils.random(getMinPosition() + GameUtils.EPSILON, getMaxPosition() - GameUtils.EPSILON);
        mPosition.y = mRiseHeight;
        
        mJumpSpeed = MathUtils.random(MIN_JUMP_SPEED, MAX_JUMP_SPEED);
        mHorizontalSpeed = MathUtils.random(MIN_HORIZONTAL_SPEED, MAX_HORIZONTAL_SPEED);
        
        mIsLeftDirection = MathUtils.randomBoolean();
        
        mSpeed.x = mIsLeftDirection ? -mHorizontalSpeed : mHorizontalSpeed;
        mSpeed.y = mJumpSpeed;
    }
    
    public void update(float delta) {
        mPosition.x += mSpeed.x * delta;
        mPosition.y += mSpeed.y * delta;
        
        float minPositionX = getMinPosition();
        float maxPositionX = getMaxPosition();
        
        if (mPosition.x <= minPositionX) {
            mPosition.x = minPositionX + GameUtils.EPSILON;
            mIsLeftDirection = false;
        } else if (mPosition.x >= maxPositionX) {
            mPosition.x = maxPositionX - GameUtils.EPSILON;
            mIsLeftDirection = true;
        }
        
        if (isFlipNeeded()) {
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
    
    private boolean isFlipNeeded() {
        return !(mIsLeftDirection ^ mIsFlippedSprite);
    }
    
    private float getMinPosition() {
        return mCameraRect.x;
    }
    
    private float getMaxPosition() {
        return mCameraRect.width + mCameraRect.x - mSize.x;
    }
}
 