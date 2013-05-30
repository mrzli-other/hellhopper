/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.game.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.dataaccess.RiseSectionData;
import com.turbogerm.hellhopper.dataaccess.RiseSectionMetadata;
import com.turbogerm.hellhopper.dataaccess.RiseSectionMetadataReader;
import com.turbogerm.hellhopper.dataaccess.RiseSectionsData;
import com.turbogerm.hellhopper.dataaccess.RiseSectionsDataReader;
import com.turbogerm.hellhopper.dataaccess.RiseSectionsMetadata;
import com.turbogerm.hellhopper.game.Rise;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.game.platforms.PlatformFactory;

public final class RiseGenerator {
    
    private static final int RISE_HEIGHT_STEPS = 5000;
    
    private static final int RISE_SECTIONS_INITIAL_CAPACITY = 20;
    private static final int INITIAL_RISE_SECTION_DESCRIPTOR_SELECTION_LIST_CAPACITY = 20;
    
    private static final RiseSectionsData PREBUILT_RISE_SECTIONS;
    private static final RiseSectionsMetadata RISE_SECTIONS_METADATA;
    private static final Array<RiseSectionDescriptor> ALL_RISE_SECTION_DESCRIPTORS;
    
    private static final Array<RiseSectionDescriptor> mRiseSectionDescriptorSelectionList;
    
    static {
        PREBUILT_RISE_SECTIONS = RiseSectionsDataReader.read(Gdx.files.internal(ResourceNames.RISE_SECTIONS_DATA));
        RISE_SECTIONS_METADATA = RiseSectionMetadataReader.read(
                Gdx.files.internal(ResourceNames.RISE_SECTIONS_METADATA));
        
        int allRiseSectionsCount = PREBUILT_RISE_SECTIONS.getRiseSectionCount() +
                RISE_SECTIONS_METADATA.getRiseSectionCount();
        ALL_RISE_SECTION_DESCRIPTORS = new Array<RiseSectionDescriptor>(true, allRiseSectionsCount);
        
        Array<RiseSectionData> prebuiltRiseSections = PREBUILT_RISE_SECTIONS.getAllRiseSections();
        for (RiseSectionData riseSectionData : prebuiltRiseSections) {
            RiseSectionDescriptor riseSectionDescriptor = new RiseSectionDescriptor(
                    false, null, riseSectionData.getName(), riseSectionData.getDifficulty());
            ALL_RISE_SECTION_DESCRIPTORS.add(riseSectionDescriptor);
        }
        
        Array<RiseSectionMetadata> riseSectionsMetadata = RISE_SECTIONS_METADATA.getAllRiseSections();
        for (RiseSectionMetadata riseSectionMetadata : riseSectionsMetadata) {
            RiseSectionDescriptor riseSectionDescriptor = new RiseSectionDescriptor(
                    true, riseSectionMetadata.getType(), riseSectionMetadata.getName(),
                    riseSectionMetadata.getDifficulty());
            ALL_RISE_SECTION_DESCRIPTORS.add(riseSectionDescriptor);
        }
        
        mRiseSectionDescriptorSelectionList = new Array<RiseSectionDescriptor>(
                true, INITIAL_RISE_SECTION_DESCRIPTOR_SELECTION_LIST_CAPACITY);
    }
    
    public static Rise generate(AssetManager assetManager) {
        Array<RiseSectionData> riseSectionsData = new Array<RiseSectionData>(true, RISE_SECTIONS_INITIAL_CAPACITY);
        
        // riseSectionsData.add(PREBUILT_RISE_SECTIONS.getRiseSection("test"));
        
        int stepsInRise = 0;
        RiseSectionData currRiseSection;
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("visibleonjump"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial0"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial1"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial2"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        while (stepsInRise < RISE_HEIGHT_STEPS) {
            currRiseSection = getRandomRiseSection(stepsInRise);
            riseSectionsData.add(currRiseSection);
            stepsInRise += currRiseSection.getStepRange();
        }
        
        Array<RiseSection> riseSections = getRiseSections(riseSectionsData, assetManager);
        
        return new Rise(riseSections);
    }
    
    private static RiseSectionData getRandomRiseSection(int stepsInRise) {
        int minDifficulty = MathUtils.clamp(stepsInRise / 500, 1, 4);
        int maxDifficulty = Math.max(stepsInRise / 500, 1);
        
        RiseSectionDescriptor riseSectionDescriptor = getRandomRiseSectionDescriptor(minDifficulty, maxDifficulty);
        RiseSectionData riseSectionData = getRiseSectionData(riseSectionDescriptor);
        
        return riseSectionData;
    }
    
    private static RiseSectionDescriptor getRandomRiseSectionDescriptor(int minDifficulty, int maxDifficulty) {
        if (maxDifficulty < 0) {
            return null;
        }
        
        mRiseSectionDescriptorSelectionList.clear();
        
        for (RiseSectionDescriptor riseSectionDescriptor : ALL_RISE_SECTION_DESCRIPTORS) {
            int difficulty = riseSectionDescriptor.getDifficulty();
            if (minDifficulty <= difficulty && difficulty <= maxDifficulty) {
                mRiseSectionDescriptorSelectionList.add(riseSectionDescriptor);
            }
        }
        
        if (mRiseSectionDescriptorSelectionList.size > 0) {
            return mRiseSectionDescriptorSelectionList.random();
        } else {
            return getRandomRiseSectionDescriptor(minDifficulty - 1, minDifficulty - 1); 
        }
    }
    
    private static RiseSectionData getRiseSectionData(RiseSectionDescriptor riseSectionDescriptor) {
        
        if (riseSectionDescriptor.isMetadata()) {
            String type = riseSectionDescriptor.getType();
            int difficulty = riseSectionDescriptor.getDifficulty();
            RiseSectionMetadata riseSectionMetadata =
                    RISE_SECTIONS_METADATA.getRandomRiseSection(type, difficulty, difficulty);
            return RiseSectionGenerator.generateRiseSection(riseSectionMetadata);
        } else {
            return PREBUILT_RISE_SECTIONS.getRiseSection(riseSectionDescriptor.getName());
        }
    }
    
    private static Array<RiseSection> getRiseSections(Array<RiseSectionData> riseSectionsData,
            AssetManager assetManager) {
        Array<RiseSection> riseSections = new Array<RiseSection>(true, riseSectionsData.size);
        
        int id = 0;
        int startStep = 0;
        for (RiseSectionData riseSectionData : riseSectionsData) {
            RiseSection riseSection = getRiseSection(id, startStep, riseSectionData, assetManager);
            riseSections.add(riseSection);
            id++;
            startStep += riseSectionData.getStepRange();
        }
        
        return riseSections;
    }
    
    private static RiseSection getRiseSection(int riseSectionId, int startStep, RiseSectionData riseSectionData,
            AssetManager assetManager) {
        
        String riseSectionName = riseSectionData.getName();
        int difficulty = riseSectionData.getDifficulty();
        
        float startY = startStep * PlatformData.STEP_HEIGHT;
        float height = riseSectionData.getStepRange() * PlatformData.STEP_HEIGHT;
        
        Array<PlatformData> platformsData = riseSectionData.getPlatformDataList();
        Array<PlatformBase> platforms = new Array<PlatformBase>(true, platformsData.size);
        for (PlatformData platformData : platformsData) {
            PlatformBase platform = PlatformFactory.create(riseSectionId, platformData, startStep, assetManager);
            platforms.add(platform);
        }
        
        return new RiseSection(riseSectionId, riseSectionName, difficulty, startY, height, platforms);
    }
}
