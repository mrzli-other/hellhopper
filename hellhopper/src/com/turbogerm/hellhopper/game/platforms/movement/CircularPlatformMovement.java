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
package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.util.GameUtils;

public final class CircularPlatformMovement extends PlatformMovementBase {
    
    private final float mRadius;
    private final float mSpeed;
    private final boolean mIsCcw;
    
    private final float mAngleSpeed;
    private float mAngle;
    private final Vector2 mRotationCenter;
    
    public CircularPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition,
            AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_NORMAL_TEXTURE,
                ResourceNames.PARTICLE_ENGINE_NORMAL, assetManager);
        
        mRadius = Float.parseFloat(movementData.getProperty(PlatformMovementData.RADIUS_PROPERTY));
        mSpeed = Float.parseFloat(movementData.getProperty(PlatformMovementData.SPEED_PROPERTY));
        mIsCcw = PlatformMovementData.DIRECTION_CCW_PROPERTY_VALUE.equals(
                movementData.getProperty(PlatformMovementData.DIRECTION_PROPERTY));
        
        mAngleSpeed = mSpeed / mRadius * MathUtils.radDeg;
        mAngle = 0.0f;
        mRotationCenter = new Vector2(
                initialPosition.x + PLATFORM_CENTER_OFFSET.x + mRadius,
                initialPosition.y + PLATFORM_CENTER_OFFSET.y);
        
        float initialDegrees = Float
                .parseFloat(movementData.getProperty(PlatformMovementData.INITIAL_DEGREES_PROPERTY));
        changePosition(initialDegrees);
    }
    
    @Override
    public void updatePosition(float delta) {
        float travelledAngle = mAngleSpeed * delta;
        if (!mIsCcw) {
            travelledAngle = -travelledAngle;
        }
        
        changePosition(travelledAngle);
    }
    
    private void changePosition(float change) {
        mAngle += change;
        mAngle = GameUtils.getPositiveModulus(mAngle, 360.0f);
        
        mPosition.x = mRotationCenter.x + MathUtils.cosDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.x;
        mPosition.y = mRotationCenter.y + MathUtils.sinDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.y;
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return true;
    }
}
