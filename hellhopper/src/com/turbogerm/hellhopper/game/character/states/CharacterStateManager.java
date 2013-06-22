package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;

public final class CharacterStateManager {
    
    public static final String NORMAL_CHARACTER_STATE = "normal";
    public static final String END_CHARACTER_STATE = "end";
    public static final String DYING_FALL_CHARACTER_STATE = "dyingfall";
    public static final String DYING_ENEMY_CHARACTER_STATE = "dyingenemy";
    public static final String DYING_FIRE_CHARACTER_STATE = "dyingfire";
    
    private static final int NUM_CHARACTER_STATES = 5;
    
    private final ObjectMap<String, CharacterStateBase> mCharacterStates;
    
    private CharacterStateBase mCurrentState;
    
    public CharacterStateManager(AssetManager assetManager) {
        mCharacterStates = new ObjectMap<String, CharacterStateBase>(NUM_CHARACTER_STATES);
        mCharacterStates.put(NORMAL_CHARACTER_STATE, new NormalCharacterState(this, assetManager));
        mCharacterStates.put(END_CHARACTER_STATE, new EndCharacterState(this));
        mCharacterStates.put(DYING_FALL_CHARACTER_STATE, new DyingFallCharacterState(this));
        mCharacterStates.put(DYING_ENEMY_CHARACTER_STATE, new DyingEnemyCharacterState(this));
        mCharacterStates.put(DYING_FIRE_CHARACTER_STATE, new DyingFireCharacterState(this));
    }
    
    public void reset() {
        for (CharacterStateBase characterState : mCharacterStates.values()) {
            characterState.reset();
        }
        
        changeState(NORMAL_CHARACTER_STATE);
    }
    
    public void changeState(String state) {
        mCurrentState = mCharacterStates.get(state);
    }
    
    public CharacterStateBase getCurrentState() {
        return mCurrentState;
    }
}
