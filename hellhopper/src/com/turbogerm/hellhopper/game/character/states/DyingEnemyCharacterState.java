package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterEyesGraphicsStunned;
import com.turbogerm.hellhopper.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class DyingEnemyCharacterState extends CharacterStateBase {
    
    private static final float DYING_STATIC_DURATION = 0.6f;
    private static final float DYING_MOVING_DURATION = 3.0f;
    private static final float DYING_TOTAL_DURATION = DYING_STATIC_DURATION + DYING_MOVING_DURATION;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsStunned mCharacterEyesGraphics;
    
    private float mDyingElapsed;
    private boolean mIsStaticPhaseStarted;
    private boolean mIsMovingPhaseStarted;
    private float mHorizontalSpeed;
    
    private final Sound mSawSound;
    private final Sound mEnemySound;
    
    private boolean mIsSawDeath;
    
    public DyingEnemyCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsStunned(assetManager);
        
        mSawSound = assetManager.get(ResourceNames.SOUND_SAW);
        mEnemySound = assetManager.get(ResourceNames.SOUND_ENEMY);
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        
        mDyingElapsed = 0.0f;
        mIsStaticPhaseStarted = false;
        mIsMovingPhaseStarted = false;
        mHorizontalSpeed = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mDyingElapsed >= DYING_TOTAL_DURATION) {
            changeState(CharacterStateManager.FINISHED_CHARACTER_STATE);
            return;
        }
        
        if (!mIsStaticPhaseStarted) {
            mHorizontalSpeed = updateData.horizontalSpeed;
            mIsStaticPhaseStarted = true;
        }
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        float delta = updateData.delta;
        
        if (mDyingElapsed >= DYING_STATIC_DURATION) {
            if (!mIsMovingPhaseStarted) {
                speed.y = 0.0f;
                mIsMovingPhaseStarted = true;
            } else {
                updatePositionAndSpeed(position, speed, mHorizontalSpeed, delta);
            }
        }
        
        mDyingElapsed += delta;
        
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
        mIsSawDeath = changeData.getData(CharacterStateChangeData.IS_SAW_KEY);
        if (mIsSawDeath) {
            mSawSound.play();
        } else {
            mEnemySound.play();
        }
    }
    
    @Override
    public void end() {
        if (mIsSawDeath) {
            mSawSound.stop();
        } else {
            mEnemySound.stop();
        }
    }
}
