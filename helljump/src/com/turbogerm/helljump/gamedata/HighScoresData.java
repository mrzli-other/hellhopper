package com.turbogerm.helljump.gamedata;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.GameUtils;

public final class HighScoresData {
    
    private static final String PREFERENCES_NAME = "HellJump_HighScores";
    
    private static final int HIGH_SCORES_CAPACITY = 24;
    private static final int NUM_HIGH_SCORES = 20;
    
    private static final String LAST_ENTERED_NAME_KEY = "LastEnteredName";
    
    private static final String DEFAULT_NAME = "<No_Name>";
    private static final String DEFAULT_ENTER_NAME_TEXT = "Enter Name";
    
    private final Array<HighScoreData> mHighScores;
    private String mLastEnteredName;
    
    private final Preferences mPreferences;
    
    public HighScoresData() {
        mHighScores = new Array<HighScoreData>(true, HIGH_SCORES_CAPACITY);
        
        mPreferences = Gdx.app.getPreferences(PREFERENCES_NAME);
        load();
    }
    
    private void load() {
        mLastEnteredName = mPreferences.getString(LAST_ENTERED_NAME_KEY);
        if (GameUtils.isNullOrEmpty(mLastEnteredName)) {
            mLastEnteredName = DEFAULT_ENTER_NAME_TEXT;
        }
        loadHighScores();
    }
    
    public void save() {
        saveHighScores();
        mPreferences.putString(LAST_ENTERED_NAME_KEY, mLastEnteredName);
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
    
    public boolean insertHighScore(String name, int score) {
        int scorePlace = getPlaceForScore(score);
        if (scorePlace < 0) {
            return false;
        }
        
        HighScoreData highScore = new HighScoreData(name, score, 0l);
        mHighScores.insert(scorePlace, highScore);
        mHighScores.truncate(NUM_HIGH_SCORES);
        mLastEnteredName = name;
        save();
        
        return true;
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
    
    public String getLastEnteredName() {
        return mLastEnteredName;
    }
}
