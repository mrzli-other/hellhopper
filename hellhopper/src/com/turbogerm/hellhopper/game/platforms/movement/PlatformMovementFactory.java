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
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.util.ExceptionThrower;

public final class PlatformMovementFactory {
    
    public static PlatformMovementBase create(PlatformMovementData movementData, Vector2 initialPosition, AssetManager assetManager) {
        
        if (movementData == null) {
            return new NullPlatformMovement(initialPosition, assetManager);
        }
        
        String movementType = movementData.getMovementType();
        if (PlatformMovementData.HORIZONTAL_MOVEMENT.equals(movementType)) {
            return new HorizontalPlatformMovement(movementData, initialPosition, assetManager);
        } else if (PlatformMovementData.VERTICAL_MOVEMENT.equals(movementType)) {
            return new VerticalPlatformMovement(movementData, initialPosition, assetManager);
        } else if (PlatformMovementData.CIRCULAR_MOVEMENT.equals(movementType)) {
            return new CircularPlatformMovement(movementData, initialPosition, assetManager);
        } else if (PlatformMovementData.JUMP_MOVEMENT.equals(movementType)) {
            return new JumpPlatformMovement(movementData, initialPosition, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid platform movement type: %s", movementType);
            return null;
        }
    }
}
