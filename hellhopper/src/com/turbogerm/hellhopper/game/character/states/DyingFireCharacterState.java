package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.ColorInterpolator;
import com.turbogerm.hellhopper.game.character.graphics.CharacterBodyGraphics;
import com.turbogerm.hellhopper.game.character.graphics.CharacterEyesGraphicsStunned;
import com.turbogerm.hellhopper.game.character.graphics.CharacterHeadGraphics;
import com.turbogerm.hellhopper.resources.ResourceNames;


final class DyingFireCharacterState extends CharacterStateBase {
    
    private static final float CHARRING_DURATION = 2.0f;
    private static final float CHARRED_DURATION = 1.0f;
    private static final float DYING_TOTAL_DURATION = CHARRING_DURATION + CHARRED_DURATION;
    
    private static final Color CHARRED_COLOR;
    
    private final CharacterBodyGraphics mCharacterBodyGraphics;
    private final CharacterHeadGraphics mCharacterHeadGraphics;
    private final CharacterEyesGraphicsStunned mCharacterEyesGraphics;
    
    private float mDyingElapsed;
    
    private final ColorInterpolator mColorInterpolator;
    
    private final Sound mFireSound;
    
    static {
        CHARRED_COLOR = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    }
    
    public DyingFireCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mCharacterBodyGraphics = new CharacterBodyGraphics(assetManager);
        mCharacterHeadGraphics = new CharacterHeadGraphics(assetManager);
        mCharacterEyesGraphics = new CharacterEyesGraphicsStunned(assetManager);
        
        mColorInterpolator = new ColorInterpolator();
        
        mFireSound = assetManager.get(ResourceNames.SOUND_FIRE);
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
        
        if (mDyingElapsed >= DYING_TOTAL_DURATION) {
            changeState(CharacterStateManager.FINISHED_CHARACTER_STATE);
            return;
        }
        
        mDyingElapsed += updateData.delta;
        
        float charringFraction = MathUtils.clamp(mDyingElapsed / CHARRED_DURATION, 0.0f, 1.0f);
        mCharacterBodyGraphics.setColor(mColorInterpolator.interpolateColor(
                CharacterBodyGraphics.DEFAULT_COLOR, CHARRED_COLOR, charringFraction));
        mCharacterHeadGraphics.setColor(mColorInterpolator.interpolateColor(
                CharacterHeadGraphics.DEFAULT_COLOR, CHARRED_COLOR, charringFraction));
        
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
        mFireSound.play();
    }
    
    @Override
    public void end() {
        mFireSound.stop();
    }
}
