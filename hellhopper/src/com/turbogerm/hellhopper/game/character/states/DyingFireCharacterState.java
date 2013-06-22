package com.turbogerm.hellhopper.game.character.states;


final class DyingFireCharacterState extends CharacterStateBase {
    
    private static final float DYING_DURATION = 3.0f;
    
    private float mDyingElapsed;
    
    public DyingFireCharacterState(CharacterStateManager characterStateManager) {
        super(characterStateManager);
    }
    
    @Override
    public void reset() {
        mDyingElapsed = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (isFinished()) {
            return;
        }
        
        mDyingElapsed += updateData.delta;
    }
    
    @Override
    public boolean isFinished() {
        return mDyingElapsed >= DYING_DURATION;
    }
}
