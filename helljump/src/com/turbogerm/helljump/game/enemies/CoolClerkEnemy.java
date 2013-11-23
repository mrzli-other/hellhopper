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
package com.turbogerm.helljump.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.dataaccess.EnemyData;
import com.turbogerm.helljump.resources.ResourceNames;

final class CoolClerkEnemy extends EnemyBase {
    
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float mSpeed;
    private final float mRange;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    private float mPositionX;
    
    private final Rectangle mCollisionRect;
    
    public CoolClerkEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_COOL_CLERK_IMAGE_NAME, startStep, assetManager);
        
        Vector2 initialPosition = enemyData.getPosition(startStep);
        mSpeed = Float.parseFloat(enemyData.getProperty(EnemyData.SPEED_PROPERTY));
        mRange = Float.parseFloat(enemyData.getProperty(EnemyData.RANGE_PROPERTY));
        
        mLeftLimit = initialPosition.x;
        mRightLimit = initialPosition.x + mRange;
        
        mPositionX = initialPosition.x;
        
        float initialOffset = Float.parseFloat(enemyData.getProperty(EnemyData.INITIAL_OFFSET_PROPERTY));
        if (initialOffset <= mRange) {
            changePosition(initialOffset);
            mIsRightMovement = true;
        } else {
            changePosition(mRange - initialOffset % mRange);
            mIsRightMovement = false;
        }
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - 2.0f * COLLISION_PADDING;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void update(float delta) {
        float travelled = mSpeed * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
        
        mSprite.setX(mPositionX);
        mCollisionRect.setX(mSprite.getX() + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private void changePosition(float change) {
        mPositionX += change;
        if (mPositionX <= mLeftLimit) {
            mIsRightMovement = true;
        } else if (mPositionX >= mRightLimit) {
            mIsRightMovement = false;
        }
        
        mPositionX = MathUtils.clamp(mPositionX, mLeftLimit, mRightLimit);
    }
}
