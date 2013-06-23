package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterEyesGraphicsStunned;
import com.turbogerm.hellhopper.game.character.graphics.CharacterHeadGraphics;


final class DyingFireCharacterState extends CharacterStateBase {
    
    private static final float DYING_DURATION = 3.0f;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsStunned mCharacterEyesGraphics;
    
    private float mDyingElapsed;
    
    public DyingFireCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsStunned(assetManager);
    }
    
    @Override
    public void reset() {
        mCharacterBodyGraphics.reset();
        mCharacterHeadGraphics.reset();
        mCharacterEyesGraphics.reset();
        
        mDyingElapsed = 0.0f;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        if (isFinished()) {
            return;
        }
        
        mDyingElapsed += updateData.delta;
        
        mCharacterEyesGraphics.update(updateData.delta);
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        mCharacterBodyGraphics.render(batch, characterPosition);
        mCharacterHeadGraphics.render(batch, characterPosition);
        mCharacterEyesGraphics.render(batch, characterPosition);
    }
    
    @Override
    public boolean isFinished() {
        return mDyingElapsed >= DYING_DURATION;
    }
}
