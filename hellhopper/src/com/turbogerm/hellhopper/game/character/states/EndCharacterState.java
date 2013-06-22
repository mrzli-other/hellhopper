package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.util.GameUtils;

final class EndCharacterState extends CharacterStateBase {
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_REACHED_DURATION = 4.0f;
    
    private float mEndReachedCountdown;
    
    public EndCharacterState(CharacterStateManager characterStateManager) {
        super(characterStateManager);
    }
    
    @Override
    public void reset() {
        mEndReachedCountdown = END_REACHED_DURATION;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mEndReachedCountdown <= 0.0f) {
            return;
        }
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        float riseHeight = updateData.riseHeight;
        float delta = updateData.delta;
        
        if (position.y <= riseHeight) {
            position.y = riseHeight + GameUtils.EPSILON;
            speed.y = Math.abs(speed.y * END_RESTITUTION_MULTIPLIER);
            speed.y = Math.max(speed.y - END_RESTITUTION_SPEED_DECREASE, 0.0f);
        }
        
        updatePositionAndSpeed(position, speed, updateData.horizontalSpeed, delta);
        
        mEndReachedCountdown -= delta;
    }
    
    @Override
    public boolean isFinished() {
        return mEndReachedCountdown <= 0.0f;
    }
}
