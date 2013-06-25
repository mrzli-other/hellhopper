package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.GameAreaUtils;

public final class PlatformData {
    
    public static final String NORMAL_TYPE = "normal";
    public static final String CRUMBLE_TYPE = "crumble";
    
    public static final int PLATFORM_WIDTH_OFFSETS = 8;
    private static final float PLATFORM_HEIGHT_STEPS = 0.5f;
    
    public static final float PLATFORM_WIDTH = GameAreaUtils.OFFSET_WIDTH * PLATFORM_WIDTH_OFFSETS;
    public static final float PLATFORM_HEIGHT = GameAreaUtils.STEP_HEIGHT * PLATFORM_HEIGHT_STEPS;
    
    public static final int MAX_PLATFORM_DISTANCE_STEPS = 5;
    public static final int MAX_PLATFORM_OFFSET = (int) (GameArea.GAME_AREA_WIDTH / GameAreaUtils.OFFSET_WIDTH) -
            PLATFORM_WIDTH_OFFSETS;
    
    private final int mId;
    private final String mPlatformType;
    private final int mStep;
    private final int mOffset;
    private final PlatformMovementData mMovementData;
    private final Array<PlatformFeatureData> mFeaturesData;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformData(int id, String platformType, int step, int offset, PlatformMovementData movementData,
            Array<PlatformFeatureData> featuresData, ObjectMap<String, String> properties) {
        
        mId = id;
        mPlatformType = platformType;
        mStep = step;
        mOffset = offset;
        mMovementData = movementData;
        mFeaturesData = featuresData;
        mProperties = properties;
    }
    
    public int getId() {
        return mId;
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
    
    public Vector2 getPosition(int startStep) {
        return GameAreaUtils.getPosition(startStep, mStep, mOffset);
    }
}
