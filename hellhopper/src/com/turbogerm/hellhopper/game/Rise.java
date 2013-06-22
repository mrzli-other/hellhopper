package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.utils.Array;

public final class Rise {
    
    private final Array<RiseSection> mRiseSections;
    private final float mHeight;
    
    public Rise(Array<RiseSection> riseSections) {
        mRiseSections = riseSections;
        
        float height = 0.0f;
        for (RiseSection riseSection : riseSections) {
            height += riseSection.getHeight();
        }
        mHeight = height;
    }
    
    public float getHeight() {
        return mHeight;
    }
    
    public Array<RiseSection> getRiseSections() {
        return mRiseSections;
    }
}
