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
// color conversion methods taken from:
//   http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c
package com.turbogerm.germlibrary.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public final class SpectrumColorInterpolator {
    
    private final ColorInterpolator mColorInterpolator;
    private final Color mResult;
    
    private final Array<ColorPositionPair> mColorPositionPairs;
    
    public SpectrumColorInterpolator(Array<ColorPositionPair> colorPositionPairs) {
        
        mColorInterpolator = new ColorInterpolator();
        mResult = new Color();
        
        mColorPositionPairs = colorPositionPairs;
        
        for (int i = 0; i < mColorPositionPairs.size; i++) {
            Color color = mColorPositionPairs.get(i).getColor();
            color.a = 1.0f;
        }
    }
    
    public Color getBackgroundColor(float spectrumFraction) {
        int numColors = mColorPositionPairs.size;
        
        float colorPosition = spectrumFraction * getPosition(numColors - 1);
        
        if (colorPosition < getPosition(0)) {
            mResult.set(getColor(0));
        } else if (colorPosition >= getPosition(numColors - 1)) {
            mResult.set(getColor(numColors - 1));
        } else {
            int stopIndex = 0;
            while (colorPosition >= getPosition(stopIndex)) {
                stopIndex++;
            }
            
            float t = (colorPosition - getPosition(stopIndex - 1)) /
                    (getPosition(stopIndex) - getPosition(stopIndex - 1));
            
            mResult.set(mColorInterpolator.interpolateColor(getColor(stopIndex - 1), getColor(stopIndex), t));
        }
        
        return mResult;
    }
    
    private Color getColor(int index) {
        return mColorPositionPairs.get(index).getColor();
    }
    
    private float getPosition(int index) {
        return mColorPositionPairs.get(index).getPosition();
    }
}
