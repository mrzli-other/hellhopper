package com.turbogerm.helljump.game;

public final class CollisionEffects {
    
    public static final int JUMP_BOOST = 0;
    public static final int BURN = 1;
    public static final int REPOSITION_PLATFORMS = 2;
    public static final int VISIBLE_ON_JUMP = 3;
    private static final int NUM_EFFECTS = 4;
    
    public static final int JUMP_BOOST_SPEED_INDEX = 0;
    public static final int JUMP_BOOST_SOUND_VOLUME_INDEX = 1; 
    
    private final boolean mEffects[];
    private final float mValues[][];
    
    public CollisionEffects() {
        mEffects = new boolean[NUM_EFFECTS];
        mValues = new float[NUM_EFFECTS][];
        
        for (int i = 0; i < NUM_EFFECTS; i++) {
            if (i == JUMP_BOOST) {
                mValues[i] = new float[2];
            } else {
                mValues[i] = new float[i];
            }
        }
    }
    
    public void clear() {
        for (int i = 0; i < NUM_EFFECTS; i++) {
            mEffects[i] = false;
        }
    }
    
    public void set(int effect) {
        mEffects[effect] = true;
    }
    
    public void set(int effect, int valueIndex, float value) {
        mEffects[effect] = true;
        mValues[effect][valueIndex] = value;
    }
    
    public void set(int effect, float value) {
        set(effect, 0, value);
    }
    
    public boolean isEffectActive(int effect) {
        return mEffects[effect];
    }
    
    public float getValue(int effect, int valueIndex) {
        return mValues[effect][valueIndex];
    }
    
    public float getValue(int effect) {
        return getValue(effect, 0);
    }
}
