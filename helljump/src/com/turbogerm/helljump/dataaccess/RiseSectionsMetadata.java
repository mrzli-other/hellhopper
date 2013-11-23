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
