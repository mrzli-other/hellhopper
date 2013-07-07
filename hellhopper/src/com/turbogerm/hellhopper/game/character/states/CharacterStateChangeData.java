package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.utils.ObjectMap;

public final class CharacterStateChangeData {
    
    public static final String IS_SAW_KEY = "issaw"; 
    
    private static final int DATA_CAPACITY = 10;
    
    private final ObjectMap<String, Object> mData;
    
    public CharacterStateChangeData() {
        mData = new ObjectMap<String, Object>(DATA_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getData(String key) {
        return (T) mData.get(key);
    }
    
    public void setData(String key, Object value) {
        mData.put(key, value);
    }
    
    public void clear() {
        mData.clear();
    }
}
