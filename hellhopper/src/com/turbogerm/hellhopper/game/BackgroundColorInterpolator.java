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
// color conversion methods taken from:
//   http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c
package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.graphics.Color;
import com.turbogerm.hellhopper.util.ColorInterpolator;
import com.turbogerm.hellhopper.util.Logger;

public final class BackgroundColorInterpolator {
    
    private static final int NUM_COLORS = 4; //5;
    private static final float LIGHT_MULTIPLIER = 1.0f; //0.6f;
    
    private final ColorInterpolator mColorInterpolator;
    private final Color mResult;
    
    private float[] mColorPositions;
    private Color[] mColors;
    
    private float mRiseHeight;
    
    public BackgroundColorInterpolator() {
        
        mColorInterpolator = new ColorInterpolator();
        mResult = new Color();
        
        mColorPositions = new float[NUM_COLORS];
        mColors = new Color[NUM_COLORS];
        //setColor(0, 0.0f, new Color(1.0f, 0.0f, 0.0f, 1.0f));
        //setColor(1, 1.0f, new Color(1.0f, 0.5f, 0.0f, 1.0f));
        //setColor(2, 2.0f, new Color(1.0f, 1.0f, 0.0f, 1.0f));
        //setColor(3, 3.0f, new Color(0.72f, 0.45f, 0.2f, 1.0f));
        //setColor(4, 4.0f, new Color(0.0f, 1.0f, 0.0f, 1.0f));
        
        setColor(0, 0.0f, new Color(0.6f, 0.0f, 0.0f, 1.0f));
        setColor(1, 1.0f, new Color(0.54f, 0.46f, 0.57f, 1.0f));
        setColor(2, 2.0f, new Color(0.91f, 0.6f, 0.09f, 1.0f));
        setColor(3, 20.0f, new Color(0.0f, 0.6f, 0.0f, 1.0f));
        
        for (int i = 0; i < NUM_COLORS; i++) {
            mColors[i].mul(LIGHT_MULTIPLIER);
            mColors[i].a = 1.0f;
        }
    }
    
    public void setRiseHeight(float riseHeight) {
        mRiseHeight = riseHeight;
    }
    
    public Color getBackgroundColor(float currentHeight) {
        float colorPosition = currentHeight / mRiseHeight * mColorPositions[NUM_COLORS - 1];
        
        if (colorPosition < mColorPositions[0]) {
            mResult.set(mColors[0]);
        } else if (colorPosition >= mColorPositions[NUM_COLORS - 1]) {
            mResult.set(mColors[NUM_COLORS - 1]);
        } else {
            int stopIndex = 0;
            while (colorPosition >= mColorPositions[stopIndex]) {
                stopIndex++;
            }
            
            float t = (colorPosition - mColorPositions[stopIndex - 1]) /
                    (mColorPositions[stopIndex] - mColorPositions[stopIndex - 1]);
            
            mResult.set(mColorInterpolator.interpolateColor(mColors[stopIndex - 1], mColors[stopIndex], t));
        }
        
        return mResult;
    }
    
    private void setColor(int stopIndex, float position, Color color) {
        mColorPositions[stopIndex] = position;
        mColors[stopIndex] = color;
    }
}
