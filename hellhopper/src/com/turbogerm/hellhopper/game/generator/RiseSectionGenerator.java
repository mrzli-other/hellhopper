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

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.dataaccess.PlatformFeatureData;
import com.turbogerm.hellhopper.dataaccess.PlatformMovementData;
import com.turbogerm.hellhopper.dataaccess.RiseSectionData;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.util.GameUtils;

final class RiseSectionGenerator {
    
    private static final float FULL_HORIZONTAL_RANGE = GameArea.GAME_AREA_WIDTH - PlatformData.PLATFORM_WIDTH;
    
    public static RiseSectionData generateBasicRiseSection(int stepRange, int numPads, int difficulty,
            float jumpBoostChance) {
        Array<PlatformData> platformDataList = new Array<PlatformData>(numPads);
        Array<StepPossiblePlaformPositions> positions = getInitialAllStepPositionsPositions(stepRange);
        for (int i = 0; i < numPads; i++) {
            PlatformPosition position = getRandomPosition(positions);
            Array<PlatformFeatureData> featuresData = getFeaturesData(jumpBoostChance);
            PlatformData padData = new PlatformData(PlatformData.NORMAL, position.getStep(), position.getOffset(),
                    null, featuresData, null);
            platformDataList.add(padData);
            updatePossiblePlatformPositions(positions, position);
        }
        
        correctPlatformList(stepRange, platformDataList, jumpBoostChance);
        RiseGeneratorUtils.sort(platformDataList);
        
        return new RiseSectionData(stepRange, difficulty, platformDataList);
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
    
    private static PlatformPosition getRandomPosition(Array<StepPossiblePlaformPositions> allStepPositions) {
        int totalNumPositions = getTotalNumPositions(allStepPositions);
        int randomPositionIndex = MathUtils.random(totalNumPositions - 1);
        
        for (StepPossiblePlaformPositions stepPositions : allStepPositions) {
            int currentStepSize = stepPositions.getNumPositions();
            if (randomPositionIndex < currentStepSize) {
                return new PlatformPosition(
                        stepPositions.getStep(),
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
    
    private static Array<PlatformFeatureData> getFeaturesData(float jumpBoostChance) {
        Array<PlatformFeatureData> featuresData;
        if (MathUtils.random() <= jumpBoostChance) {
            featuresData = new Array<PlatformFeatureData>(true, 1);
            
            ObjectMap<String, String> properties = new ObjectMap<String, String>(2);
            properties.put(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY,
                    String.valueOf(MathUtils.random()));
            properties.put(PlatformFeatureData.JUMP_BOOST_POWER_PROPERTY,
                    PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE);
            
            PlatformFeatureData featureData = new PlatformFeatureData(PlatformFeatureData.JUMP_BOOST, properties);
            featuresData.add(featureData);
        } else {
            featuresData = null;
        }
        
        return featuresData;
    }
    
    private static void updatePossiblePlatformPositions(Array<StepPossiblePlaformPositions> allStepPositions,
            PlatformPosition takenPosition) {
        for (StepPossiblePlaformPositions stepPositions : allStepPositions) {
            if (stepPositions.getStep() == takenPosition.getStep()) {
                int firstInvalidatedOffset = takenPosition.getOffset() - PlatformData.PLATFORM_WIDTH_OFFSETS + 1;
                int numInvalidatedOffsets = PlatformData.PLATFORM_WIDTH_OFFSETS * 2 - 1;
                for (int i = 0; i < numInvalidatedOffsets; i++) {
                    stepPositions.removeOffsetValue(firstInvalidatedOffset + i);
                }
            }
        }
    }
    
    private static void correctPlatformList(int stepRange, Array<PlatformData> platformDataList,
            float jumpBoostChance) {
        int step = getFirstEmptyRequiredStep(stepRange, platformDataList);
        while (step != -1) {
            int offset = MathUtils.random(PlatformData.MAX_PLATFORM_OFFSET - 1);
            Array<PlatformFeatureData> featuresData = getFeaturesData(jumpBoostChance);
            PlatformData platformData = new PlatformData(PlatformData.NORMAL, step, offset, null,
                    featuresData, null);
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
    
    public static RiseSectionData generateRiseSection(
            int stepRange,
            int minStepDistance,
            int maxStepDistance,
            float movingPlatformFraction,
            float minSpeed,
            float maxSpeed,
            float crumblePlatformFraction,
            float jumpBoostChance,
            int difficulty) {
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        
        int movingPlatformCount = (int) (filledSteps.size * movingPlatformFraction);
        Array<Integer> movingPlatformIndexes = GameUtils.getRandomIndexes(filledSteps.size, movingPlatformCount);
        
        int crumblePlatformCount = (int) (filledSteps.size * crumblePlatformFraction);
        Array<Integer> crumblePlatformIndexes = GameUtils.getRandomIndexes(filledSteps.size, crumblePlatformCount);
        
        for (int i = 0; i < filledSteps.size; i++) {
            int step = filledSteps.get(i);
            
            PlatformMovementData movementData = getMovementData(
                    i, filledSteps, movingPlatformIndexes, minSpeed, maxSpeed);
            Array<PlatformFeatureData> featuresData = getFeaturesData(jumpBoostChance);
            
            int offset;
            if (movementData == null) {
                offset = MathUtils.random(PlatformData.MAX_PLATFORM_OFFSET);
            } else {
                offset = 0;
            }
            
            String platformType = crumblePlatformIndexes.contains(i, false) ?
                    PlatformData.CRUMBLE : PlatformData.NORMAL;
            
            PlatformData padData = new PlatformData(platformType, step, offset,
                    movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(stepRange, difficulty, platformDataList);
    }
    
    private static Array<Integer> getFilledSteps(int stepRange, int minStepDistance, int maxStepDistance) {
        Array<Integer> filledSteps = new Array<Integer>(true, stepRange);
        int currentStep = 0;
        while (currentStep < stepRange) {
            filledSteps.add(currentStep);
            currentStep += MathUtils.random(minStepDistance, maxStepDistance);;
        }
        
        return filledSteps;
    }
    
    private static PlatformMovementData getMovementData(int index,
            Array<Integer> filledSteps, Array<Integer> movingPlatformIndexes,
            float minSpeed, float maxSpeed) {
        
        PlatformMovementData movementData;
        if (movingPlatformIndexes.contains(index, false)) {
            ObjectMap<String, String> properties = new ObjectMap<String, String>(2);
            properties.put(PlatformMovementData.RANGE_PROPERTY,
                    String.valueOf(FULL_HORIZONTAL_RANGE));
            properties.put(PlatformMovementData.SPEED_PROPERTY,
                    String.valueOf(MathUtils.random(minSpeed, maxSpeed)));
            properties.put(PlatformMovementData.INITIAL_OFFSET_PROPERTY,
                    String.valueOf(MathUtils.random(0.0f, FULL_HORIZONTAL_RANGE * 2.0f)));
            
            movementData = new PlatformMovementData(PlatformMovementData.HORIZONTAL_MOVEMENT, properties);
        } else {
            movementData = null;
        }
        
        return movementData;
    }
}
