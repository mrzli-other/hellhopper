package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterEyesGraphicsNormal;
import com.turbogerm.hellhopper.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.hellhopper.util.GameUtils;

final class EndCharacterState extends CharacterStateBase {
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_DURATION = 4.0f;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsNormal mCharacterEyesGraphics;
    
    private float mEndCountdown;
    
    public EndCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsNormal(assetManager);
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        
        mEndCountdown = END_DURATION;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (mEndCountdown <= 0.0f) {
            return;
        }
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        float riseHeight = updateData.riseHeight;
        float delta = updateData.delta;
        
        if (position.y <= riseHeight) {
            position.y = riseHeight + GameUtils.EPSILON;
            speed.y = Math.abs(speed.y * END_RESTITUTION_MULTIPLIER);
            speed.y = Math.max(speed.y - END_RESTITUTION_SPEED_DECREASE, 0.0f);
        }
        
        updatePositionAndSpeed(position, speed, updateData.horizontalSpeed, delta);
        
        mEndCountdown -= delta;
        
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
    public boolean isFinished() {
        return mEndCountdown <= 0.0f;
    }
}
