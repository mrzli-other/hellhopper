package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.turbogerm.hellhopper.game.GameAreaUtils;

public final class ItemData {
    
    public static final String BEANS_TYPE = "beans";
    public static final String SHIELD_TYPE = "shield";
    public static final String JUMP_SUIT_TYPE = "jumpsuit";
    public static final String LIFE_TYPE = "life";
    public static final String COIN_TYPE = "coin";
    public static final String RUBY_TYPE = "ruby";
    public static final String BLACK_BOX_TYPE = "blackbox";
    
    public static final String COIN_TYPE_PROPERTY = "cointype";
    
    public static final String COIN_TYPE_GOLD_PROPERTY_VALUE = "gold";
    public static final String COIN_TYPE_SILVER_PROPERTY_VALUE = "silver";
    public static final String COIN_TYPE_COPPER_PROPERTY_VALUE = "copper";
    
    private final String mType;
    private final float mStep;
    private final float mOffset;
    private final float mAppearanceChance;
    private final int mAttachedToPlatformId;
    private final ObjectMap<String, String> mProperties;
    
    public ItemData(String type, float step, float offset, float appearanceChance,
            int attachedToPlatformId, ObjectMap<String, String> properties) {
        mType = type;
        mStep = step;
        mOffset = offset;
        mAppearanceChance = appearanceChance;
        mAttachedToPlatformId = attachedToPlatformId;
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
    
    public float getAppearanceChance() {
        return mAppearanceChance;
    }
    
    public int getAttachedToPlatformId() {
        return mAttachedToPlatformId;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
    
    public Vector2 getPosition(int startStep) {
        return GameAreaUtils.getPosition(startStep, mStep, mOffset);
    }
}
