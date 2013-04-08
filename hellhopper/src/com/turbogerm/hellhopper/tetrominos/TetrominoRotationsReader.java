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
package com.turbogerm.hellhopper.tetrominos;

import com.badlogic.gdx.files.FileHandle;
import com.turbogerm.hellhopper.util.IntPair;

public final class TetrominoRotationsReader {
    
    public static IntPair[][][] read(FileHandle fileHandle) {
        String allText = fileHandle.readString();
        
        String[] tetrominoStrings = allText.split("\\r?\\n");
        IntPair[][][] tetrominoRotations = new IntPair[tetrominoStrings.length][][];
        for (int i = 0; i < tetrominoStrings.length; i++) {
            tetrominoRotations[i] = getSingleTetrominoRotationData(tetrominoStrings[i]);
        }
        
        return tetrominoRotations;
    }
    
    private static IntPair[][] getSingleTetrominoRotationData(String tetrominoString) {
        String[] rotationStrings = tetrominoString.split(";");
        IntPair[][] rotationsData = new IntPair[rotationStrings.length][];
        for (int i = 0; i < rotationStrings.length; i++) {
            rotationsData[i] = getRotationData(rotationStrings[i]);
        }
        
        return rotationsData;
    }
    
    private static IntPair[] getRotationData(String rotationString) {
        String[] coordinateStrings = rotationString.split(",");
        IntPair[] rotationData = new IntPair[coordinateStrings.length];
        for (int i = 0; i < coordinateStrings.length; i++) {
            rotationData[i] = getCoordinate(coordinateStrings[i]);
        }
        
        return rotationData;
    }
    
    private static IntPair getCoordinate(String coordinateString) {
        String[] coordinatePartStringsWithEmpty = coordinateString.split(" ");
        int nonEmptyCount = 0;
        for (int i = 0; i < coordinatePartStringsWithEmpty.length; i++) {
            if (!coordinatePartStringsWithEmpty[i].isEmpty()) {
                nonEmptyCount++;
            }
        }
        
        String[] coordinatePartStrings = new String[nonEmptyCount];
        int currentNonEmptyIndex = 0;
        for (int i = 0; i < coordinatePartStringsWithEmpty.length; i++) {
            if (!coordinatePartStringsWithEmpty[i].isEmpty()) {
                coordinatePartStrings[currentNonEmptyIndex] = coordinatePartStringsWithEmpty[i];
                currentNonEmptyIndex++;
            }
        }
        
        int x = Integer.parseInt(coordinatePartStrings[0]);
        int y = Integer.parseInt(coordinatePartStrings[1]);
        return new IntPair(x, y);
    }
}
