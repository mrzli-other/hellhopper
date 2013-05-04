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
import com.turbogerm.hellhopper.util.HslColor;

public final class BackgroundColorInterpolator {
    
    private static final int NUM_COLORS = 5;
    private static final float LIGHT_MULTIPLIER = 0.6f;
    
    private final HslColor mHsl1;
    private final HslColor mHsl2;
    private final Color mResult;
    
    private float[] mColorPositions;
    private Color[] mColors;
    
    private float mRiseHeight;
    
    public BackgroundColorInterpolator() {
        mHsl1 = new HslColor();
        mHsl2 = new HslColor();
        mResult = new Color();
        mResult.a = 1.0f;
        
        mColorPositions = new float[NUM_COLORS];
        mColors = new Color[NUM_COLORS];
        setColor(0, 0.0f, new Color(1.0f, 0.0f, 0.0f, 1.0f));
        setColor(1, 1.0f, new Color(1.0f, 0.5f, 0.0f, 1.0f));
        setColor(2, 2.0f, new Color(1.0f, 1.0f, 0.0f, 1.0f));
        setColor(3, 3.0f, new Color(0.72f, 0.45f, 0.2f, 1.0f));
        setColor(4, 4.0f, new Color(0.0f, 1.0f, 0.0f, 1.0f));
        
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
            
            interpolateColor(mColors[stopIndex - 1], mColors[stopIndex], t);
        }
        
        return mResult;
    }
    
    private void interpolateColor(Color c1, Color c2, float t) {
        rgbToHsl(c1.r, c1.g, c1.b, true);
        rgbToHsl(c2.r, c2.g, c2.b, false);
        
        float h = interpolateHue(mHsl1.h, mHsl2.h, t);
        float s = mHsl1.s + t * (mHsl2.s - mHsl1.s);
        float l = mHsl1.l + t * (mHsl2.l - mHsl1.l);
        hslToRgb(h, s, l);
    }
    
    private void setColor(int stopIndex, float position, Color color) {
        mColorPositions[stopIndex] = position;
        mColors[stopIndex] = color;
    }
    
    private void rgbToHsl(float r, float g, float b, boolean isFirst) {
        float max = Math.max(Math.max(r, g), b);
        float min = Math.min(Math.min(r, g), b);
        float h, s, l;
        h = s = l = (max + min) / 2;
        
        if (max == min) {
            h = s = 0.0f; // achromatic
        } else {
            float d = max - min;
            s = l > 0.5f ? d / (2.0f - max - min) : d / (max + min);
            if (max == r) {
                h = (g - b) / d + (g < b ? 6.0f : 0.0f);
            } else if (max == g) {
                h = (b - r) / d + 2.0f;
            } else if (max == b) {
                h = (r - g) / d + 4.0f;
            }
            h /= 6.0f;
        }
        
        HslColor hsl = isFirst ? mHsl1 : mHsl2;
        hsl.h = h;
        hsl.s = s;
        hsl.l = l;
    }
    
    private void hslToRgb(float h, float s, float l) {
        float r, g, b;
        
        if (s == 0) {
            r = g = b = l; // achromatic
        } else {
            float q = l < 0.5f ? l * (1.0f + s) : l + s - l * s;
            float p = 2.0f * l - q;
            r = hueToRgb(p, q, h + 1.0f / 3.0f);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1.0f / 3.0f);
        }
        
        mResult.r = r;
        mResult.g = g;
        mResult.b = b;
    }
    
    private static float hueToRgb(float p, float q, float t) {
        if (t < 0)
            t += 1;
        if (t > 1)
            t -= 1;
        if (t < 1.0f / 6.0f)
            return p + (q - p) * 6.0f * t;
        if (t < 1.0f / 2.0f)
            return q;
        if (t < 2.0f / 3.0f)
            return p + (q - p) * (2.0f / 3.0f - t) * 6.0f;
        return p;
    }
    
    private static float interpolateHue(float h1, float h2, float t) {
        float h = h1 + t * (h2 - h1);
        if (Math.abs(h2 - h1) > 0.5f) {
            h = (h + 0.5f) % 1.0f;
        }
        
        return h;
    }
}
