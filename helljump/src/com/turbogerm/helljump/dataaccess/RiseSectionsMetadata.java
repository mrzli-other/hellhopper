package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.utils.Array;

public final class RiseSectionsMetadata {
    
    private static final int INITIAL_SELECTION_LIST_CAPACITY = 20;
    
    private final Array<RiseSectionMetadata> mRiseSectionMetadataList;
    
    private final Array<RiseSectionMetadata> mSelectionList;
    
    public RiseSectionsMetadata(Array<RiseSectionMetadata> riseSectionMetadataList) {
        mRiseSectionMetadataList = riseSectionMetadataList;
        
        mSelectionList = new Array<RiseSectionMetadata>(INITIAL_SELECTION_LIST_CAPACITY);
    }
    
    public Array<RiseSectionMetadata> getAllRiseSections() {
        return mRiseSectionMetadataList;
    }
    
    public int getRiseSectionCount() {
        return mRiseSectionMetadataList.size;
    }
    
    public RiseSectionMetadata getByName(String name) {
        for (RiseSectionMetadata riseSection : mRiseSectionMetadataList) {
            if (riseSection.getName().equals(name)) {
                return riseSection;
            }
        }
        
        return null;
    }
    
    public RiseSectionMetadata getRandomRiseSection(String generatorType, int minDifficulty, int maxDifficulty) {
        if (maxDifficulty < 0) {
            return null;
        }
        
        mSelectionList.clear();
        
        for (RiseSectionMetadata riseSection : mRiseSectionMetadataList) {
            int difficulty = riseSection.getDifficulty();
            if (riseSection.getGeneratorType().equals(generatorType) && minDifficulty <= difficulty && difficulty <= maxDifficulty) {
                mSelectionList.add(riseSection);
            }
        }
        
        if (mSelectionList.size > 0) {
            return mSelectionList.random();
        } else {
            return getRandomRiseSection(generatorType, minDifficulty - 1, minDifficulty - 1); 
        }
    }
}
