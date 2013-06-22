package com.turbogerm.hellhopper.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.character.GameCharacter;

public final class DebugData {
    
    private int mFps;
    private int mRenderCalls;
    private int mMaxSpritesInBatch;
    private String mRiseSectionName;
    private int mRiseSectionDifficulty;
    private int mDeathCount;
    
    public DebugData() {
    }
    
    public void update(SpriteBatch batch, RiseSection riseSection, GameCharacter character) {
        mFps = Gdx.graphics.getFramesPerSecond();
        mRenderCalls = batch.renderCalls;
        mMaxSpritesInBatch = batch.maxSpritesInBatch;
        if (riseSection != null) {
            mRiseSectionName = riseSection.getName();
            mRiseSectionDifficulty = riseSection.getDifficulty();
        } else {
            mRiseSectionName = "";
            mRiseSectionDifficulty = -1;
        }
        // TODO: remove
        mDeathCount = 0;//character.deathCount;
    }
    
    @Override
    public String toString() {
        return String.format("Fps: %d; Calls: %d; Max Batch: %d\nSection: %s\nDiffic: %d; Deaths: %d",
                mFps, mRenderCalls, mMaxSpritesInBatch, mRiseSectionName, mRiseSectionDifficulty, mDeathCount);
    }
}
