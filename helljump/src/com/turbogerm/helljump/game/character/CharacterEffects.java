/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump.game.character;

public final class CharacterEffects {
    
    private int mLives;
    private int mScore;
    private int mNumSignets;
    
    private float mShieldRemaining;
    private int mFartsRemaining;
    private float mHighJumpRemaining;
    
    public CharacterEffects() {
    }
    
    public void reset() {
        mLives = 0;
        mScore = 0;
        mNumSignets = 0;
        
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
    
    public int getNumSignets() {
        return mNumSignets;
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
    
    public void addSignet() {
        mNumSignets++;
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
