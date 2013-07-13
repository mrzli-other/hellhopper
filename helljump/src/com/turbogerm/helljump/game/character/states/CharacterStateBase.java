package com.turbogerm.helljump.game.character.states;

import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.game.character.GameCharacter;

public abstract class CharacterStateBase {
    
    private final CharacterStateManager mCharacterStateManager;
    
    public CharacterStateBase(CharacterStateManager characterStateManager) {
        mCharacterStateManager = characterStateManager;
    }
    
    public void reset() {
    }
    
    public void update(CharacterStateUpdateData updateData) {
    }
    
    public void render(CharacterStateRenderData renderData) {
    }
    
    public void start(CharacterStateChangeData changeData) {
    }
    
    public void end() {
    }
    
    public boolean isFinished() {
        return false;
    }
    
    protected void changeState(String state) {
        mCharacterStateManager.changeState(state);
    }
    
    protected void changeState(String state, CharacterStateChangeData changeData) {
        mCharacterStateManager.changeState(state, changeData);
    }
    
    protected static void updatePositionAndSpeed(
            Vector2 position, Vector2 speed,
            float horizontalSpeed, float delta) {
        
        updatePositionAndSpeed(position, speed, horizontalSpeed, 0.0f, GameArea.GAME_AREA_WIDTH, delta);
    }
    
    protected static void updatePositionAndSpeed(
            Vector2 position, Vector2 speed, float horizontalSpeed,
            float minPositionX, float rangeX, float delta) {
        
        position.x += speed.x * delta;
        position.y += speed.y * delta;
        speed.x = horizontalSpeed;
        speed.y = Math.max(speed.y - GameCharacter.GRAVITY * delta, -GameCharacter.JUMP_SPEED);
        
        float leftGoThroughtOffsetX = minPositionX - GameCharacter.CHARACTER_CENTER_X_OFFSET;
        position.x = GameUtils.getPositiveModulus(position.x - leftGoThroughtOffsetX, rangeX) + leftGoThroughtOffsetX;
    }
}
