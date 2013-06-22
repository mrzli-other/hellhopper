package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.utils.ObjectMap;

public final class PlatformMovementData {
    
    public static final String HORIZONTAL_MOVEMENT = "horizontal";
    public static final String VERTICAL_MOVEMENT = "vertical";
    public static final String CIRCULAR_MOVEMENT = "circular";
    public static final String REPOSITION_MOVEMENT = "reposition";
    
    public static final String SPEED_PROPERTY = "speed";
    
    public static final String RANGE_PROPERTY = "range";
    public static final String INITIAL_OFFSET_PROPERTY = "initialoffset";
    public static final String RADIUS_PROPERTY = "radius";
    public static final String INITIAL_DEGREES_PROPERTY = "initialdegrees";
    
    public static final String DIRECTION_PROPERTY = "direction";
    public static final String DIRECTION_CCW_PROPERTY_VALUE = "ccw";
    public static final String DIRECTION_CW_PROPERTY_VALUE = "cw";
    
    public static final String REPOSITION_TYPE_PROPERTY = "repositiontype";
    public static final String REPOSITION_TYPE_RANDOM_PROPERTY_VALUE = "random";
    public static final String REPOSITION_TYPE_EDGE_PROPERTY_VALUE = "edge";
    
    private final String mMovementType;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformMovementData(String movementType, ObjectMap<String, String> properties) {
        mMovementType = movementType;
        mProperties = properties;
    }
    
    public String getMovementType() {
        return mMovementType;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
}
