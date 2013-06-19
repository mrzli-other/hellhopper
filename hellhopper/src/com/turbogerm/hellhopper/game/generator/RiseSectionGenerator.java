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
import com.turbogerm.hellhopper.dataaccess.RiseSectionMetadata;
import com.turbogerm.hellhopper.game.GameAreaUtils;
import com.turbogerm.hellhopper.util.ExceptionThrower;
import com.turbogerm.hellhopper.util.GameUtils;

final class RiseSectionGenerator {
    
    public static RiseSectionData generateRiseSection(RiseSectionMetadata riseSectionMetadata) {
        String generatorType = riseSectionMetadata.getGeneratorType();
        if (RiseSectionMetadata.BASIC_GENERATOR_TYPE.equals(generatorType)) {
            return generateRiseSectionBasic(riseSectionMetadata);
        } else if (RiseSectionMetadata.JUMP_BOOST_GENERATOR_TYPE.equals(generatorType)) {
            return generateRiseSectionJumpBoost(riseSectionMetadata);
        } else if (RiseSectionMetadata.VISIBLE_ON_JUMP_GENERATOR_TYPE.equals(generatorType)) {
            return generateRiseSectionVisibleOnJump(riseSectionMetadata);
        } else if (RiseSectionMetadata.CRUMBLE_GENERATOR_TYPE.equals(generatorType)) {
            return generateRiseSectionCrumble(riseSectionMetadata);
        } else {
            ExceptionThrower.throwException("Invalid rise section metadata generator type: %s", generatorType);
            return null;
        }
    }
    
    private static RiseSectionData generateRiseSectionBasic(RiseSectionMetadata riseSectionMetadata) {
        
        String type = riseSectionMetadata.getType();
        String name = riseSectionMetadata.getName();
        int stepRange = MathUtils.random(
                riseSectionMetadata.getMinStepRange(), riseSectionMetadata.getMaxStepRange());
        int minStepDistance = riseSectionMetadata.getMinStepDistance();
        int maxStepDistance = riseSectionMetadata.getMaxStepDistance();
        int difficulty = riseSectionMetadata.getDifficulty();
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        
        float normalPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.NORMAL_PLATFORM_WEIGHT_PROPERTY));
        float movingPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MOVING_PLATFORM_WEIGHT_PROPERTY));
        float repositionPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.REPOSITION_PLATFORM_WEIGHT_PROPERTY));
        
        float[] weights = new float[3];
        weights[0] = normalPlatformWeight;
        weights[1] = movingPlatformWeight;
        weights[2] = repositionPlatformWeight;
        
        Array<Array<Integer>> allPlatformIndexes = getPlatformIndexes(filledSteps.size, weights, 0);
        
        Array<Integer> movingPlatformIndexes = allPlatformIndexes.get(1);
        Array<Integer> repositionPlatformIndexes = allPlatformIndexes.get(2);
        
        float jumpBoostFraction = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_FRACTION_PROPERTY));
        float jumpBoostLowWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_LOW_WEIGHT_PROPERTY));
        float jumpBoostMediumWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_MEDIUM_WEIGHT_PROPERTY));
        float jumpBoostHighWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_HIGH_WEIGHT_PROPERTY));
        
        float[] jumpBoostWeights = new float[3];
        jumpBoostWeights[0] = jumpBoostLowWeight;
        jumpBoostWeights[1] = jumpBoostMediumWeight;
        jumpBoostWeights[2] = jumpBoostHighWeight;
        
        Array<Array<Integer>> allJumpBoostPlatformIndexes = getPlatformIndexes(
                filledSteps.size, jumpBoostFraction, jumpBoostWeights, 0);
        
        float minMovingSpeed = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MIN_MOVING_SPEED_PROPERTY));
        float maxMovingSpeed = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MAX_MOVING_SPEED_PROPERTY));
        float minMovingRange = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MIN_MOVING_RANGE_PROPERTY));
        float maxMovingRange = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MAX_MOVING_RANGE_PROPERTY));
        float minRepositionRange = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MIN_REPOSITION_RANGE_PROPERTY));
        float maxRepositionRange = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MAX_REPOSITION_RANGE_PROPERTY));
        
        for (int i = 0; i < filledSteps.size; i++) {
            int step = filledSteps.get(i);
            
            PlatformMovementData movementData = getMovementData(i, filledSteps,
                    movingPlatformIndexes, minMovingSpeed, maxMovingSpeed, minMovingRange, maxMovingRange,
                    repositionPlatformIndexes, minRepositionRange, maxRepositionRange);
            
            int offset = getOffset(movementData);
            
            Array<PlatformFeatureData> featuresData = getFeaturesData(i, allJumpBoostPlatformIndexes);
            
            PlatformData padData = new PlatformData(PlatformData.NORMAL_TYPE, step, offset,
                    movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, null);
    }
    
    private static RiseSectionData generateRiseSectionCrumble(RiseSectionMetadata riseSectionMetadata) {
        
        String type = riseSectionMetadata.getType();
        String name = riseSectionMetadata.getName();
        int stepRange = MathUtils.random(
                riseSectionMetadata.getMinStepRange(), riseSectionMetadata.getMaxStepRange());
        int minStepDistance = riseSectionMetadata.getMinStepDistance();
        int maxStepDistance = riseSectionMetadata.getMaxStepDistance();
        int difficulty = riseSectionMetadata.getDifficulty();
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        
        float crumbleFraction = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.CRUMBLE_FRACTION_PROPERTY));
        int crumbleCount = (int) (filledSteps.size * crumbleFraction);
        
        Array<Integer> crumbleIndexes = GameUtils.getRandomIndexes(filledSteps.size, crumbleCount, 0);
        
        float jumpBoostFraction = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_FRACTION_PROPERTY));
        float jumpBoostLowWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_LOW_WEIGHT_PROPERTY));
        float jumpBoostMediumWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_MEDIUM_WEIGHT_PROPERTY));
        float jumpBoostHighWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_HIGH_WEIGHT_PROPERTY));
        
        float[] jumpBoostWeights = new float[3];
        jumpBoostWeights[0] = jumpBoostLowWeight;
        jumpBoostWeights[1] = jumpBoostMediumWeight;
        jumpBoostWeights[2] = jumpBoostHighWeight;
        
        Array<Array<Integer>> allJumpBoostPlatformIndexes = getPlatformIndexes(
                filledSteps.size, jumpBoostFraction, jumpBoostWeights, 0);
        
        for (int i = 0; i < filledSteps.size; i++) {
            int step = filledSteps.get(i);
            
            PlatformMovementData movementData = null;
            
            int offset = getOffset(movementData);
            
            Array<PlatformFeatureData> featuresData = getFeaturesData(i, allJumpBoostPlatformIndexes);
            
            String platformType = crumbleIndexes.contains(i, false) ?
                    PlatformData.CRUMBLE_TYPE : PlatformData.NORMAL_TYPE;
            
            PlatformData padData = new PlatformData(platformType, step, offset, movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, null);
    }
    
    private static RiseSectionData generateRiseSectionJumpBoost(RiseSectionMetadata riseSectionMetadata) {
        
        String type = riseSectionMetadata.getType();
        String name = riseSectionMetadata.getName();
        int stepRange = MathUtils.random(
                riseSectionMetadata.getMinStepRange(), riseSectionMetadata.getMaxStepRange());
        int minStepDistance = riseSectionMetadata.getMinStepDistance();
        int maxStepDistance = riseSectionMetadata.getMaxStepDistance();
        int difficulty = riseSectionMetadata.getDifficulty();
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        filledSteps.removeIndex(0);
        filledSteps.removeIndex(0);
        
        int jumpBoostCount = filledSteps.size;
        int numNonJumpBoostSteps = 0;
        for (int step = 0; step < filledSteps.get(0); step += PlatformData.MAX_PLATFORM_DISTANCE_STEPS) {
            filledSteps.add(step);
            numNonJumpBoostSteps++;
        }
        filledSteps.sort();
        
        float jumpBoostLowWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_LOW_WEIGHT_PROPERTY));
        float jumpBoostMediumWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_MEDIUM_WEIGHT_PROPERTY));
        float jumpBoostHighWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_HIGH_WEIGHT_PROPERTY));
        
        float[] jumpBoostWeights = new float[3];
        jumpBoostWeights[0] = jumpBoostLowWeight;
        jumpBoostWeights[1] = jumpBoostMediumWeight;
        jumpBoostWeights[2] = jumpBoostHighWeight;
        
        Array<Array<Integer>> allJumpBoostPlatformIndexes = getPlatformIndexes(
                jumpBoostCount, 1.0f, jumpBoostWeights, numNonJumpBoostSteps);
        
        boolean isCrumble = Boolean.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.CRUMBLE_PROPERTY));
        
        for (int i = 0; i < filledSteps.size; i++) {
            int step = filledSteps.get(i);
            
            PlatformMovementData movementData = null;
            
            int offset = getOffset(movementData);
            
            Array<PlatformFeatureData> featuresData = getFeaturesData(i, allJumpBoostPlatformIndexes);
            
            String platformType = i >= numNonJumpBoostSteps && isCrumble ?
                    PlatformData.CRUMBLE_TYPE : PlatformData.NORMAL_TYPE;
            
            PlatformData padData = new PlatformData(platformType, step, offset,
                    movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, null);
    }
    
    private static RiseSectionData generateRiseSectionVisibleOnJump(RiseSectionMetadata riseSectionMetadata) {
        
        String type = riseSectionMetadata.getType();
        String name = riseSectionMetadata.getName();
        int stepRange = MathUtils.random(
                riseSectionMetadata.getMinStepRange(), riseSectionMetadata.getMaxStepRange());
        int minStepDistance = riseSectionMetadata.getMinStepDistance();
        int maxStepDistance = riseSectionMetadata.getMaxStepDistance();
        int difficulty = riseSectionMetadata.getDifficulty();
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        
        float normalPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.NORMAL_PLATFORM_WEIGHT_PROPERTY));
        float visibleOnJumpPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.VISIBLE_ON_JUMP_PLATFORM_WEIGHT_PROPERTY));
        
        float[] weights = new float[2];
        weights[0] = normalPlatformWeight;
        weights[1] = visibleOnJumpPlatformWeight;
        Array<Array<Integer>> allPlatformIndexes = getPlatformIndexes(filledSteps.size - 1, weights, 1);
        
        Array<Integer> visibleOnJumpPlatformIndexes = allPlatformIndexes.get(1);
        
        boolean isCrumble = Boolean.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.CRUMBLE_PROPERTY));
        
        for (int i = 0; i < filledSteps.size; i++) {
            int step = filledSteps.get(i);
            
            PlatformMovementData movementData = null;
            
            int offset = getOffset(movementData);
            
            Array<PlatformFeatureData> featuresData = getFeaturesDataVisibleOnJump(i, visibleOnJumpPlatformIndexes);
            
            String platformType = featuresData != null && isCrumble ?
                    PlatformData.CRUMBLE_TYPE : PlatformData.NORMAL_TYPE;
            
            PlatformData padData = new PlatformData(platformType, step, offset,
                    movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, null);
    }
    
    private static Array<Integer> getFilledSteps(int stepRange, int minStepDistance, int maxStepDistance) {
        Array<Integer> filledSteps = new Array<Integer>(true, stepRange);
        int currentStep = 0;
        while (currentStep < stepRange) {
            filledSteps.add(currentStep);
            currentStep += MathUtils.random(minStepDistance, maxStepDistance);
        }
        
        return filledSteps;
    }
    
    private static Array<Array<Integer>> getPlatformIndexes(int numIndexes, float[] weights, int offset) {
        return getPlatformIndexes(numIndexes, 1.0f, weights, offset);
    }
    
    private static Array<Array<Integer>> getPlatformIndexes(int numIndexes, float totalFraction,
            float[] weights, int offset) {
        
        int[] counts = getCounts(numIndexes, totalFraction, weights);
        
        Array<Array<Integer>> allIndexes = new Array<Array<Integer>>(true, weights.length);
        
        Array<Integer> takenIndexes = new Array<Integer>();
        for (int i = 0; i < weights.length; i++) {
            Array<Integer> platformIndexes = GameUtils.getRandomIndexes(
                    numIndexes, counts[i], offset, takenIndexes);
            takenIndexes.addAll(platformIndexes);
            allIndexes.add(platformIndexes);
        }
        
        return allIndexes;
    }
    
    private static int[] getCounts(int numIndexes, float totalFraction, float[] weights) {
        float totalWeight = 0.0f;
        for (float weight : weights) {
            totalWeight += weight;
        }
        
        float[] weightFractions = new float[weights.length];
        for (int i = 0; i < weights.length; i++) {
            weightFractions[i] = weights[i] / totalWeight;
        }
        
        int totalCount = MathUtils.roundPositive(totalFraction * numIndexes);
        totalCount = MathUtils.clamp(totalCount, 0, numIndexes);
        
        int[] counts = new int[weights.length];
        int currentTotalCount = 0;
        for (int i = 0; i < weights.length; i++) {
            counts[i] = MathUtils.roundPositive(weightFractions[i] * totalCount);
            currentTotalCount += counts[i];
        }
        
        int adjustmentRequired = totalCount - currentTotalCount;
        if (adjustmentRequired > 0) {
            float[] cumulativeWeightFractions = new float[weights.length];
            for (int i = 0; i < weights.length - 1; i++) {
                cumulativeWeightFractions[i] = weightFractions[i] + (i > 0 ? cumulativeWeightFractions[i - 1] : 0.0f);
            }
            cumulativeWeightFractions[weights.length - 1] = 1.0f;
            
            while (adjustmentRequired > 0) {
                float randomFloat = MathUtils.random();
                for (int i = 0; i < cumulativeWeightFractions.length; i++) {
                    if (randomFloat < cumulativeWeightFractions[i]) {
                        counts[i]++;
                        break;
                    }
                }
                adjustmentRequired--;
            }
        } else if (adjustmentRequired < 0) {
            while (adjustmentRequired < 0) {
                int maxCountIndex = 0;
                for (int i = 1; i < counts.length; i++) {
                    if (counts[i] >= counts[maxCountIndex]) {
                        maxCountIndex = i;
                    }
                }
                counts[maxCountIndex]--;
                adjustmentRequired++;
            }
        }
        
        return counts;
    }
    
    private static PlatformMovementData getMovementData(int index, Array<Integer> filledSteps,
            Array<Integer> movingPlatformIndexes, float minMovingSpeed, float maxMovingSpeed,
            float minMovingRange, float maxMovingRange,
            Array<Integer> repositionPlatformIndexes, float minRepositionSpeed, float maxRepositionSpeed) {
        
        PlatformMovementData movementData;
        if (movingPlatformIndexes.contains(index, false)) {
            float movingSpeed = MathUtils.random(minMovingSpeed, maxMovingSpeed);
            float movingRange = MathUtils.random(minMovingRange, maxMovingRange);
            
            ObjectMap<String, String> properties = new ObjectMap<String, String>(3);
            properties.put(PlatformMovementData.RANGE_PROPERTY, String.valueOf(movingRange));
            properties.put(PlatformMovementData.SPEED_PROPERTY, String.valueOf(movingSpeed));
            properties.put(PlatformMovementData.INITIAL_OFFSET_PROPERTY,
                    String.valueOf(MathUtils.random(0.0f, movingRange * 2.0f)));
            
            movementData = new PlatformMovementData(PlatformMovementData.HORIZONTAL_MOVEMENT, properties);
        } else if (repositionPlatformIndexes.contains(index, false)) {
            float repositionRange = MathUtils.random(minRepositionSpeed, maxRepositionSpeed);
            
            ObjectMap<String, String> properties = new ObjectMap<String, String>(3);
            properties.put(PlatformMovementData.REPOSITION_TYPE_PROPERTY,
                    PlatformMovementData.REPOSITION_TYPE_EDGE_PROPERTY_VALUE);
            properties.put(PlatformMovementData.INITIAL_OFFSET_PROPERTY,
                    String.valueOf(MathUtils.randomBoolean() ? 0.0f : repositionRange));
            properties.put(PlatformMovementData.RANGE_PROPERTY, String.valueOf(repositionRange));
            
            movementData = new PlatformMovementData(PlatformMovementData.REPOSITION_MOVEMENT, properties);
        } else {
            movementData = null;
        }
        
        return movementData;
    }
    
    private static int getOffset(PlatformMovementData movementData) {
        
        if (movementData != null) {
            float range = Float.valueOf(movementData.getProperty(PlatformMovementData.RANGE_PROPERTY));
            int rangeInOffsets = MathUtils.ceil(range / GameAreaUtils.OFFSET_WIDTH);
            int maxOffset = PlatformData.MAX_PLATFORM_OFFSET - rangeInOffsets;
            return MathUtils.random(maxOffset);
        } else {
            return MathUtils.random(PlatformData.MAX_PLATFORM_OFFSET);
        }
    }
    
    private static Array<PlatformFeatureData> getFeaturesData(int index,
            Array<Array<Integer>> allJumpBoostPlatformIndexes) {
        
        String jumpBoostPowerString;
        if (allJumpBoostPlatformIndexes.get(0).contains(index, false)) {
            jumpBoostPowerString = PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE;
        } else if (allJumpBoostPlatformIndexes.get(1).contains(index, false)) {
            jumpBoostPowerString = PlatformFeatureData.JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE;
        } else if (allJumpBoostPlatformIndexes.get(2).contains(index, false)) {
            jumpBoostPowerString = PlatformFeatureData.JUMP_BOOST_POWER_HIGH_PROPERTY_VALUE;
        } else {
            jumpBoostPowerString = null;
        }
        
        Array<PlatformFeatureData> featuresData;
        if (jumpBoostPowerString != null) {
            
            featuresData = new Array<PlatformFeatureData>(true, 1);
            
            ObjectMap<String, String> properties = new ObjectMap<String, String>(2);
            properties.put(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY,
                    String.valueOf(MathUtils.random()));
            properties.put(PlatformFeatureData.JUMP_BOOST_POWER_PROPERTY, jumpBoostPowerString);
            
            PlatformFeatureData featureData = new PlatformFeatureData(PlatformFeatureData.JUMP_BOOST_FEATURE,
                    properties);
            featuresData.add(featureData);
        } else {
            featuresData = null;
        }
        
        return featuresData;
    }
    
    private static Array<PlatformFeatureData> getFeaturesDataVisibleOnJump(int index,
            Array<Integer> visibleOnJumpPlatformIndexes) {
        
        Array<PlatformFeatureData> featuresData;
        if (visibleOnJumpPlatformIndexes.contains(index, false)) {
            featuresData = new Array<PlatformFeatureData>(true, 1);
            PlatformFeatureData featureData = new PlatformFeatureData(
                    PlatformFeatureData.VISIBLE_ON_JUMP_FEATURE, null);
            featuresData.add(featureData);
        } else {
            featuresData = null;
        }
        
        return featuresData;
    }
}
