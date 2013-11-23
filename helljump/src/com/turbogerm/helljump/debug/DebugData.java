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
