package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.utils.Array;

public final class RiseSectionsData {
    
    private final Array<RiseSectionData> mRiseSections;
    
    public RiseSectionsData(Array<RiseSectionData> riseSections) {
        mRiseSections = riseSections;
    }
    
    public RiseSectionData getRiseSection(String name) {
        
        for (RiseSectionData riseSectionData : mRiseSections) {
            if (riseSectionData.getName().equals(name)) {
                return riseSectionData;
            }
        }
        
        return null;
    }
    
    public Array<RiseSectionData> getAllRiseSections() {
        return mRiseSections;
    }
    
    public int getRiseSectionCount() {
        return mRiseSections.size;
    }
}
