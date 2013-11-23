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
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.dataaccess.EnemyData;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.resources.ResourceNames;

final class LocoEnemy extends EnemyBase {
    
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float mTravelPeriod;
    private final float mTravelHalfPeriod;
    private final float mRange;
    private float mTravelTime;
    
    private final Rectangle mCollisionRect;
    
    public LocoEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_LOCO_IMAGE_NAME, startStep, assetManager);
        
        mTravelPeriod = Float.parseFloat(enemyData.getProperty(EnemyData.TRAVEL_PERIOD_PROPERTY));
        mTravelHalfPeriod = mTravelPeriod / 2.0f;
        mRange = GameArea.GAME_AREA_WIDTH - mSprite.getWidth();
                
        mSprite.setX(0.0f);
        
        mTravelTime = 0.0f;
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - 2.0f * COLLISION_PADDING;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void update(float delta) {
        mTravelTime = (mTravelTime + delta) % mTravelPeriod;
        float x = mRange * getPositionFraction();
        mSprite.setX(x);
        mCollisionRect.setX(mSprite.getX() + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private float getPositionFraction() {
        float t = mTravelTime / mTravelHalfPeriod;
        if (t > 1.0f) {
            t = 2.0f - t;
        }
        t = MathUtils.clamp(t, 0.0f, 1.0f);
        t = Interpolation.pow5.apply(t);
        
        return t;
    }
}
