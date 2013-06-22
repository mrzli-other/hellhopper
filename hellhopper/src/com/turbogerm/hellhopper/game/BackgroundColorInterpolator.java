// color conversion methods taken from:
//   http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c
package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.util.ColorInterpolator;

public final class BackgroundColorInterpolator {
    
    private static final int BACKGROUND_COLORS_CAPACITY = 10;
    
    private static final float LIGHT_MULTIPLIER = 1.0f;
    
    private final ColorInterpolator mColorInterpolator;
    private final Color mResult;
    
    private final Array<BackgroundColor> mBackgroundColors;
    
    private float mRiseHeight;
    
    public BackgroundColorInterpolator() {
        
        mColorInterpolator = new ColorInterpolator();
        mResult = new Color();
        
        mBackgroundColors = new Array<BackgroundColor>(true, BACKGROUND_COLORS_CAPACITY);
        
        mBackgroundColors.add(new BackgroundColor(new Color(0.1f, 0.0f, 0.0f, 1.0f), 0.0f));
        mBackgroundColors.add(new BackgroundColor(new Color(0.6f, 0.0f, 0.0f, 1.0f), 5.0f));
        mBackgroundColors.add(new BackgroundColor(new Color(0.91f, 0.6f, 0.09f, 1.0f), 10.0f));
        mBackgroundColors.add(new BackgroundColor(new Color(0.91f, 0.6f, 0.09f, 1.0f), 18.0f));
        mBackgroundColors.add(new BackgroundColor(new Color(0.0f, 0.6f, 0.0f, 1.0f), 20.0f));
        
        for (int i = 0; i < mBackgroundColors.size; i++) {
            Color color = mBackgroundColors.get(i).getColor();
            color.mul(LIGHT_MULTIPLIER);
            color.a = 1.0f;
        }
    }
    
    public void setRiseHeight(float riseHeight) {
        mRiseHeight = riseHeight;
    }
    
    public Color getBackgroundColor(float currentHeight) {
        int numColors = mBackgroundColors.size;
        
        float colorPosition = currentHeight / mRiseHeight * getPosition(numColors - 1);
        
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
        return mBackgroundColors.get(index).getColor();
    }
    
    private float getPosition(int index) {
        return mBackgroundColors.get(index).getColorPosition();
    }
    
    private static class BackgroundColor {
        private final Color mColor;
        private final float mColorPosition;
        
        public BackgroundColor(Color color, float colorPosition) {
            mColor = color;
            mColorPosition = colorPosition;
        }
        
        public Color getColor() {
            return mColor;
        }
        
        public float getColorPosition() {
            return mColorPosition;
        }
    }
}
