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
package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.hellhopper.game.GameArea;

public final class PlatformData {
    
    public static final String NORMAL = "normal";
    public static final String CRUMBLE = "crumble";
    public static final String FLAME = "flame";
    
    // 'step' is vertical offset, 'offset' is horizontal offset in position grid
    
    public static final float STEP_HEIGHT = 1.0f;
    private static final float OFFSET_WIDTH = 0.25f;
    
    public static final int PLATFORM_WIDTH_OFFSETS = 8;
    private static final float PLATFORM_HEIGHT_STEPS = 0.5f;
    
    public static final float PLATFORM_WIDTH = OFFSET_WIDTH * PLATFORM_WIDTH_OFFSETS;
    public static final float PLATFORM_HEIGHT = STEP_HEIGHT * PLATFORM_HEIGHT_STEPS;
    
    public static final int MAX_PLATFORM_DISTANCE_STEPS = 5;
    public static final int MAX_PLATFORM_OFFSET = (int) (GameArea.GAME_AREA_WIDTH / OFFSET_WIDTH) -
            PLATFORM_WIDTH_OFFSETS;
    
    private final String mPlatformType;
    private final int mStep;
    private final int mOffset;
    private final PlatformMovementData mMovementData;
    private final Array<PlatformFeatureData> mFeaturesData;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformData(String platformType, int step, int offset, PlatformMovementData movementData,
            Array<PlatformFeatureData> featuresData, ObjectMap<String, String> properties) {
        mPlatformType = platformType;
        mStep = step;
        mOffset = offset;
        mMovementData = movementData;
        mFeaturesData = featuresData;
        mProperties = properties;
    }
    
    public String getPlatformType() {
        return mPlatformType;
    }
    
    public int getStep() {
        return mStep;
    }
    
    public int getOffset() {
        return mOffset;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
    
    public PlatformMovementData getMovementData() {
        return mMovementData;
    }
    
    public Array<PlatformFeatureData> getFeaturesData() {
        return mFeaturesData;
    }
    
    public Vector2 getPlatformPositions(int startStep) {
        float x = mOffset * OFFSET_WIDTH;
        float y = (mStep + startStep) * STEP_HEIGHT;
        return new Vector2(x, y);
    }
}
