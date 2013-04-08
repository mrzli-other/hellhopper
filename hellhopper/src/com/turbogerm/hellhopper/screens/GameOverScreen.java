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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.SuchyBlocks;

public final class GameOverScreen extends ScreenBase {
    
    private static final int NAME_MAX_LENGTH = 20;
    
    private final Label mGameOverLabel;
    private final Label mPlacementLabel;
    private final TextField mNameTextField;
    
    public GameOverScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("xxxl-font", BitmapFont.class); // mResources.getFont("xxxl");
        
        final float gameOverLabelY = 600.0f;
        final float gameOverLabelHeight = 135.0f;
        
        mGameOverLabel = new Label("", mGuiSkin);
        mGameOverLabel.setBounds(0.0f, gameOverLabelY, SuchyBlocks.VIEWPORT_WIDTH, gameOverLabelHeight);
        mGameOverLabel.setStyle(labelStyle);
        mGameOverLabel.setAlignment(Align.center);
        mGuiStage.addActor(mGameOverLabel);
        
        final float placementLabelY = gameOverLabelY - gameOverLabelHeight;
        final float placementLabelHeight = 90.0f;
        
        mPlacementLabel = new Label("", mGuiSkin);
        mPlacementLabel.setBounds(0.0f, placementLabelY, SuchyBlocks.VIEWPORT_WIDTH, placementLabelHeight);
        mPlacementLabel.setStyle(labelStyle);
        mPlacementLabel.setAlignment(Align.center);
        mGuiStage.addActor(mPlacementLabel);
        
        // TextFieldStyle textFieldStyle = new TextFieldStyle(mGuiSkin.get(TextFieldStyle.class));
        // textFieldStyle.font = mGuiSkin.get("xl-font", BitmapFont.class); //mResources.getFont("xl");
        
        final float nameTextFieldY = placementLabelY - placementLabelHeight;
        final float nameTextFieldWidth = 360.0f;
        final float nameTextFieldHeight = 50.0f;
        final float nameTextFieldX = (SuchyBlocks.VIEWPORT_WIDTH - nameTextFieldWidth) / 2.0f;
        
        mNameTextField = new TextField("Enter Name", mGuiSkin);
        mNameTextField.setBounds(nameTextFieldX, nameTextFieldY, nameTextFieldWidth, nameTextFieldHeight);
        // mNameTextField.setStyle(textFieldStyle);
        mNameTextField.setMaxLength(NAME_MAX_LENGTH);
        mGuiStage.addActor(mNameTextField);
        
        final float buttonWidth = 360.0f;
        final float buttonHeight = 80.0f;
        final float buttonX = (SuchyBlocks.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
        final float buttonY = buttonX;
        
        TextureRegion continueUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_CONTINUE_UP_TEXTURE));
        Drawable continueUpDrawable = new TextureRegionDrawable(continueUpTextureRegion);
        TextureRegion continueDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_CONTINUE_DOWN_TEXTURE));
        Drawable continueDownDrawable = new TextureRegionDrawable(continueDownTextureRegion);
        ImageButton continueButton = new ImageButton(continueUpDrawable, continueDownDrawable);
        continueButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        continueButton.addListener(getContinueInputListener(continueButton));
        mGuiStage.addActor(continueButton);
    }
    
    @Override
    public void show() {
        super.show();
        
        int score = mGameData.getScore();
        
        String gameOverText = String.format("Game over!\nYour score is:\n%d", score);
        mGameOverLabel.setText(gameOverText);
        
        int scorePlace = mGameData.getHighScoresData().getPlaceForScore(score);
        if (scorePlace >= 0) {
            String placementText = String.format("You place %d.\non high score list.", scorePlace + 1);
            mPlacementLabel.setText(placementText);
        } else {
            mPlacementLabel.setText("");
        }
    }
    
    private static InputListener getStageInputListener(final GameOverScreen loseGameScreen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    loseGameScreen.mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private InputListener getContinueInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGameData.getHighScoresData().insertHighScore(mNameTextField.getText(), mGameData.getScore());
                    mGame.setScreen(SuchyBlocks.HIGH_SCORE_SCREEN_NAME);
                }
            }
        };
    }
}
