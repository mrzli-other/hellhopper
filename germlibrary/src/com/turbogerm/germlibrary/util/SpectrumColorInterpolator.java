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
