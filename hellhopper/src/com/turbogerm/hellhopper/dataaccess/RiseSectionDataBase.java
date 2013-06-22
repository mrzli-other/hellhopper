package com.turbogerm.hellhopper.dataaccess;

public abstract class RiseSectionDataBase {
    
    // private static final String INITIAL_TYPE = "initial";
    // private static final String GENERIC_TYPE = "generic";
    
    private static final String TRANSITION_TYPE = "transition";
    
    private static final String NORMAL_TYPE = "normal";
    private static final String MOVING_TYPE = "moving";
    private static final String REPOSITION_TYPE = "reposition";
    
    private static final String ENEMY_TYPE = "enemy";
    
    // private static final String JUMP_BOOST_TYPE = "jumpboost";
    private static final String VISIBLE_ON_JUMP_TYPE = "visibleonjump";
    private static final String CRUMBLE_TYPE = "crumble";
    private static final String FLAME_TYPE = "flame";
    
    private final String mType;
    private final String mName;
    private final int mDifficulty;
    
    public RiseSectionDataBase(String type, String name, int difficulty) {
        mType = type;
        mName = name;
        mDifficulty = difficulty;
    }
    
    public abstract boolean isMetadata();
    
    public String getType() {
        return mType;
    }
    
    public String getName() {
        return mName;
    }
    
    public int getDifficulty() {
        return mDifficulty;
    }
    
    public static boolean isTransitionType(String type) {
        return TRANSITION_TYPE.endsWith(type);
    }
    
    public static boolean isStandardType(String type) {
        return NORMAL_TYPE.equals(type) || MOVING_TYPE.equals(type) || REPOSITION_TYPE.equals(type);
    }
    
    public static boolean isEnemyType(String type) {
        return ENEMY_TYPE.equals(type);
    }
    
    public static boolean isSpecialType(String type) {
        return /*JUMP_BOOST_TYPE.equals(type) ||*/ VISIBLE_ON_JUMP_TYPE.equals(type) || CRUMBLE_TYPE.equals(type) || 
                FLAME_TYPE.equals(type);
    }
}
