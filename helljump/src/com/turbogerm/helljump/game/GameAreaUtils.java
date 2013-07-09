package com.turbogerm.helljump.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorPositionPair;

public final class GameAreaUtils {
    
    public static final float METER_TO_PIXEL = 40.0f;
    public static final float PIXEL_TO_METER = 1.0f / METER_TO_PIXEL;
    
    // 'step' is vertical offset, 'offset' is horizontal offset in position grid
    public static final float STEP_HEIGHT = 1.0f;
    public static final float OFFSET_WIDTH = 0.25f;
    
    public static Vector2 getPosition(int startStep, float step, float offset) {
        float x = offset *  OFFSET_WIDTH;
        float y = (step + startStep) * STEP_HEIGHT;
        return new Vector2(x, y);
    }
    
    public static Array<ColorPositionPair> getBackgroundColorSpectrum() {
        Array<ColorPositionPair> colorPositionPairs = new Array<ColorPositionPair>(true, 5);
        
        colorPositionPairs.add(new ColorPositionPair(new Color(0.1f, 0.0f, 0.0f, 1.0f), 0.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.6f, 0.0f, 0.0f, 1.0f), 5.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.91f, 0.6f, 0.09f, 1.0f), 10.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.91f, 0.6f, 0.09f, 1.0f), 18.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.0f, 0.6f, 0.0f, 1.0f), 20.0f));
        
        return colorPositionPairs;
    }
}
