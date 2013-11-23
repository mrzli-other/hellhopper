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
package com.turbogerm.helljump.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.controls.CustomButtonAction;
import com.turbogerm.germlibrary.controls.CustomImageButton;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.gamedata.HighScoreData;
import com.turbogerm.helljump.resources.ResourceNames;
import com.turbogerm.helljump.screens.general.ScreenBackground;

public final class HighScoreScreen extends ScreenBase {
    
    private static final float HIGH_SCORE_PADDING = 10.0f;
    private static final float HIGH_SCORE_HEIGHT = 35.0f;
    private static final float HIGH_SCORE_INDEX_WIDTH = 30.0f;
    private static final float HIGH_SCORE_NAME_WIDTH = 240.0f;
    private static final float HIGH_SCORE_VALUE_WIDTH =
            HellJump.VIEWPORT_WIDTH - HIGH_SCORE_INDEX_WIDTH - HIGH_SCORE_NAME_WIDTH - 4.0f * HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_INDEX_X = HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_NAME_X =
            HIGH_SCORE_INDEX_X + HIGH_SCORE_INDEX_WIDTH + HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_VALUE_X =
            HIGH_SCORE_NAME_X + HIGH_SCORE_NAME_WIDTH + HIGH_SCORE_PADDING;
    
    private static final float BUTTON_X = 105.0f;
    private static final float BUTTON_Y = 20.0f;
    
    private final ScreenBackground mScreenBackground;
    
    public HighScoreScreen(HellJump game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        mScreenBackground = new ScreenBackground(mCameraData, mAssetManager);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
        
        mGuiStage.clear();
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("large-font");
        
        Array<HighScoreData> highScores = mGameData.getHighScoresData().getHighScores();
        for (int i = 0; i < highScores.size; i++) {
            float highScoreY = HellJump.VIEWPORT_HEIGHT - (i + 1) * HIGH_SCORE_HEIGHT;
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
        
        TextureAtlas atlas = mAssetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        
        CustomImageButton button = new CustomImageButton(
                BUTTON_X, BUTTON_Y,
                atlas,
                ResourceNames.GUI_BUTTON_BACK_UP_IMAGE_NAME,
                ResourceNames.GUI_BUTTON_BACK_DOWN_IMAGE_NAME,
                getBackAction(),
                mAssetManager);
        button.addToStage(mGuiStage);
    }
    
    @Override
    protected void updateImpl(float delta) {
        mScreenBackground.update(delta);
    }
    
    @Override
    public void renderImpl() {
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mBatch.end();
    }
    
    private static InputListener getStageInputListener(final HighScoreScreen screen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(HellJump.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private CustomButtonAction getBackAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellJump.MAIN_MENU_SCREEN_NAME);
            }
        };
    }
}
