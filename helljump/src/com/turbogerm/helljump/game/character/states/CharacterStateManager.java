package com.turbogerm.helljump.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.helljump.CameraData;

public final class CharacterStateManager {
    
    public static final String NORMAL_CHARACTER_STATE = "normal";
    public static final String END_CHARACTER_STATE = "end";
    public static final String DYING_FALL_CHARACTER_STATE = "dyingfall";
    public static final String DYING_ENEMY_CHARACTER_STATE = "dyingenemy";
    public static final String DYING_FIRE_CHARACTER_STATE = "dyingfire";
    public static final String FINISHED_CHARACTER_STATE = "finished";
    
    private static final int NUM_CHARACTER_STATES = 5;
    
    private final ObjectMap<String, CharacterStateBase> mCharacterStates;
    
    private CharacterStateBase mCurrentState;
    
    public CharacterStateManager(CameraData cameraData, AssetManager assetManager) {
        mCharacterStates = new ObjectMap<String, CharacterStateBase>(NUM_CHARACTER_STATES);
        mCharacterStates.put(NORMAL_CHARACTER_STATE, new NormalCharacterState(this, assetManager));
        mCharacterStates.put(END_CHARACTER_STATE, new EndCharacterState(this, cameraData, assetManager));
        mCharacterStates.put(DYING_FALL_CHARACTER_STATE, new DyingFallCharacterState(this, assetManager));
        mCharacterStates.put(DYING_ENEMY_CHARACTER_STATE, new DyingEnemyCharacterState(this, assetManager));
        mCharacterStates.put(DYING_FIRE_CHARACTER_STATE, new DyingFireCharacterState(this, assetManager));
        mCharacterStates.put(FINISHED_CHARACTER_STATE, new FinishedCharacterState(this));
    }
    
    public void reset() {
        for (CharacterStateBase characterState : mCharacterStates.values()) {
            characterState.reset();
        }
        
        mCurrentState = mCharacterStates.get(NORMAL_CHARACTER_STATE);
        mCurrentState.start(null);
    }
    
    public void changeState(String state) {
        changeState(state, null);
    }
    
    public void changeState(String state, CharacterStateChangeData changeData) {
        mCurrentState.end();
        mCurrentState = mCharacterStates.get(state);
        mCurrentState.start(changeData);
    }
    
    public CharacterStateBase getCurrentState() {
        return mCurrentState;
    }
}
