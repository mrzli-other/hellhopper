package com.turbogerm.helljump.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.helljump.game.character.graphics.CharacterEyesGraphicsNormal;
import com.turbogerm.helljump.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.helljump.resources.ResourceNames;

final class DyingFallCharacterState extends CharacterStateBase {
    
    private static final float FALL_STOP_LIMIT = 3.0f;
    private static final float DYING_DURATION = 2.0f;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsNormal mCharacterEyesGraphics;
    
    private float mDyingCountdown;
    
    private final Sound mFallSound;
    
    public DyingFallCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsNormal(assetManager);
        
        mFallSound = assetManager.get(ResourceNames.SOUND_FALL);
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        
        mDyingCountdown = DYING_DURATION;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mDyingCountdown <= 0.0f) {
            changeState(CharacterStateManager.FINISHED_CHARACTER_STATE);
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
        
        mCharacterEyesGraphics.update(updateData.delta);
    }
    
    @Override
    public void render(CharacterStateRenderData renderData) {
        SpriteBatch batch = renderData.batch;
        Vector2 position = renderData.characterPosition;
        
        mCharacterBodyGraphics.render(batch, position);
        mCharacterHeadGraphics.render(batch, position);
        mCharacterEyesGraphics.render(batch, position);
    }
    
    @Override
    public void start(CharacterStateChangeData changeData) {
        mFallSound.play();
    }
    
    @Override
    public void end() {
        mFallSound.stop();
    }
}