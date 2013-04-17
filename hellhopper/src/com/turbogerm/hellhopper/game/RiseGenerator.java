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
package com.turbogerm.hellhopper.game;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.game.platforms.PlatformFactory;

public final class RiseGenerator {
    
    private static final int RISE_SECTIONS_INITIAL_CAPACITY = 20;
    
    private static final Comparator<PlatformData> PLATFORM_DATA_COMPARATOR;
    
    static {
        PLATFORM_DATA_COMPARATOR = new Comparator<PlatformData>() {
            @Override
            public int compare(PlatformData p1, PlatformData p2) {
                if (p1.getStep() < p2.getStep()) {
                    return -1;
                } else if (p1.getStep() > p2.getStep()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }
    
    public static Rise generate(AssetManager assetManager) {
        Array<RiseSectionData> riseSections = new Array<RiseSectionData>(true, RISE_SECTIONS_INITIAL_CAPACITY);
        //riseSections.add(generateRiseSection(50, 100));
        //riseSections.add(generateRiseSection(100, 100));
        
        RiseSectionData testRiseSection = RiseSectionDataReader.read(Gdx.files.internal(ResourceNames.RISE_SECTION_TEST));
        riseSections.add(testRiseSection);
        
        int totalNumPlatforms = 0;
        for (RiseSectionData riseSection : riseSections) {
            totalNumPlatforms += riseSection.getPlatformDataList().size;
        }
        
        Array<PlatformBase> platforms = new Array<PlatformBase>(true, totalNumPlatforms);
        int startStep = 0;
        for (RiseSectionData riseSection : riseSections) {
            addPlatforms(platforms, riseSection, startStep, assetManager);
            startStep += riseSection.getStepRange();
        }
        
        float riseHeight = startStep * PlatformData.STEP_HEIGHT;
        
        return new Rise(riseHeight, platforms);
    }
    
    public static void sort(Array<PlatformData> platformDataList) {
        platformDataList.sort(PLATFORM_DATA_COMPARATOR);
    }
    
    private static RiseSectionData generateRiseSection(int stepRange, int numPads) {
        Array<PlatformData> platformDataList = new Array<PlatformData>(numPads);
        Array<StepPossiblePlaformPositions> positions = getInitialAllStepPositionsPositions(stepRange);
        for (int i = 0; i < numPads; i++) {
            PlaformPosition position = getRandomPosition(positions);
            PlatformData padData = new PlatformData(PlatformData.NORMAL, position.step, position.offset, null);
            platformDataList.add(padData);
            updatePossiblePlatformPositions(positions, position);
        }
        
        correctPlatformList(stepRange, platformDataList);
        sort(platformDataList);
        
        return new RiseSectionData(stepRange, 0, platformDataList);
    }
    
    private static Array<StepPossiblePlaformPositions> getInitialAllStepPositionsPositions(int stepRange) {
        Array<StepPossiblePlaformPositions> positions = new Array<StepPossiblePlaformPositions>(true, stepRange);
        for (int i = 0; i < stepRange; i++) {
            StepPossiblePlaformPositions stepPositions = getInitialStepPositions(i);
            positions.add(stepPositions);
        }
        
        return positions;
    }
    
    private static StepPossiblePlaformPositions getInitialStepPositions(int step) {
        Array<Integer> positions = new Array<Integer>(true, PlatformData.MAX_PLATFORM_OFFSET + 1);
        for (int i = 0; i <= PlatformData.MAX_PLATFORM_OFFSET; i++) {
            positions.add(i);
        }
        
        return new StepPossiblePlaformPositions(step, positions);
    }
    
    private static PlaformPosition getRandomPosition(Array<StepPossiblePlaformPositions> allStepPositions) {
        int totalNumPositions = getTotalNumPositions(allStepPositions);
        int randomPositionIndex = MathUtils.random(totalNumPositions - 1);
        
        for (StepPossiblePlaformPositions stepPositions : allStepPositions) {
            int currentStepSize = stepPositions.getNumPositions();
            if (randomPositionIndex < currentStepSize) {
                return new PlaformPosition(
                        stepPositions.step,
                        stepPositions.getOffset(randomPositionIndex));
            }
            
            randomPositionIndex -= currentStepSize;
        }
        
        return null;
    }
    
    private static int getTotalNumPositions(Array<StepPossiblePlaformPositions> allStepPositions) {
        int totalNumPositions = 0;
        for (StepPossiblePlaformPositions stepPositions : allStepPositions) {
            totalNumPositions += stepPositions.getNumPositions();
        }
        
        return totalNumPositions;
    }
    
    private static void updatePossiblePlatformPositions(Array<StepPossiblePlaformPositions> allStepPositions,
            PlaformPosition takenPosition) {
        for (StepPossiblePlaformPositions stepPositions : allStepPositions) {
            if (stepPositions.step == takenPosition.step) {
                int firstInvalidatedOffset = takenPosition.offset - PlatformData.PLATFORM_WIDTH_OFFSETS + 1;
                int numInvalidatedOffsets = PlatformData.PLATFORM_WIDTH_OFFSETS * 2 - 1;
                for (int i = 0; i < numInvalidatedOffsets; i++) {
                    stepPositions.removeOffsetValue(firstInvalidatedOffset + i);
                }
            }
        }
    }
    
    private static void correctPlatformList(int stepRange, Array<PlatformData> platformDataList) {
        int step = getFirstEmptyRequiredStep(stepRange, platformDataList);
        while (step != -1) {
            int offset = MathUtils.random(PlatformData.MAX_PLATFORM_OFFSET - 1);
            PlatformData platformData = new PlatformData(PlatformData.NORMAL, step, offset, null);
            platformDataList.add(platformData);
            
            step = getFirstEmptyRequiredStep(stepRange, platformDataList);
        }
    }
    
    private static int getFirstEmptyRequiredStep(int stepRange, Array<PlatformData> platformDataList) {
        Array<Integer> stepsWithPlatforms = getStepsWithPlatforms(platformDataList);
        
        // step 0 must always be filled
        if (stepsWithPlatforms.size == 0 || stepsWithPlatforms.get(0) != 0) {
            return 0;
        }
        
        for (int i = 1; i < stepsWithPlatforms.size; i++) {
            if (stepsWithPlatforms.get(i) - stepsWithPlatforms.get(i - 1) > PlatformData.MAX_PLATFORM_DISTANCE_STEPS) {
                return stepsWithPlatforms.get(i - 1) + PlatformData.MAX_PLATFORM_DISTANCE_STEPS;
            }
        }
        
        if (stepRange - stepsWithPlatforms.peek() > PlatformData.MAX_PLATFORM_DISTANCE_STEPS) {
            return stepsWithPlatforms.peek() + PlatformData.MAX_PLATFORM_DISTANCE_STEPS;
        }
        
        return -1;
    }
    
    private static Array<Integer> getStepsWithPlatforms(Array<PlatformData> platformDataList) {
        Array<Integer> steps = new Array<Integer>(true, platformDataList.size);
        for (PlatformData padData : platformDataList) {
            int step = padData.getStep();
            if (!steps.contains(step, false)) {
                steps.add(step);
            }
        }
        
        steps.sort();
        
        return steps;
    }
    
    // private static Array<PadData> getPadsInStepsWithMultiplePads(Array<PadData> padDataList) {
    // Array<Integer> getStepsWithMultiplePads(Array<PadData> padDataList)
    // }
    //
    // private static Array<Integer> getStepsWithMultiplePads(Array<PadData> padDataList) {
    // Array<Integer> steps = new Array<Integer>(true, padDataList.size);
    // Array<Integer> stepsWithSinglePad = new Array<Integer>(true, padDataList.size);
    // for (PadData padData : padDataList) {
    // int step = padData.getOffsetY();
    // if (!steps.contains(step, false)) {
    // if (stepsWithSinglePad.contains(step, false)) {
    // steps.add(step);
    // } else {
    // stepsWithSinglePad.add(step);
    // }
    // }
    // }
    //
    // steps.sort();
    //
    // return steps;
    // }
    
    private static void addPlatforms(Array<PlatformBase> platforms, RiseSectionData riseSection, int startStep,
            AssetManager assetManager) {
        Array<PlatformData> platformDataList = riseSection.getPlatformDataList();
        for (PlatformData platformData : platformDataList) {
            PlatformBase platform = PlatformFactory.create(platformData, startStep, assetManager);
            platforms.add(platform);
        }
    }
    
    private static class StepPossiblePlaformPositions {
        private final int step;
        private final Array<Integer> offsets;
        
        public StepPossiblePlaformPositions(int step, Array<Integer> offsets) {
            this.step = step;
            this.offsets = offsets;
        }
        
        public int getNumPositions() {
            return offsets.size;
        }
        
        public int getOffset(int index) {
            return offsets.get(index);
        }
        
        public void removeOffsetValue(int value) {
            offsets.removeValue(value, false);
        }
    }
    
    private static class PlaformPosition {
        private final int step;
        private final int offset;
        
        public PlaformPosition(int step, int offset) {
            this.step = step;
            this.offset = offset;
        }
    }
}
