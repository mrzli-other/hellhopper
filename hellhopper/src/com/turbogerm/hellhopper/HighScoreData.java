package com.turbogerm.hellhopper;

public final class HighScoreData {
    
    private final String mName;
    private final int mScore;
    private final long mTime;
    
    public HighScoreData(String name, int score, long time) {
        mName = name;
        mScore = score;
        mTime = time;
    }
    
    public String getName() {
        return mName;
    }
    
    public int getScore() {
        return mScore;
    }
    
    public long getTime() {
        return mTime;
    }
}
