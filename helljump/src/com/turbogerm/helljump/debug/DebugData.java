package com.turbogerm.helljump.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.helljump.game.RiseSection;
import com.turbogerm.helljump.game.character.GameCharacter;

public final class DebugData {
    
    private int mFps;
    private int mRenderCalls;
    private int mMaxSpritesInBatch;
    private String mRiseSectionName;
    private int mRiseSectionDifficulty;
    
    public DebugData() {
    }
    
    public void update(SpriteBatch batch, RiseSection riseSection, GameCharacter character) {
        mFps = Gdx.graphics.getFramesPerSecond();
        mRenderCalls = batch.renderCalls;
        mMaxSpritesInBatch = batch.maxSpritesInBatch;
        batch.maxSpritesInBatch = 0;
        if (riseSection != null) {
            mRiseSectionName = riseSection.getName();
            mRiseSectionDifficulty = riseSection.getDifficulty();
        } else {
            mRiseSectionName = "";
            mRiseSectionDifficulty = -1;
        }
    }
    
    @Override
    public String toString() {
        return String.format("Fps: %d; Calls: %d; Max Batch: %d\nSection: %s\nDiffic: %d",
                mFps, mRenderCalls, mMaxSpritesInBatch, mRiseSectionName, mRiseSectionDifficulty);
    }
}
