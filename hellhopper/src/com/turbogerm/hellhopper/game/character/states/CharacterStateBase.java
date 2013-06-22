package com.turbogerm.hellhopper.game.character.states;

public abstract class CharacterStateBase {
    
    private final CharacterStateManager mCharacterStateManager;
    
    public CharacterStateBase(CharacterStateManager characterStateManager) {
        mCharacterStateManager = characterStateManager;
    }
    
    public void reset() {
    }
    
    public void update(CharacterStateUpdateData updateData) {
    }
    
    protected void changeState(String state) {
        mCharacterStateManager.changeState(state);
    }
}
