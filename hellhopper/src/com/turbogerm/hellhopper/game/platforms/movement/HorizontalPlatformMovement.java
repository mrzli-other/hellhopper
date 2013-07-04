package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.game.platforms.features.PlatformModifier;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class HorizontalPlatformMovement extends PlatformMovementBase {
    
    private final float mRange;
    private final float mSpeed;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    
    public HorizontalPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition,
            AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_NORMAL_TEXTURE,
                ResourceNames.PARTICLE_ENGINE_NORMAL, assetManager);
        
        mRange = Float.parseFloat(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
        mSpeed = Float.parseFloat(movementData.getProperty(PlatformMovementData.SPEED_PROPERTY));
        
        mLeftLimit = initialPosition.x;
        mRightLimit = initialPosition.x + mRange;
        
        float initialOffset = Float.parseFloat(movementData.getProperty(PlatformMovementData.INITIAL_OFFSET_PROPERTY));
        if (initialOffset <= mRange) {
            changePosition(initialOffset);
            mIsRightMovement = true;
        } else {
            changePosition(mRange - initialOffset % mRange);
            mIsRightMovement = false;
        }
    }
    
    @Override
    protected void updateImpl(float delta) {
        float travelled = mSpeed * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.spriteColor.set(0.5f, 0.5f, 0.0f, 1.0f);
    }
    
    private void changePosition(float change) {
        mPosition.x += change;
        if (mPosition.x <= mLeftLimit) {
            mIsRightMovement = true;
        } else if (mPosition.x >= mRightLimit) {
            mIsRightMovement = false;
        }
        
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return false;
    }
}
