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
import com.turbogerm.hellhopper.util.ExceptionThrower;
import com.turbogerm.hellhopper.util.GameUtils;

final class RiseSectionGenerator {
    
    public static RiseSectionData generateRiseSection(RiseSectionMetadata riseSectionMetadata) {
        String type = riseSectionMetadata.getType();
        if (RiseSectionMetadata.BASIC_TYPE.equals(type)) {
            return generateRiseSectionBasic(riseSectionMetadata);
        } else if (RiseSectionMetadata.JUMP_BOOST_TYPE.equals(type)) {
            return generateRiseSectionBasic(riseSectionMetadata);
        } else {
            ExceptionThrower.throwException("Invalid rise section metadata type: %s", type);
            return null;
        }
    }
    
    private static RiseSectionData generateRiseSectionBasic(RiseSectionMetadata riseSectionMetadata) {
        
        String name = riseSectionMetadata.getName();
        int stepRange = MathUtils.random(
                riseSectionMetadata.getMinStepRange(), riseSectionMetadata.getMaxStepRange());
        int minStepDistance = riseSectionMetadata.getMinStepDistance();
        int maxStepDistance = riseSectionMetadata.getMaxStepDistance();
        int difficulty = riseSectionMetadata.getDifficulty();
        
        float normalPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.NORMAL_PLATFORM_WEIGHT_PROPERTY));
        float movingPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.MOVING_PLATFORM_WEIGHT_PROPERTY));
        float repositionPlatformWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.REPOSITION_PLATFORM_WEIGHT_PROPERTY));
        
        float totalPlatformWeight = normalPlatformWeight + movingPlatformWeight + repositionPlatformWeight;
        float normalPlatformFraction = normalPlatformWeight / totalPlatformWeight;
        float movingPlatformFraction = movingPlatformWeight / totalPlatformWeight;
        
        Array<PlatformData> platformDataList = new Array<PlatformData>(stepRange);
        Array<Integer> filledSteps = getFilledSteps(stepRange, minStepDistance, maxStepDistance);
        
        int normalPlatformCount = (int) (filledSteps.size * normalPlatformFraction);
        Array<Integer> normalPlatformIndexes =
                GameUtils.getRandomIndexes(filledSteps.size, normalPlatformCount);
        
        int movingPlatformCount = (int) (filledSteps.size * movingPlatformFraction);
        Array<Integer> movingPlatformIndexes =
                GameUtils.getRandomIndexes(filledSteps.size, movingPlatformCount, normalPlatformIndexes);
        
        Array<Integer> repositionPlatformIndexes = GameUtils.getRange(filledSteps.size);
        for (Integer normalPlatformIndex : normalPlatformIndexes) {
            repositionPlatformIndexes.removeValue(normalPlatformIndex, false);
        }
        for (Integer movingPlatformIndex : movingPlatformIndexes) {
            repositionPlatformIndexes.removeValue(movingPlatformIndex, false);
        }
        
        float jumpBoostFraction = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_FRACTION_PROPERTY));
        float jumpBoostLowWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_LOW_WEIGHT_PROPERTY));
        float jumpBoostMediumWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_MEDIUM_WEIGHT_PROPERTY));
        float jumpBoostHighWeight = Float.valueOf(
                riseSectionMetadata.getProperty(RiseSectionMetadata.JUMP_BOOST_HIGH_WEIGHT_PROPERTY));
        
        float totalJumpBoostWeight = jumpBoostLowWeight + jumpBoostMediumWeight + jumpBoostHighWeight;
        float jumpBoostLowFraction = jumpBoostLowWeight / totalJumpBoostWeight;
        float jumpBoostMediumFraction = jumpBoostMediumWeight / totalJumpBoostWeight;
        
        int jumpBoostCount = (int) (filledSteps.size * jumpBoostFraction);
        int jumpBoostLowCount = (int) (jumpBoostCount * jumpBoostLowFraction);
        int jumpBoostMediumCount = (int) (jumpBoostCount * jumpBoostMediumFraction);
        int jumpBoostHighCount = jumpBoostCount - jumpBoostLowCount - jumpBoostMediumCount;
        
        Array<Integer> jumpBoostLowPlatformIndexes =
                GameUtils.getRandomIndexes(filledSteps.size, jumpBoostLowCount);
        Array<Integer> jumpBoostMediumPlatformIndexes =
                GameUtils.getRandomIndexes(filledSteps.size, jumpBoostMediumCount, jumpBoostLowPlatformIndexes);
        Array<Integer> jumpBoostLowAndMediumPlatformIndexes = new Array<Integer>(jumpBoostLowPlatformIndexes);
        jumpBoostLowAndMediumPlatformIndexes.addAll(jumpBoostMediumPlatformIndexes);
        Array<Integer> jumpBoostHighPlatformIndexes =
                GameUtils.getRandomIndexes(filledSteps.size, jumpBoostHighCount, jumpBoostLowAndMediumPlatformIndexes);
        
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
            
            Array<PlatformFeatureData> featuresData = getFeaturesData(i,
                    jumpBoostLowPlatformIndexes, jumpBoostMediumPlatformIndexes, jumpBoostHighPlatformIndexes);
            
            PlatformData padData = new PlatformData(PlatformData.NORMAL, step, offset,
                    movementData, featuresData, null);
            platformDataList.add(padData);
        }
        
        return new RiseSectionData(name, stepRange, difficulty, platformDataList);
    }
    
    private static Array<Integer> getFilledSteps(int stepRange, int minStepDistance, int maxStepDistance) {
        Array<Integer> filledSteps = new Array<Integer>(true, stepRange);
        int currentStep = 0;
        while (currentStep < stepRange) {
            filledSteps.add(currentStep);
            currentStep += MathUtils.random(minStepDistance, maxStepDistance);
            ;
        }
        
        return filledSteps;
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
            int rangeInOffsets = MathUtils.ceil(range / PlatformData.OFFSET_WIDTH);
            int maxOffset = PlatformData.MAX_PLATFORM_OFFSET - rangeInOffsets;
            return MathUtils.random(maxOffset);
        } else {
            return MathUtils.random(PlatformData.MAX_PLATFORM_OFFSET);
        }
    }
    
    private static Array<PlatformFeatureData> getFeaturesData(int index,
            Array<Integer> jumpBoostLowPlatformIndexes, Array<Integer> jumpBoostMediumPlatformIndexes,
            Array<Integer> jumpBoostHighPlatformIndexes) {
        
        String jumpBoostPowerString;
        if (jumpBoostLowPlatformIndexes.contains(index, false)) {
            jumpBoostPowerString = PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE;
        } else if (jumpBoostMediumPlatformIndexes.contains(index, false)) {
            jumpBoostPowerString = PlatformFeatureData.JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE;
        } else if (jumpBoostHighPlatformIndexes.contains(index, false)) {
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
}
