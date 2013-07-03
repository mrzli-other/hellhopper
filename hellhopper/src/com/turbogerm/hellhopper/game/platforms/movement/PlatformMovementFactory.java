package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;

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
        } else if (PlatformMovementData.REPOSITION_MOVEMENT.equals(movementType)) {
            return new RepositionPlatformMovement(movementData, initialPosition, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid platform movement type: %s", movementType);
            return null;
        }
    }
}
