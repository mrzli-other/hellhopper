package com.turbogerm.germlibrary.controls;

public final class CustomImageButtonStyleData {
    
    private final int mId;
    private final String mUpImageName;
    private final String mDownImageName;
    
    public CustomImageButtonStyleData(int id, String upImageName, String downImageName) {
        mId = id;
        mUpImageName = upImageName;
        mDownImageName = downImageName;
    }
    
    public int getId() {
        return mId;
    }
    
    public String getUpImageName() {
        return mUpImageName;
    }
    
    public String getDownImageName() {
        return mDownImageName;
    }
}
