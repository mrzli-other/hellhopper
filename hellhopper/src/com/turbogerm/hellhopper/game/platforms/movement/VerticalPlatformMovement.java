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
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;

public final class VerticalPlatformMovement extends PlatformMovementBase {
    
    private final float mRange;
    private final float mSpeed;
    
    private final float mBottomLimit;
    private final float mTopLimit;
    private boolean mIsUpMovement;
    
    public VerticalPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition, AssetManager assetManager) {
        super(initialPosition, assetManager);
        
        mRange = Float.parseFloat(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
        mSpeed = Float.parseFloat(movementData.getProperty(PlatformMovementData.SPEED_PROPERTY));
        
        mBottomLimit = initialPosition.y;
        mTopLimit = initialPosition.y + mRange;
        mIsUpMovement = true;
    }
    
    @Override
    public void updatePosition(float delta) {
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
    public boolean hasVerticalMovement() {
        return true;
    }
}
