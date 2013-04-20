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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.util.GameUtils;

final class CircularMovementPlatform extends PlatformBase {
    
    private static final Vector2 PLATFORM_CENTER_OFFSET;
    
    private final float mRadius;
    private final float mSpeed;
    private final Vector2 mPosition;
    
    private final float mAngleSpeed;
    private float mAngle;
    private final Vector2 mRotationCenter;
    
    static {
        PLATFORM_CENTER_OFFSET = new Vector2(PlatformData.PLATFORM_WIDTH / 2.0f, PlatformData.PLATFORM_HEIGHT / 2.0f);
    }
    
    public CircularMovementPlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData.getPlatformPositions(startStep), ResourceNames.PLATFORM_CIRCULAR_MOVEMENT_TEXTURE,
                assetManager, true);
        
        mRadius = Float.parseFloat(platformData.getProperty(PlatformData.CIRCULAR_MOVEMENT_RADIUS_PROPERTY));
        mSpeed = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_SPEED_PROPERTY));
        mPosition = new Vector2(mInitialPosition);
        mAngleSpeed = mSpeed / mRadius * MathUtils.radDeg;
        mAngle = 0.0f;
        mRotationCenter = new Vector2(
                mInitialPosition.x + PLATFORM_CENTER_OFFSET.x + mRadius,
                mInitialPosition.y + PLATFORM_CENTER_OFFSET.y);
    }
    
    @Override
    public void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        float travelledAngle = mAngleSpeed * delta;
        mAngle += travelledAngle;
        mAngle = GameUtils.getPositiveModulus(mAngle, 360.0f);
        
        mPosition.x = mRotationCenter.x + MathUtils.cosDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.x;
        mPosition.y = mRotationCenter.y + MathUtils.sinDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.y;
    }
    
    @Override
    public Vector2 getPosition() {
        return mPosition;
    }
}
