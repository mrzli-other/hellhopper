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

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;

public final class GameAreaGenerator {
    
    private static final float STEP_X = 10.0f;
    private static final float STEP_Y = 40.0f;
    
    private static final int PAD_SIZE_X_STEPS = 8;
    private static final float PAD_SIZE_Y_STEPS = 0.5f;
    
    public static final float PAD_WIDTH = STEP_X * PAD_SIZE_X_STEPS;
    public static final float PAD_HEIGHT = STEP_Y * PAD_SIZE_Y_STEPS;
    
    private static final int MAX_STEP_Y_DISTANCE = 5;
    private static final int MAX_STEP_X = (int) (HellHopper.VIEWPORT_WIDTH / STEP_X) - PAD_SIZE_X_STEPS;
    
    public static GameAreaPads generateGameAreaPads() {
        PadCollectionData padCollection = generatePadCollection(50, 50);
        Array<Vector2> padPositions = getPadPositions(padCollection, 0);
        
        return new GameAreaPads(padPositions);
    }
    
    private static Array<Vector2> getPadPositions(PadCollectionData padCollection, int startStep) {
        Array<PadData> padDataList = padCollection.getPadDataList();
        Array<Vector2> padPositions = new Array<Vector2>(true, padDataList.size);
        for (PadData padData : padDataList) {
            float x = padData.getOffsetX() * STEP_X;
            float y = (padData.getOffsetY() + startStep) * STEP_Y;
            Vector2 padPosition = new Vector2(x, y);
            padPositions.add(padPosition);
        }
        
        return padPositions;
    }
    
    private static PadCollectionData generatePadCollection(int stepRange, int numPads) {
        
        Array<PadData> padDataList = new Array<PadData>(numPads);
        Array<StepFreePositions> freePositions = getInitialFreePositions(stepRange);
        for (int i = 0; i < numPads; i++) {
            FreePosition position = getRandomFreePosition(freePositions);
            PadData padData = new PadData(position.stepX, position.stepY);
            padDataList.add(padData);
            updateFreePositions(freePositions, position);
        }
        
        padDataList.sort(new Comparator<PadData>() {
            
            @Override
            public int compare(PadData p1, PadData p2) {
                if (p1.getOffsetY() < p2.getOffsetY()) {
                    return -1;
                } else if (p1.getOffsetY() > p2.getOffsetY()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        
        return new PadCollectionData(stepRange, padDataList);
    }
    
    private static Array<StepFreePositions> getInitialFreePositions(int stepRange) {
        Array<StepFreePositions> freePositions = new Array<StepFreePositions>(true, stepRange);
        for (int i = 0; i < stepRange; i++) {
            StepFreePositions stepFreePositions = getInitialFreePositionsForStep(i);
            freePositions.add(stepFreePositions);
        }
        
        return freePositions;
    }
    
    private static StepFreePositions getInitialFreePositionsForStep(int step) {
        Array<Integer> freePositionsForStep = new Array<Integer>(true, MAX_STEP_X + 1);
        for (int i = 0; i <= MAX_STEP_X; i++) {
            freePositionsForStep.add(i);
        }
        
        return new StepFreePositions(step, freePositionsForStep);
    }
    
    private static FreePosition getRandomFreePosition(Array<StepFreePositions> freePositions) {
        int totalNumFreePositions = getTotalNumFreePositions(freePositions);
        int freePositionIndex = MathUtils.random(totalNumFreePositions - 1);
        
        for (StepFreePositions stepFreePositions : freePositions) {
            int currentStepSize = stepFreePositions.freePositionsForStep.size;
            if (freePositionIndex < currentStepSize) {
                return new FreePosition(stepFreePositions.freePositionsForStep.get(freePositionIndex),
                        stepFreePositions.step);
            }
            
            freePositionIndex -= currentStepSize;
        }
        
        return null;
    }
    
    private static int getTotalNumFreePositions(Array<StepFreePositions> freePositions) {
        int totalNumFreePositions = 0;
        for (StepFreePositions stepFreePositions : freePositions) {
            totalNumFreePositions += stepFreePositions.freePositionsForStep.size;
        }
        
        return totalNumFreePositions;
    }
    
    private static void updateFreePositions(Array<StepFreePositions> freePositions, FreePosition takenPosition) {
        for (StepFreePositions stepFreePositions : freePositions) {
            if (stepFreePositions.step == takenPosition.stepY) {
                int firstInvalidatedPositionInStep = takenPosition.stepX - PAD_SIZE_X_STEPS + 1;
                int numInvalidatePositions = PAD_SIZE_X_STEPS * 2 - 1;
                for (int i = 0; i < numInvalidatePositions; i++) {
                    stepFreePositions.freePositionsForStep.removeValue(firstInvalidatedPositionInStep + i, false);
                }
            }
        }
    }
    
    private static class StepFreePositions {
        public int step;
        public Array<Integer> freePositionsForStep;
        
        public StepFreePositions(int step, Array<Integer> freePositionsForStep) {
            this.step = step;
            this.freePositionsForStep = freePositionsForStep;
        }
    }
    
    private static class FreePosition {
        public int stepX;
        public int stepY;
        
        public FreePosition(int stepX, int stepY) {
            this.stepX = stepX;
            this.stepY = stepY;
        }
    }
}
