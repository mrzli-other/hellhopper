package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class NullPlatformMovement extends PlatformMovementBase {
    
    public NullPlatformMovement(Vector2 initialPosition, AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_NORMAL_IMAGE_NAME,
                ResourceNames.PARTICLE_ENGINE_NORMAL, assetManager);
    }
    
    @Override
    public void render(SpriteBatch batch, float alpha) {
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return false;
    }
}
