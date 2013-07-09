package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.utils.ObjectMap;

public final class PlatformFeatureData {
    
    public static final String JUMP_BOOST_FEATURE = "jumpboost";
    public static final String FLAME_FEATURE = "flame";
    public static final String VISIBLE_ON_JUMP_FEATURE = "visibleonjump";
    
    public static final String JUMP_BOOST_POSITION_PROPERTY = "position";
    public static final String JUMP_BOOST_POWER_PROPERTY = "power";
    
    public static final String JUMP_BOOST_POWER_LOW_PROPERTY_VALUE = "low";
    public static final String JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE = "medium";
    public static final String JUMP_BOOST_POWER_HIGH_PROPERTY_VALUE = "high";
    
    public static final String FLAME_CYCLE_OFFSET_PROPERTY = "cycleoffset";
    public static final String FLAME_FLAME_DURATION_PROPERTY = "flameduration";
    public static final String FLAME_DORMANT_DURATION_PROPERTY = "dormantduration";
    public static final String FLAME_TRANSITION_DURATION_PROPERTY = "transitionduration";
    
    private final String mFeatureType;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformFeatureData(String featureType, ObjectMap<String, String> properties) {
        mFeatureType = featureType;
        mProperties = properties;
    }
    
    public String getFeatureType() {
        return mFeatureType;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
}
