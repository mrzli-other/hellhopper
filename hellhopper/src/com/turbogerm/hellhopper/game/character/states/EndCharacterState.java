package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.hellhopper.game.character.GameCharacter;
import com.turbogerm.hellhopper.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterEyesGraphicsNormal;
import com.turbogerm.hellhopper.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterMouthGraphicsSmile;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class EndCharacterState extends CharacterStateBase {
    
    private static final float FIRST_END_JUMP_POWER_MULTIPLIER = 1.0f;
    private static final float FIRST_END_JUMP_SPEED = GameCharacter.JUMP_SPEED * FIRST_END_JUMP_POWER_MULTIPLIER;
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    
    private static final float CHARACTER_STOPPED_DURATION = 4.0f;
    
    private static final float MIN_SHEEP_SOUND_INTERVAL = 0.5f;
    private static final float MAX_SHEEP_SOUND_INTERVAL = 3.0f;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsNormal mCharacterEyesGraphics;
    private final CharacterMouthGraphicsSmile mCharacterMouthGraphics;
    
    private boolean mIsFirstEndJump;
    private boolean mIsCharacterStopped;
    private float mCharacterStoppedCountdown;
    
    private final Sound[] mSheepSounds;
    
    private float mNextSheepSoundInteval;
    private float mElapsedSinceLastSheepSound;
    
    public EndCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsNormal(assetManager);
        mCharacterMouthGraphics = new CharacterMouthGraphicsSmile(assetManager);
        
        mSheepSounds = new Sound[ResourceNames.SOUND_SHEEP_COUNT];
        for (int i = 0; i < mSheepSounds.length; i++) {
            mSheepSounds[i] = assetManager.get(ResourceNames.getSoundSheep(i));
        }
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        mCharacterMouthGraphics.reset();
        
        mIsFirstEndJump = true;
        mIsCharacterStopped = false;
        mCharacterStoppedCountdown = CHARACTER_STOPPED_DURATION;
        mNextSheepSoundInteval = MathUtils.random(MIN_SHEEP_SOUND_INTERVAL, MAX_SHEEP_SOUND_INTERVAL);
        mElapsedSinceLastSheepSound = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mCharacterStoppedCountdown <= 0.0f) {
            return;
        }
        
        float delta = updateData.delta;
        
        if (!mIsCharacterStopped) {
            Vector2 position = updateData.characterPosition;
            Vector2 speed = updateData.characterSpeed;
            float riseHeight = updateData.riseHeight;
            
            if (position.y <= riseHeight) {
                position.y = riseHeight + GameUtils.EPSILON;
                if (mIsFirstEndJump) {
                    speed.y = FIRST_END_JUMP_SPEED;
                    mIsFirstEndJump = false;
                } else {
                    speed.y = Math.abs(speed.y * END_RESTITUTION_MULTIPLIER);
                    speed.y -= END_RESTITUTION_SPEED_DECREASE;
                    if (speed.y <= 0.0f) {
                        mIsCharacterStopped = true;
                    }
                }
            }
            
            if (!mIsCharacterStopped) {
                updatePositionAndSpeed(position, speed, updateData.horizontalSpeed, delta);
            }
        } else {
            mCharacterStoppedCountdown -= delta;
        }
        
        mCharacterEyesGraphics.update(delta);
        
        mElapsedSinceLastSheepSound += delta;
        if (mElapsedSinceLastSheepSound >= mNextSheepSoundInteval) {
            mSheepSounds[MathUtils.random(ResourceNames.SOUND_SHEEP_COUNT - 1)].play();
            mNextSheepSoundInteval = MathUtils.random(MIN_SHEEP_SOUND_INTERVAL, MAX_SHEEP_SOUND_INTERVAL);
            mElapsedSinceLastSheepSound = 0.0f;
        }
    }
    
    @Override
    public void render(CharacterStateRenderData renderData) {
        SpriteBatch batch = renderData.batch;
        Vector2 position = renderData.characterPosition;
        
        mCharacterBodyGraphics.render(batch, position);
        mCharacterHeadGraphics.render(batch, position);
        mCharacterEyesGraphics.render(batch, position);
        mCharacterMouthGraphics.render(batch, position);
    }
    
    @Override
    public boolean isFinished() {
        return mCharacterStoppedCountdown <= 0.0f;
    }
}
