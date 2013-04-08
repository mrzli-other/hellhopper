/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;

public final class HighScoresData {
    
    private static final String PREFERENCES_NAME = "SuchyBlocks_HighScores";
    
    private static final int HIGH_SCORES_CAPACITY = 24;
    private static final int NUM_HIGH_SCORES = 20;
    
    private static final String DEFAULT_NAME = "<No_Name>";
    
    private final Array<HighScoreData> mHighScores;
    private final Preferences mPreferences;
    
    public HighScoresData() {
        mHighScores = new Array<HighScoreData>(true, HIGH_SCORES_CAPACITY);
        
        mPreferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        loadHighScores();
    }
    
    public void save() {
        saveHighScores();
        mPreferences.flush();
    }
    
    public int getPlaceForScore(int score) {
        for (int i = 0; i < NUM_HIGH_SCORES; i++) {
            if (score > mHighScores.get(i).getScore()) {
                return i;
            }
        }
        
        return -1;
    }
    
    public void insertHighScore(String name, int score) {
        int scorePlace = getPlaceForScore(score);
        if (scorePlace < 0) {
            return;
        }
        
        HighScoreData highScore = new HighScoreData(name, score, 0l);
        mHighScores.insert(scorePlace, highScore);
        mHighScores.truncate(NUM_HIGH_SCORES);
        save();
    }
    
    private void loadHighScores() {
        mHighScores.clear();
        
        for (int i = 0; i < NUM_HIGH_SCORES; i++) {
            String name = getScoreName(i);
            if (name == null || name.equals("")) {
                name = DEFAULT_NAME;
            }
            int score = getScoreValue(i);
            
            HighScoreData highScore = new HighScoreData(name, score, 0l);
            mHighScores.add(highScore);
        }
    }
    
    private void saveHighScores() {
        for (int i = 0; i < NUM_HIGH_SCORES; i++) {
            HighScoreData highScore = mHighScores.get(i);
            putScoreName(i, highScore.getName());
            putScoreValue(i, highScore.getScore());
        }
    }
    
    private String getScoreName(int scoreIndex) {
        String key = getScoreNameKey(scoreIndex);
        return mPreferences.getString(key);
    }
    
    private void putScoreName(int scoreIndex, String scoreName) {
        String key = getScoreNameKey(scoreIndex);
        mPreferences.putString(key, scoreName);
    }
    
    private String getScoreNameKey(int scoreIndex) {
        return String.format("ScoreName%02d", scoreIndex + 1);
    }
    
    private int getScoreValue(int scoreIndex) {
        String key = getScoreValueKey(scoreIndex);
        return mPreferences.getInteger(key);
    }
    
    private void putScoreValue(int scoreIndex, int scoreValue) {
        String key = getScoreValueKey(scoreIndex);
        mPreferences.putInteger(key, scoreValue);
    }
    
    private String getScoreValueKey(int scoreIndex) {
        return String.format("ScoreValue%02d", scoreIndex + 1);
    }
    
    public Array<HighScoreData> getHighScores() {
        return mHighScores;
    }
}
