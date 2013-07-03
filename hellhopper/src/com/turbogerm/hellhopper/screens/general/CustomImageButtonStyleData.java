package com.turbogerm.hellhopper.screens.general;

public final class CustomImageButtonStyleData {
    
    private final int mId;
    private final String mUpTexturePath;
    private final String mDownTexturePath;
    
    public CustomImageButtonStyleData(int id, String upTexturePath, String downTexturePath) {
        mId = id;
        mUpTexturePath = upTexturePath;
        mDownTexturePath = downTexturePath;
    }
    
    public int getId() {
        return mId;
    }
    
    public String getUpTexturePath() {
        return mUpTexturePath;
    }
    
    public String getDownTexturePath() {
        return mDownTexturePath;
    }
}
