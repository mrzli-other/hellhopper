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

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HighScoreData;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.SuchyBlocks;

public final class HighScoreScreen extends ScreenBase {
    
    private static final float HIGH_SCORE_PADDING = 10.0f;
    private static final float HIGH_SCORE_HEIGHT = 35.0f;
    private static final float HIGH_SCORE_INDEX_WIDTH = 30.0f;
    private static final float HIGH_SCORE_NAME_WIDTH = 240.0f;
    private static final float HIGH_SCORE_VALUE_WIDTH =
            SuchyBlocks.VIEWPORT_WIDTH - HIGH_SCORE_INDEX_WIDTH - HIGH_SCORE_NAME_WIDTH - 4.0f * HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_INDEX_X = HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_NAME_X =
            HIGH_SCORE_INDEX_X + HIGH_SCORE_INDEX_WIDTH + HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_VALUE_X =
            HIGH_SCORE_NAME_X + HIGH_SCORE_NAME_WIDTH + HIGH_SCORE_PADDING;
    
    public HighScoreScreen(SuchyBlocks game) {
        super(game);
        
        mClearColor = Color.DARK_GRAY;
        
        mGuiStage.addListener(getStageInputListener(this));
    }
    
    @Override
    public void show() {
        super.show();
        
        mGuiStage.clear();
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("large-font", BitmapFont.class); // mResources.getFont("medium");
        
        Array<HighScoreData> highScores = mGameData.getHighScoresData().getHighScores();
        for (int i = 0; i < highScores.size; i++) {
            float highScoreY = SuchyBlocks.VIEWPORT_HEIGHT - (i + 1) * HIGH_SCORE_HEIGHT;
            HighScoreData highScore = highScores.get(i);
            
            Label highScoreIndexLabel = new Label(String.valueOf(i + 1) + ".", mGuiSkin);
            highScoreIndexLabel.setBounds(HIGH_SCORE_INDEX_X, highScoreY, HIGH_SCORE_INDEX_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreIndexLabel.setStyle(labelStyle);
            highScoreIndexLabel.setAlignment(Align.right);
            mGuiStage.addActor(highScoreIndexLabel);
            
            Label highScoreNameLabel = new Label(highScore.getName(), mGuiSkin);
            highScoreNameLabel.setBounds(HIGH_SCORE_NAME_X, highScoreY, HIGH_SCORE_NAME_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreNameLabel.setStyle(labelStyle);
            highScoreNameLabel.setAlignment(Align.left);
            mGuiStage.addActor(highScoreNameLabel);
            
            Label highScoreValueLabel = new Label(String.valueOf(highScore.getScore()), mGuiSkin);
            highScoreValueLabel.setBounds(HIGH_SCORE_VALUE_X, highScoreY, HIGH_SCORE_VALUE_WIDTH, HIGH_SCORE_HEIGHT);
            highScoreValueLabel.setStyle(labelStyle);
            highScoreValueLabel.setAlignment(Align.right);
            mGuiStage.addActor(highScoreValueLabel);
        }
        
        final float buttonWidth = 360.0f; 
        final float buttonHeight = 80.0f; 
        final float buttonX = (SuchyBlocks.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
        final float buttonY = 10.0f;
        
        TextureRegion backUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE));
        Drawable backUpDrawable = new TextureRegionDrawable(backUpTextureRegion);
        TextureRegion backDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE));
        Drawable backDownDrawable = new TextureRegionDrawable(backDownTextureRegion);
        ImageButton backButton = new ImageButton(backUpDrawable, backDownDrawable);
        backButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        backButton.addListener(getBackInputListener(backButton));
        mGuiStage.addActor(backButton);
    }
    
    private static InputListener getStageInputListener(final HighScoreScreen screen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private InputListener getBackInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                }
            }
        };
    }
}
