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
package com.turbogerm.helljump.screens.splash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.resources.ResourceNames;

public final class SplashTitle {
    
    private static final float GRAVITY = 1100.0f;
    private static final float JUMP_SPEED = 850.0f;
    
    private static final float INITIAL_POSITION_Y = 1000.0f;
    private static final float COLLISION_OFFSET_Y = 45.0f;
    
    private static final float RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float RESTITUTION_SPEED_DECREASE = 50.0f;
    
    private final Sprite mSprite;
    
    private final Vector2 mPosition;
    private float mSpeedY;
    private float mCurrentMaxJumpSpeed;
    
    private float mMinPositionY;
    
    private boolean mIsFinished;
    
    private final Sound mJumpSound;
    
    public SplashTitle(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.GUI_SPLASH_TITLE_IMAGE_NAME);
        
        mPosition = new Vector2();
        mPosition.x = (HellJump.VIEWPORT_WIDTH - mSprite.getWidth()) / 2.0f;
        
        mJumpSound = assetManager.get(ResourceNames.SOUND_JUMP);
    }
    
    public void reset(float minPositionY) {
        mPosition.y = INITIAL_POSITION_Y;
        mMinPositionY = minPositionY - COLLISION_OFFSET_Y;
        mSpeedY = 0.0f;
        mCurrentMaxJumpSpeed = JUMP_SPEED; 
        mIsFinished = false;
    }
    
    public void update(float delta) {
        mPosition.y += mSpeedY * delta;
        mPosition.y = Math.max(mPosition.y, mMinPositionY);
        
        if (mPosition.y <= mMinPositionY) {
            mPosition.y = mMinPositionY + GameUtils.EPSILON;
            mSpeedY = Math.min(-mSpeedY * RESTITUTION_MULTIPLIER, mCurrentMaxJumpSpeed);
            mCurrentMaxJumpSpeed = Math.min(mCurrentMaxJumpSpeed, mSpeedY);
            mSpeedY = Math.max(mSpeedY - RESTITUTION_SPEED_DECREASE, 0.0f);
            if (mSpeedY <= GameUtils.EPSILON) {
                mIsFinished = true;
            }
            
            if (!mIsFinished) {
                mJumpSound.play();
            }
        } else {
            mSpeedY = MathUtils.clamp(mSpeedY - GRAVITY * delta, -mCurrentMaxJumpSpeed, mCurrentMaxJumpSpeed);
        }
    }
    
    public void render(SpriteBatch batch) {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mSprite.draw(batch);
    }
    
    public boolean isFinished() {
        return mIsFinished;
    }
}
