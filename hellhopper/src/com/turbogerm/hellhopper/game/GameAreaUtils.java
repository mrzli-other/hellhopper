package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.math.Vector2;

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
}
