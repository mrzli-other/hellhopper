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
package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.EnemyData;

final class ImpEnemy extends EnemyBase {
    
    private static final float SPEED = 3.0f;
    private static final float SIN_AMPLITUDE = 0.2f;
    private static final float SIN_PERIOD = 0.5f;
    
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float mRange;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    
    private final Vector2 mInitialPosition;
    private final Vector2 mPosition;
    
    private float mSinTime;
    
    private final Rectangle mCollisionRect;
    
    public ImpEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_IMP_TEXTURE, startStep, assetManager);
        
        mInitialPosition = enemyData.getPosition(startStep);
        mRange = Float.parseFloat(enemyData.getProperty(EnemyData.RANGE_PROPERTY));
        
        mLeftLimit = mInitialPosition.x;
        mRightLimit = mInitialPosition.x + mRange;
        mIsRightMovement = true;
        
        mPosition = new Vector2(mInitialPosition);
        
        mSprite.flip(true, false);
        
        mSinTime = 0.0f;
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - 2.0f * COLLISION_PADDING;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void update(float delta) {
        float travelled = SPEED * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
        
        mSinTime = (mSinTime + delta) % SIN_PERIOD;
        mPosition.y = mInitialPosition.y + MathUtils.sinDeg(mSinTime / SIN_PERIOD * 360.0f) * SIN_AMPLITUDE;
        
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionRect.setX(mPosition.x + COLLISION_PADDING);
        mCollisionRect.setY(mPosition.y + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private void changePosition(float change) {
        mPosition.x += change;
        if (mPosition.x <= mLeftLimit) {
            mIsRightMovement = true;
            mSprite.flip(true, false);
        } else if (mPosition.x >= mRightLimit) {
            mIsRightMovement = false;
            mSprite.flip(true, false);
        }
        
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
}
