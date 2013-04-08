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
package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.SuchyBlocks;

public final class MainMenuScreen extends ScreenBase {
    
    private final Texture mBackgroundTexture;
    
    public MainMenuScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.GUI_BACKGROUND_TEXTURE);
        
        TextButtonStyle menuTextButtonStyle = new TextButtonStyle(mGuiSkin.get(TextButtonStyle.class));
        menuTextButtonStyle.font = mGuiSkin.get("xxxl-font", BitmapFont.class); //mResources.getFont("xxxl");
        
        // menu buttons
        final float buttonWidth = 360.0f; 
        final float buttonHeight = 80.0f; 
        final float buttonPadding = 80.0f;
        
        final float buttonX = (SuchyBlocks.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
        final float firstButtonY = 520.0f;
        final float buttonVerticalStride = buttonHeight + buttonPadding;
        
        TextureRegion startUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_START_UP_TEXTURE));
        Drawable startUpDrawable = new TextureRegionDrawable(startUpTextureRegion);
        TextureRegion startDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_START_DOWN_TEXTURE));
        Drawable startDownDrawable = new TextureRegionDrawable(startDownTextureRegion);
        ImageButton startButton = new ImageButton(startUpDrawable, startDownDrawable);
        startButton.setBounds(buttonX, firstButtonY, buttonWidth, buttonHeight);
        startButton.addListener(getStartInputListener(startButton));
        mGuiStage.addActor(startButton);
        
        TextureRegion highScoreUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_UP_TEXTURE));
        Drawable highScoreUpDrawable = new TextureRegionDrawable(highScoreUpTextureRegion);
        TextureRegion highScoreDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_DOWN_TEXTURE));
        Drawable highScoreDownDrawable = new TextureRegionDrawable(highScoreDownTextureRegion);
        ImageButton highScoreButton = new ImageButton(highScoreUpDrawable, highScoreDownDrawable);
        highScoreButton.setBounds(buttonX, firstButtonY - 1.0f * buttonVerticalStride, buttonWidth, buttonHeight);
        highScoreButton.addListener(getHighScoreInputListener(highScoreButton));
        mGuiStage.addActor(highScoreButton);
        
        TextureRegion infoUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_INFO_UP_TEXTURE));
        Drawable infoUpDrawable = new TextureRegionDrawable(infoUpTextureRegion);
        TextureRegion infoDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_MAIN_MENU_INFO_DOWN_TEXTURE));
        Drawable infoDownDrawable = new TextureRegionDrawable(infoDownTextureRegion);
        ImageButton infoButton = new ImageButton(infoUpDrawable, infoDownDrawable);
        infoButton.setBounds(buttonX, firstButtonY - 2.0f * buttonVerticalStride, buttonWidth, buttonHeight);
        infoButton.addListener(getInfoInputListener(infoButton));
        mGuiStage.addActor(infoButton);
    }
    
    @Override
    public void renderImpl(float delta) {
        mBatch.begin();
        mBatch.draw(mBackgroundTexture, 0.0f, 0.0f, SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        mBatch.end();
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    Gdx.app.exit();
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private InputListener getStartInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.PLAY_SCREEN_NAME);
                }
            }
        };
    }
    
    private InputListener getHighScoreInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.HIGH_SCORE_SCREEN_NAME);
                }
            }
        };
    }
    
    private InputListener getInfoInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.INFO_SCREEN_NAME);
                }
            }
        };
    }
}