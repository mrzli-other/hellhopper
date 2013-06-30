package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.game.CollisionEffects;
import com.turbogerm.hellhopper.game.platforms.features.PlatformModifier;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class RepositionPlatformMovement extends PlatformMovementBase {
    
    private static final float REPOSITION_TIME = 0.2f;
    
    private final float mRange;
    
    private final boolean mIsRandomMovement;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private float mTargetPosition;
    
    private float mSpeed;
    
    public RepositionPlatformMovement(PlatformMovementData movementData, Vector2 initialPosition,
            AssetManager assetManager) {
        super(initialPosition, ResourceNames.PLATFORM_ENGINE_REPOSITION_TEXTURE,
                ResourceNames.PARTICLE_ENGINE_REPOSITION, assetManager);
        
        mRange = Float.parseFloat(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
        
        mIsRandomMovement = PlatformMovementData.REPOSITION_TYPE_RANDOM_PROPERTY_VALUE.equals(
                movementData.getProperty(PlatformMovementData.REPOSITION_TYPE_PROPERTY));
        
        mLeftLimit = initialPosition.x;
        mRightLimit = initialPosition.x + mRange;
        
        float initialOffset = Float.parseFloat(movementData.getProperty(PlatformMovementData.INITIAL_OFFSET_PROPERTY));
        if (initialOffset <= mRange) {
            changePosition(initialOffset);
        } else {
            changePosition(mRange - initialOffset % mRange);
        }
        
        mTargetPosition = mPosition.x;
        mSpeed = 0.0f;
    }
    
    @Override
    public void updatePosition(float delta) {
        if (mTargetPosition == mPosition.x) {
            return;
        }
        
        float travelled = mSpeed * delta;
        float distance = mTargetPosition - mPosition.x;
        if (Math.abs(distance) <= travelled) {
            setPosition(mTargetPosition);
        } else {
            changePosition(Math.signum(distance) * travelled);
        }
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.spriteColor.set(0.5f, 0.0f, 0.0f, 1.0f);
    }
    
    @Override
    public void applyContact(CollisionEffects collisionEffects) {
        collisionEffects.set(CollisionEffects.REPOSITION_PLATFORMS);
    }
    
    @Override
    public void applyEffect(int collisionEffect) {
        if (collisionEffect == CollisionEffects.REPOSITION_PLATFORMS) {
            reposition();
        }
    }
    
    private void changePosition(float change) {
        mPosition.x += change;
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
    
    private void setPosition(float position) {
        mPosition.x = MathUtils.clamp(position, mLeftLimit, mRightLimit);
    }
    
    private void reposition() {
        if (mIsRandomMovement) {
            mTargetPosition = MathUtils.random(mLeftLimit, mRightLimit);
        } else {
            if (mPosition.x == mLeftLimit) {
                mTargetPosition = mRightLimit;
            } else {
                mTargetPosition = mLeftLimit;
            }
        }
        
        mSpeed = Math.abs(mTargetPosition - mPosition.x) / REPOSITION_TIME;
    }
    
    @Override
    public boolean hasVerticalMovement() {
        return false;
    }
}
