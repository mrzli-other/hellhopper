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