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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

final class HorizontalMovementPlatform extends PlatformBase {
    
    private static final float ENGINE_WIDTH = 8.0f;
    private static final float ENGINE_HEIGHT = 16.0f;
    private static final float LEFT_ENGINE_X_OFFSET = 0.0f;
    private static final float LEFT_ENGINE_Y_OFFSET = (PlatformData.PLATFORM_HEIGHT - ENGINE_HEIGHT) / 2.0f;
    private static final float RIGHT_ENGINE_X_OFFSET = PlatformData.PLATFORM_WIDTH - ENGINE_WIDTH;
    private static final float RIGHT_ENGINE_Y_OFFSET = LEFT_ENGINE_Y_OFFSET;
    
    private final Sprite mLeftEngineSprite;
    private final Sprite mRightEngineSprite;
    private final ParticleEffect mEngineEffect;
    
    private final float mRange;
    private final float mSpeed;
    private final Vector2 mPosition;
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    
    public HorizontalMovementPlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData.getPlatformPositions(startStep), ResourceNames.getRandomPlatformNormalTexture(),
                assetManager, false);
        
        Texture leftEngineTexture = assetManager.get(ResourceNames.PLATFORM_ENGINE_LEFT_TEXTURE);
        mLeftEngineSprite = new Sprite(leftEngineTexture);
        mLeftEngineSprite.setBounds(
                mInitialPosition.x + LEFT_ENGINE_X_OFFSET, mInitialPosition.y + LEFT_ENGINE_Y_OFFSET,
                ENGINE_WIDTH, ENGINE_HEIGHT);
        
        Texture rightEngineTexture = assetManager.get(ResourceNames.PLATFORM_ENGINE_RIGHT_TEXTURE);
        mRightEngineSprite = new Sprite(rightEngineTexture);
        mRightEngineSprite.setBounds(
                mInitialPosition.x + RIGHT_ENGINE_X_OFFSET, mInitialPosition.y + RIGHT_ENGINE_Y_OFFSET,
                ENGINE_WIDTH, ENGINE_HEIGHT);
        
        mEngineEffect = new ParticleEffect((ParticleEffect) assetManager.get(ResourceNames.PARTICLE_ENGINE));
        
        mRange = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_RANGE_PROPERTY));
        mSpeed = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_SPEED_PROPERTY));
        mPosition = new Vector2(mInitialPosition);
        mLeftLimit = mInitialPosition.x;
        mRightLimit = mInitialPosition.x + mRange;
        mIsRightMovement = true;
    }
    
    @Override
    public void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        float travelled = mSpeed * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        mPosition.x += travelled;
        if (mPosition.x <= mLeftLimit){
            mIsRightMovement = true;
        } else if (mPosition.x >= mRightLimit) {
            mIsRightMovement = false;
        }
        
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
    
    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        
        mLeftEngineSprite.setPosition(mPosition.x + LEFT_ENGINE_X_OFFSET, mPosition.y + LEFT_ENGINE_Y_OFFSET);
        mLeftEngineSprite.draw(batch);
        
        mRightEngineSprite.setPosition(mPosition.x + RIGHT_ENGINE_X_OFFSET, mPosition.y + RIGHT_ENGINE_Y_OFFSET);
        mRightEngineSprite.draw(batch);
        
        Sprite activeEngineSprite = mIsRightMovement ? mLeftEngineSprite : mRightEngineSprite;
        mEngineEffect.setPosition(
                activeEngineSprite.getX() + ENGINE_WIDTH / 2.0f, activeEngineSprite.getY() + ENGINE_HEIGHT / 2.0f);
        mEngineEffect.draw(batch, delta);
    }
    
    @Override
    public Vector2 getPosition() {
        return mPosition;
    }
}
