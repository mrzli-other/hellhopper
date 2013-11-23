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
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.dataaccess.EnemyData;
import com.turbogerm.helljump.resources.ResourceNames;

final class EvilTwinEnemy extends EnemyBase {
    
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float[] mAngleSpeeds;
    private final float[] mRadiuses;
    private final Vector2[] mRotationCenters;
    
    private float mAngleParameter;
    private final float mHalfMaxAngleParameter;
    private final float mMaxAngleParameter;
    
    private final Vector2 mPosition;
    private final Vector2 mCenterOffset;
    
    private final Rectangle mCollisionRect;
    
    public EvilTwinEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_EVIL_TWIN_IMAGE_NAME, startStep, assetManager);
        
        Vector2 initialPosition = enemyData.getPosition(startStep);
        float speed = Float.parseFloat(enemyData.getProperty(EnemyData.SPEED_PROPERTY));
        float[] ranges = getRanges(enemyData.getProperty(EnemyData.RANGES_PROPERTY));
        int numCurves = ranges.length;
        
        mAngleSpeeds = new float[numCurves];
        mRadiuses = new float[numCurves];
        mRotationCenters = new Vector2[numCurves];
        
        mPosition = new Vector2();
        mCenterOffset = new Vector2(
                mSprite.getWidth() / 2.0f, mSprite.getHeight() / 2.0f);
        
        float rotationCenterY = initialPosition.y + mCenterOffset.y;
        for (int i = 0; i < numCurves; i++) {
            mRadiuses[i] = ranges[i] / 2.0f;
            mAngleSpeeds[i] = speed / mRadiuses[i] * MathUtils.radDeg;
            
            float rotationCenterX = i == 0 ?
                    initialPosition.x + mCenterOffset.x + mRadiuses[i] :
                    mRotationCenters[i - 1].x + mRadiuses[i - 1] + mRadiuses[i];
            mRotationCenters[i] = new Vector2(rotationCenterX, rotationCenterY);
        }
        
        mAngleParameter = 0.0f;
        mHalfMaxAngleParameter = numCurves * 180.0f;
        mMaxAngleParameter = mHalfMaxAngleParameter * 2.0f;
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - 2.0f * COLLISION_PADDING;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    private static float[] getRanges(String rangesString) {
        String[] rangesStrings = rangesString.split(",");
        
        float[] ranges = new float[rangesStrings.length];
        for (int i = 0; i < rangesStrings.length; i++) {
            ranges[i] = Float.parseFloat(rangesStrings[i]);
        }
        
        return ranges;
    }
    
    @Override
    public void update(float delta) {
        
        changePosition(delta);
        
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionRect.setX(mPosition.x + COLLISION_PADDING);
        mCollisionRect.setY(mPosition.y + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private void changePosition(float delta) {
        int curveIndex = getCurveIndex();
        
        float travelledAngle = mAngleSpeeds[curveIndex] * delta;
        
        mAngleParameter += travelledAngle;
        mAngleParameter = GameUtils.getPositiveModulus(mAngleParameter, mMaxAngleParameter);
        
        curveIndex = getCurveIndex();
        float angle = mAngleParameter <= mHalfMaxAngleParameter ?
                180.0f - mAngleParameter % 180.0f : mAngleParameter % 180.0f;
        
        mPosition.x = mRotationCenters[curveIndex].x +
                MathUtils.cosDeg(angle) * mRadiuses[curveIndex] - mCenterOffset.x;
        mPosition.y = mRotationCenters[curveIndex].y +
                MathUtils.sinDeg(angle) * mRadiuses[curveIndex] - mCenterOffset.y;
    }
    
    private int getCurveIndex() {
        int numCurves = mAngleSpeeds.length;
        
        int curveIndex;
        if (mAngleParameter <= mHalfMaxAngleParameter) {
            curveIndex = (int) (mAngleParameter / 180.0f);
        } else {
            curveIndex = numCurves - 1 - (int) ((mAngleParameter - mHalfMaxAngleParameter) / 180.0f);
        }
        
        curveIndex = MathUtils.clamp(curveIndex, 0, numCurves - 1);
        return curveIndex;
    }
}
