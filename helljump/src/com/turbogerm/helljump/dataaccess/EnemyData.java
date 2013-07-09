package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.helljump.game.GameAreaUtils;

public final class EnemyData {
    
    public static final String SAW_TYPE = "saw";
    public static final String IMP_TYPE = "imp";
    public static final String LOCO_TYPE = "loco";
    public static final String KNIGHT_TYPE = "knight";
    public static final String EVIL_TWIN_TYPE = "eviltwin";
    public static final String COOL_CLERK_TYPE = "coolclerk";
    
    public static final String SPEED_PROPERTY = "speed";
    public static final String TRAVEL_PERIOD_PROPERTY = "travelperiod";
    public static final String RANGE_PROPERTY = "range";
    public static final String RANGES_PROPERTY = "ranges";
    public static final String INITIAL_OFFSET_PROPERTY = "initialoffset";
    
    private final String mType;
    private final float mStep;
    private final float mOffset;
    private final ObjectMap<String, String> mProperties;
    
    public EnemyData(String type, float step, float offset, ObjectMap<String, String> properties) {
        mType = type;
        mStep = step;
        mOffset = offset;
        mProperties = properties;
    }
    
    public String getType() {
        return mType;
    }
    
    public float getStep() {
        return mStep;
    }
    
    public float getOffset() {
        return mOffset;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
    
    public Vector2 getPosition(int startStep) {
        return GameAreaUtils.getPosition(startStep, mStep, mOffset);
    }
}
