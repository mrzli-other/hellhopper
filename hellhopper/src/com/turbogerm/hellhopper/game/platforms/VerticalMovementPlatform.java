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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

final class VerticalMovementPlatform extends PlatformBase {
    
    private static final float ENGINE_WIDTH = 16.0f;
    private static final float ENGINE_HEIGHT = 8.0f;
    private static final float BOTTOM_ENGINE_X_OFFSET = (PlatformData.PLATFORM_WIDTH - ENGINE_WIDTH) / 2.0f;
    private static final float BOTTOM_ENGINE_Y_OFFSET = 0.0f;
    private static final float TOP_ENGINE_X_OFFSET = BOTTOM_ENGINE_X_OFFSET;
    private static final float TOP_ENGINE_Y_OFFSET = PlatformData.PLATFORM_HEIGHT - ENGINE_HEIGHT;
    
    private final Sprite mBottomEngineSprite;
    private final Sprite mTopEngineSprite;
    
    private final float mRange;
    private final float mSpeed;
    private final Vector2 mPosition;
    private final float mBottomLimit;
    private final float mTopLimit;
    private boolean mIsUpMovement;
    
    public VerticalMovementPlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData.getPlatformPositions(startStep), ResourceNames.getRandomPlatformNormalTexture(),
                assetManager, true);
        
        Texture bottomEngineTexture = assetManager.get(ResourceNames.PLATFORM_ENGINE_BOTTOM_TEXTURE);
        mBottomEngineSprite = new Sprite(bottomEngineTexture);
        mBottomEngineSprite.setBounds(
                mInitialPosition.x + BOTTOM_ENGINE_X_OFFSET, mInitialPosition.y + BOTTOM_ENGINE_Y_OFFSET,
                ENGINE_WIDTH, ENGINE_HEIGHT);
        
        Texture topEngineTexture = assetManager.get(ResourceNames.PLATFORM_ENGINE_TOP_TEXTURE);
        mTopEngineSprite = new Sprite(topEngineTexture);
        mTopEngineSprite.setBounds(
                mInitialPosition.x + TOP_ENGINE_X_OFFSET, mInitialPosition.y + TOP_ENGINE_Y_OFFSET,
                ENGINE_WIDTH, ENGINE_HEIGHT);
        
        mRange = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_RANGE_PROPERTY));
        mSpeed = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_SPEED_PROPERTY));
        mPosition = new Vector2(mInitialPosition);
        mBottomLimit = mInitialPosition.y;
        mTopLimit = mInitialPosition.y + mRange;
        mIsUpMovement = true;
    }
    
    @Override
    public void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        
        float travelled = mSpeed * delta;
        if (!mIsUpMovement) {
            travelled = -travelled;
        }
        
        mPosition.y += travelled;
        if (mPosition.y <= mBottomLimit){
            mIsUpMovement = true;
        } else if (mPosition.y >= mTopLimit) {
            mIsUpMovement = false;
        }
        
        mPosition.y = MathUtils.clamp(mPosition.y, mBottomLimit, mTopLimit);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        
        mBottomEngineSprite.setPosition(mPosition.x + BOTTOM_ENGINE_X_OFFSET, mPosition.y + BOTTOM_ENGINE_Y_OFFSET);
        mBottomEngineSprite.draw(batch);
        
        mTopEngineSprite.setPosition(mPosition.x + TOP_ENGINE_X_OFFSET, mPosition.y + TOP_ENGINE_Y_OFFSET);
        mTopEngineSprite.draw(batch);
    }
    
    @Override
    public Vector2 getPosition() {
        return mPosition;
    }
}
