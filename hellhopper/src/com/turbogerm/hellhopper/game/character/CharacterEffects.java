package com.turbogerm.hellhopper.game.character;

public final class CharacterEffects {
    
    private int mLives;
    private int mScore;
    private int mNumRubies;
    
    private float mShieldRemaining;
    private int mFartsRemaining;
    private float mHighJumpRemaining;
    
    public CharacterEffects() {
    }
    
    public void reset() {
        mLives = 0;
        mScore = 0;
        mNumRubies = 0;
        
        mShieldRemaining = 0.0f;
        mFartsRemaining = 0;
        mHighJumpRemaining = 0.0f;
    }
    
    public void update(float delta) {
        mShieldRemaining = Math.max(mShieldRemaining - delta, 0.0f);
        mHighJumpRemaining = Math.max(mHighJumpRemaining - delta, 0.0f);
        
        // fart and high jump movement should always be shielded
        if (isFarting() || isHighJump()) {
            mShieldRemaining = Math.max(mShieldRemaining, 1.0f);
        }
    }
    
    public int getLives() {
        return mLives;
    }
    
    public int getScore() {
        return mScore;
    }
    
    public int getNumRubies() {
        return mNumRubies;
    }
    
    public boolean isShielded() {
        return mShieldRemaining > 0.0f;
    }
    
    public float getShieldRemaining() {
        return mShieldRemaining;
    }
    
    public boolean isFarting() {
        return mFartsRemaining > 0;
    }
    
    public boolean isHighJump() {
        return mHighJumpRemaining > 0.0f;
    }
    
    public void addLife() {
        mLives++;
    }
    
    public void subtractLife() {
        mLives--;
    }
    
    public void addScore(int score) {
        mScore += score;
    }
    
    public void addRuby() {
        mNumRubies++;
    }
    
    public void setShield(float duration) {
        mShieldRemaining = Math.max(mShieldRemaining, duration);
    }
    
    public void setFarts(int farts) {
        mFartsRemaining = Math.max(mFartsRemaining, farts);
    }
    
    public void subtractFart() {
        mFartsRemaining--;
    }
    
    public void setHighJump(float duration) {
        mHighJumpRemaining = Math.max(mHighJumpRemaining, duration);
    }
}
