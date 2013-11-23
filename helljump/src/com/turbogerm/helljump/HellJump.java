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
package com.turbogerm.helljump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Logger;
import com.turbogerm.germlibrary.game.GameBase;
import com.turbogerm.germlibrary.game.Screen;
import com.turbogerm.helljump.gamedata.GameData;
import com.turbogerm.helljump.init.InitData;
import com.turbogerm.helljump.resources.Resources;
import com.turbogerm.helljump.screens.CreditsScreen;
import com.turbogerm.helljump.screens.GameOverScreen;
import com.turbogerm.helljump.screens.HighScoreScreen;
import com.turbogerm.helljump.screens.MainMenuScreen;
import com.turbogerm.helljump.screens.PlayScreen;
import com.turbogerm.helljump.screens.SplashScreen;
    
public class HellJump extends GameBase {
    
    public static final String LOG = HellJump.class.getSimpleName();
    
    public static final String SPLASH_SCREEN_NAME = "Splash";
    public static final String MAIN_MENU_SCREEN_NAME = "MainMenu";
    public static final String PLAY_SCREEN_NAME = "Play";
    public static final String HIGH_SCORE_SCREEN_NAME = "HighScore";
    public static final String CREDITS_SCREEN_NAME = "Credits";
    public static final String GAME_OVER_SCREEN_NAME = "GameOver";
    
    public static final float VIEWPORT_WIDTH = 450.0f;
    public static final float VIEWPORT_HEIGHT = 800.0f;
    
    //private FPSLogger mFpsLogger;
    private ArrayMap<String, Screen> mScreens;
    
    private Resources mResources;
    private GameData mGameData;
    private InitData mInitData;
    private CameraData mCameraData;
    
    public HellJump(InitData initData) {
        mInitData = initData;
    }
    
    @Override
    public void create() {
        
        //mFpsLogger = new FPSLogger();
        Gdx.app.setLogLevel(Logger.DEBUG);
        Gdx.input.setCatchBackKey(true);
        
        mCameraData = new CameraData(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
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
    
    @Override
    public void resize(int width, int height) {
        mCameraData.resize(width, height);
        super.resize(width, height);
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
    
    public InitData getInitData() {
        return mInitData;
    }
    
    public CameraData getCameraData() {
        return mCameraData;
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
        mScreens.put(CREDITS_SCREEN_NAME, new CreditsScreen(this));
        mScreens.put(GAME_OVER_SCREEN_NAME, new GameOverScreen(this));
        
        setScreen(SPLASH_SCREEN_NAME);
    }
}
