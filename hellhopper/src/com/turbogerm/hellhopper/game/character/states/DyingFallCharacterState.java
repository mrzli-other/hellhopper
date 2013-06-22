package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.math.Vector2;

final class DyingFallCharacterState extends CharacterStateBase {
    
    private static final float FALL_STOP_LIMIT = 3.0f;
    private static final float DYING_DURATION = 2.0f;
    
    private float mDyingCountdown;
    
    public DyingFallCharacterState(CharacterStateManager characterStateManager) {
        super(characterStateManager);
    }
    
    @Override
    public void reset() {
        mDyingCountdown = DYING_DURATION;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (isFinished()) {
            return;
        }
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        float visibleAreaPosition = updateData.visibleAreaPosition;
        float delta = updateData.delta;
        
        if (position.y > visibleAreaPosition - FALL_STOP_LIMIT) {
            updatePositionAndSpeed(position, speed, updateData.horizontalSpeed, delta);
        }
        
        mDyingCountdown -= delta;
    }
    
    @Override
    public boolean isFinished() {
        return mDyingCountdown <= 0.0f;
    }
}
