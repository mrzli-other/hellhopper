package com.turbogerm.hellhopper.gamedata;

public final class GameData {
    
    private int mScore;
    
    private final HighScoresData mHighScoresData;
    
    public GameData() {
        mScore = 0;
        mHighScoresData = new HighScoresData();
    }
    
    public void dispose() {
        mHighScoresData.save();
    }
    
    public int getScore() {
        return mScore;
    }
    
    public void setScore(int score) {
        mScore = score;
    }
    
    public HighScoresData getHighScoresData() {
        return mHighScoresData;
    }
}
