package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.util.GameUtils;

final class EndCharacterState extends CharacterStateBase {
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_DURATION = 4.0f;
    
    private float mEndCountdown;
    
    public EndCharacterState(CharacterStateManager characterStateManager) {
        super(characterStateManager);
    }
    
    @Override
    public void reset() {
        mEndCountdown = END_DURATION;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mEndCountdown <= 0.0f) {
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
        
        mEndCountdown -= delta;
    }
    
    @Override
    public boolean isFinished() {
        return mEndCountdown <= 0.0f;
    }
}
