package com.turbogerm.hellhopper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Logger;
import com.turbogerm.hellhopper.screens.GameOverScreen;
import com.turbogerm.hellhopper.screens.HighScoreScreen;
import com.turbogerm.hellhopper.screens.InfoScreen;
import com.turbogerm.hellhopper.screens.MainMenuScreen;
import com.turbogerm.hellhopper.screens.PlayScreen;
import com.turbogerm.hellhopper.screens.SplashScreen;
    
public class HellHopper extends Game {
    
    public static final String LOG = HellHopper.class.getSimpleName();
    
    public static final String SPLASH_SCREEN_NAME = "Splash";
    public static final String MAIN_MENU_SCREEN_NAME = "MainMenu";
    public static final String PLAY_SCREEN_NAME = "Play";
    public static final String HIGH_SCORE_SCREEN_NAME = "HighScore";
    public static final String INFO_SCREEN_NAME = "Info";
    public static final String GAME_OVER_SCREEN_NAME = "GameOver";
    
    public static final float VIEWPORT_WIDTH = 450.0f;
    public static final float VIEWPORT_HEIGHT = 800.0f;
    
    //private FPSLogger mFpsLogger;
    private ArrayMap<String, Screen> mScreens;
    
    private Resources mResources;
    private GameData mGameData;
    
    @Override
    public void create() {
        
        //mFpsLogger = new FPSLogger();
        Gdx.app.setLogLevel(Logger.DEBUG);
        Gdx.input.setCatchBackKey(true);
        
        initializeResourcesAndGameData();
        initializeScreens();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        mGameData.dispose();
        mResources.dispose();
    }
    
    @Override
    public void render() {
        //mFpsLogger.log();
        
        super.render();
    }
    
    public Screen getScreen(String name) {
        return mScreens.get(name);
    }
    
    public void setScreen(String name) {
        setScreen(getScreen(name));
    }
    
    public Resources getResources() {
        return mResources;
    }
    
    public GameData getGameData() {
        return mGameData;
    }
    
    private void initializeResourcesAndGameData() {
        
        mResources = new Resources();
        mGameData = new GameData();
    }
    
    private void initializeScreens() {
        mScreens = new ArrayMap<String, Screen>();
        mScreens.put(SPLASH_SCREEN_NAME, new SplashScreen(this));
        mScreens.put(MAIN_MENU_SCREEN_NAME, new MainMenuScreen(this));
        mScreens.put(PLAY_SCREEN_NAME, new PlayScreen(this));
        mScreens.put(HIGH_SCORE_SCREEN_NAME, new HighScoreScreen(this));
        mScreens.put(INFO_SCREEN_NAME, new InfoScreen(this));
        mScreens.put(GAME_OVER_SCREEN_NAME, new GameOverScreen(this));
        
        setScreen(SPLASH_SCREEN_NAME);
    }
}
