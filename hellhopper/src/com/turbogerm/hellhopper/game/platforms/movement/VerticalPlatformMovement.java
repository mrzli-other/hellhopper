package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.game.platforms.features.PlatformModifier;

public final class VerticalPlatformMovement extends PlatformMovementBase {
    
    private final float mRange;
    private final float mSpeed;
    
    private final float mBottomLimit;
    private final float mTopLimit;
    private boolean mIsUpMovement;
    
    public VerticalPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition, AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_NORMAL_TEXTURE,
                ResourceNames.PARTICLE_ENGINE_NORMAL, assetManager);
        
        mRange = Float.parseFloat(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
        mSpeed = Float.parseFloat(movementData.getProperty(PlatformMovementData.SPEED_PROPERTY));
        
        mBottomLimit = initialPosition.y;
        mTopLimit = initialPosition.y + mRange;
        mIsUpMovement = true;
        
        float initialOffset = Float.parseFloat(movementData.getProperty(PlatformMovementData.INITIAL_OFFSET_PROPERTY));
        if (initialOffset <= mRange) {
            changePosition(initialOffset);
            mIsUpMovement = true;
        } else {
            changePosition(mRange - initialOffset % mRange);
            mIsUpMovement = false;
        }
    }
    
    @Override
    public void updatePosition(float delta) {
        float travelled = mSpeed * delta;
        if (!mIsUpMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
    }
    
    private void changePosition(float change) {
        mPosition.y += change;
        if (mPosition.y <= mBottomLimit){
            mIsUpMovement = true;
        } else if (mPosition.y >= mTopLimit) {
            mIsUpMovement = false;
        }
        
        mPosition.y = MathUtils.clamp(mPosition.y, mBottomLimit, mTopLimit);
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.spriteColor.set(0.7f, 0.13f, 0.13f, 1.0f);
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return true;
    }
}
