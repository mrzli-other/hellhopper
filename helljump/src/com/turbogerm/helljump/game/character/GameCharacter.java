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
package com.turbogerm.helljump.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.game.PlatformToCharCollisionData;
import com.turbogerm.helljump.game.RiseSection;
import com.turbogerm.helljump.game.character.states.CharacterStateManager;
import com.turbogerm.helljump.game.character.states.CharacterStateRenderData;
import com.turbogerm.helljump.game.character.states.CharacterStateUpdateData;
import com.turbogerm.helljump.game.enemies.EnemyBase;
import com.turbogerm.helljump.game.items.ItemBase;
import com.turbogerm.helljump.game.platforms.PlatformBase;

public final class GameCharacter {
    
    public static final float WIDTH = 1.0f;
    public static final float HEIGHT = 1.5f;
    public static final float CHARACTER_CENTER_X_OFFSET = WIDTH / 2.0f;
    public static final float COLLISION_WIDTH = WIDTH * 0.6f;
    public static final float COLLISION_WIDTH_OFFSET = (WIDTH - COLLISION_WIDTH) / 2.0f;
    public static final float COLLISION_LINE_LENGTH = COLLISION_WIDTH + COLLISION_WIDTH_OFFSET;
    
    public static final float JUMP_SPEED = 21.25f;
    public static final float GRAVITY = 35.0f;
    
    private final Vector2 mPosition;
    private final Vector2 mSpeed;
    
    private float mRiseHeight;
    
    private final CharacterStateManager mCharacterStateManager;
    private final CharacterEffects mCharacterEffects;
    
    private final CharacterStateUpdateData mCharacterStateUpdateData;
    private final CharacterStateRenderData mCharacterStateRenderData;
    
    public GameCharacter(CameraData cameraData, AssetManager assetManager) {
        
        mPosition = new Vector2();
        mSpeed = new Vector2();
        
        mCharacterStateManager = new CharacterStateManager(cameraData, assetManager);
        mCharacterEffects = new CharacterEffects();
        
        mCharacterStateUpdateData = new CharacterStateUpdateData();
        mCharacterStateRenderData = new CharacterStateRenderData();
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mPosition.set(GameArea.GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mSpeed.set(0.0f, JUMP_SPEED);
        
        mCharacterStateManager.reset();
        mCharacterEffects.reset();
    }
    
    public void update(float horizontalSpeed,
            PlatformToCharCollisionData platformToCharCollisionData,
            Array<RiseSection> activeRiseSections,
            Array<PlatformBase> visiblePlatforms,
            Array<EnemyBase> visibleEnemies,
            Array<ItemBase> visibleItems,
            float visibleAreaPosition,
            float delta) {
        
        mCharacterStateUpdateData.characterPosition = mPosition;
        mCharacterStateUpdateData.characterSpeed = mSpeed;
        mCharacterStateUpdateData.horizontalSpeed = horizontalSpeed;
        mCharacterStateUpdateData.platformToCharCollisionData = platformToCharCollisionData;
        mCharacterStateUpdateData.activeRiseSections = activeRiseSections;
        mCharacterStateUpdateData.visiblePlatforms = visiblePlatforms;
        mCharacterStateUpdateData.visibleEnemies = visibleEnemies;
        mCharacterStateUpdateData.visibleItems = visibleItems;
        mCharacterStateUpdateData.riseHeight = mRiseHeight;
        mCharacterStateUpdateData.visibleAreaPosition = visibleAreaPosition;
        mCharacterStateUpdateData.characterEffects = mCharacterEffects;
        mCharacterStateUpdateData.delta = delta;
        
        mCharacterStateManager.getCurrentState().update(mCharacterStateUpdateData);
    }
    
    public void render(SpriteBatch batch) {
        mCharacterStateRenderData.batch = batch;
        mCharacterStateRenderData.characterPosition = mPosition;
        mCharacterStateRenderData.characterEffects = mCharacterEffects;
        
        mCharacterStateManager.getCurrentState().render(mCharacterStateRenderData);
    }
    
    public boolean isFinished() {
        return mCharacterStateManager.getCurrentState().isFinished();
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }
    
    public Vector2 getSpeed() {
        return mSpeed;
    }
    
    public int getScore() {
        return mCharacterEffects.getScore();
    }
    
    public int getLives() {
        return mCharacterEffects.getLives();
    }
}
