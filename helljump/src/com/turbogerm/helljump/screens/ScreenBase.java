// based on AbstractScreen from http://steigert.blogspot.com/2012/02/2-libgdx-tutorial-game-screens.html (by Gustavo Steigert)
// modified by Goran Mrzljak
package com.turbogerm.helljump.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turbogerm.germlibrary.game.Screen;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.gamedata.GameData;
import com.turbogerm.helljump.init.InitData;
import com.turbogerm.helljump.resources.Resources;

public abstract class ScreenBase implements Screen {
    
    protected final HellJump mGame;
    protected final InitData mInitData;
    protected final Resources mResources;
    protected final AssetManager mAssetManager;
    protected final Skin mGuiSkin;
    protected final GameData mGameData;
    protected final CameraData mCameraData;
    
    protected final SpriteBatch mBatch;
    protected final Stage mGuiStage;
    
    protected final Color mClearColor;
    
    protected final Rectangle mGuiCameraRect;
    
    public ScreenBase(HellJump game) {
        mGame = game;
        mInitData = mGame.getInitData();
        mResources = mGame.getResources();
        mAssetManager = mResources.getAssetManager();
        mGuiSkin = mResources.getGuiSkin();
        mGameData = mGame.getGameData();
        mCameraData = mGame.getCameraData();
        
        mGuiCameraRect = mCameraData.getGuiCameraRect();
        
        mBatch = new SpriteBatch();
        
        mGuiStage = new Stage();
        mGuiStage.setCamera(mCameraData.getGuiCamera());
        
        mClearColor = new Color(Color.BLACK);
    }
    
    protected String getName() {
        return getClass().getSimpleName();
    }
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(mGuiStage);
    }
    
    @Override
    public void resize(int width, int height) {
    }
    
    @Override
    public final void update(float delta) {
        updateImpl(delta);
        mGuiStage.act(delta);
    }
    
    @Override
    public final void render() {
        
        if (mClearColor != null) {
            Gdx.gl.glClearColor(mClearColor.r, mClearColor.g, mClearColor.b, mClearColor.a);
        } else {
            Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mBatch.setProjectionMatrix(mCameraData.getGuiMatrix());
        renderImpl();
        
        mGuiStage.draw();
    }
    
    protected void updateImpl(float delta) {
    }
    
    protected void renderImpl() {
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
        mGuiStage.dispose();
        mBatch.dispose();
    }
    
}