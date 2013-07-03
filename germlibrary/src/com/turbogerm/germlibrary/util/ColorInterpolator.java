package com.turbogerm.germlibrary.util;

import com.badlogic.gdx.graphics.Color;

public final class ColorInterpolator {
    
    private final HslColor mHsl1;
    private final HslColor mHsl2;
    private final Color mResult;
    
    public ColorInterpolator() {
        mHsl1 = new HslColor();
        mHsl2 = new HslColor();
        mResult = new Color();
        mResult.a = 1.0f;
    }
    
    public Color interpolateColor(Color c1, Color c2, float t) {
        rgbToHsl(c1.r, c1.g, c1.b, true);
        rgbToHsl(c2.r, c2.g, c2.b, false);
        
        float h = interpolateHue(mHsl1.h, mHsl2.h, t);
        float s = mHsl1.s + t * (mHsl2.s - mHsl1.s);
        float l = mHsl1.l + t * (mHsl2.l - mHsl1.l);
        hslToRgb(h, s, l);
        
        return mResult;
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
        // http://stackoverflow.com/questions/2593832/how-to-interpolate-hue-values-in-hsv-colour-space
        
        float distCCW = (h1 >= h2) ? h1 - h2 : 1 + h1 - h2;
        float distCW = (h1 >= h2) ? 1 + h2 - h1 : h2 - h1;
        
        float h = (distCW <= distCCW) ? h1 + (distCW * t) : h1 - (distCCW * t);
        h = GameUtils.getPositiveModulus(h, 1.0f);
        
        return h;
    }
}
