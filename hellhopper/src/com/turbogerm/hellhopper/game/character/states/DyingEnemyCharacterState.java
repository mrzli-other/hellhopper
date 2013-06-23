package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;

final class DyingEnemyCharacterState extends CharacterStateBase {
    
    private static final float DYING_STATIC_DURATION = 0.6f;
    private static final float DYING_MOVING_DURATION = 3.0f;
    private static final float DYING_TOTAL_DURATION = DYING_STATIC_DURATION + DYING_MOVING_DURATION;
    
    private float mDyingElapsed;
    private boolean mIsStaticPhaseStarted;
    private boolean mIsMovingPhaseStarted;
    private float mHorizontalSpeed;
    
    public DyingEnemyCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
    }
    
    @Override
    public void reset() {
        mDyingElapsed = 0.0f;
        mIsStaticPhaseStarted = false;
        mIsMovingPhaseStarted = false;
        mHorizontalSpeed = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (isFinished()) {
            return;
        }
        
        if (!mIsStaticPhaseStarted) {
            mHorizontalSpeed = updateData.horizontalSpeed;
            mIsStaticPhaseStarted = true;
        }
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        float delta = updateData.delta;
        
        if (mDyingElapsed >= DYING_STATIC_DURATION) {
            if (!mIsMovingPhaseStarted) {
                speed.y = 0.0f;
                mIsMovingPhaseStarted = true;
            } else {
                updatePositionAndSpeed(position, speed, mHorizontalSpeed, delta);
            }
        }
        
        mDyingElapsed += delta;
    }
    
    @Override
    public boolean isFinished() {
        return mDyingElapsed >= DYING_TOTAL_DURATION;
    }
}
