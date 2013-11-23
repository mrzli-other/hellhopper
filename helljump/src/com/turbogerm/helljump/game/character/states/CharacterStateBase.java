/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
