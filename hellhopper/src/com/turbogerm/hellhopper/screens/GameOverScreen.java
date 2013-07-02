package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.screens.general.CustomButtonAction;
import com.turbogerm.hellhopper.screens.general.CustomImageButton;
import com.turbogerm.hellhopper.screens.general.ScreenBackground;

public final class GameOverScreen extends ScreenBase {
    
    private static final float BUTTON_X = 105.0f;
    private static final float BUTTON_Y = 20.0f;
    
    private static final int NAME_MAX_LENGTH = 20;
    
    private final ScreenBackground mScreenBackground;
    
    private final Label mGameOverLabel;
    private final Label mPlacementLabel;
    private final TextField mNameTextField;
    
    public GameOverScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        mScreenBackground = new ScreenBackground(mAssetManager);
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("xxxl-font");
        
        final float gameOverLabelY = 600.0f;
        final float gameOverLabelHeight = 135.0f;
        
        mGameOverLabel = new Label("", mGuiSkin);
        mGameOverLabel.setBounds(0.0f, gameOverLabelY, HellHopper.VIEWPORT_WIDTH, gameOverLabelHeight);
        mGameOverLabel.setStyle(labelStyle);
        mGameOverLabel.setAlignment(Align.center);
        mGuiStage.addActor(mGameOverLabel);
        
        final float placementLabelY = gameOverLabelY - gameOverLabelHeight;
        final float placementLabelHeight = 90.0f;
        
        mPlacementLabel = new Label("", mGuiSkin);
        mPlacementLabel.setBounds(0.0f, placementLabelY, HellHopper.VIEWPORT_WIDTH, placementLabelHeight);
        mPlacementLabel.setStyle(labelStyle);
        mPlacementLabel.setAlignment(Align.center);
        mGuiStage.addActor(mPlacementLabel);
        
        final float nameTextFieldY = placementLabelY - placementLabelHeight;
        final float nameTextFieldWidth = 360.0f;
        final float nameTextFieldHeight = 50.0f;
        final float nameTextFieldX = (HellHopper.VIEWPORT_WIDTH - nameTextFieldWidth) / 2.0f;
        
        mNameTextField = new TextField("", mGuiSkin);
        mNameTextField.setBounds(nameTextFieldX, nameTextFieldY, nameTextFieldWidth, nameTextFieldHeight);
        mNameTextField.setMaxLength(NAME_MAX_LENGTH);
        mGuiStage.addActor(mNameTextField);
        
        CustomImageButton button = new CustomImageButton(
                BUTTON_X, BUTTON_Y,
                ResourceNames.GUI_BUTTON_CONTINUE_UP_TEXTURE,
                ResourceNames.GUI_BUTTON_CONTINUE_DOWN_TEXTURE,
                getContinueAction(),
                mAssetManager);
        button.addToStage(mGuiStage);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
        
        int score = mGameData.getScore();
        
        String gameOverText = String.format("Game over!\nYour score is:\n%d", score);
        mGameOverLabel.setText(gameOverText);
        
        int scorePlace = mGameData.getHighScoresData().getPlaceForScore(score);
        if (scorePlace >= 0) {
            String placementText = String.format("You place %d.\non high score list.", scorePlace + 1);
            mPlacementLabel.setText(placementText);
            mNameTextField.setText(mGameData.getHighScoresData().getLastEnteredName());
            mNameTextField.setVisible(true);
        } else {
            mPlacementLabel.setText("");
            mNameTextField.setVisible(false);
        }
    }
    
    @Override
    public void renderImpl(float delta) {
        mScreenBackground.update(delta);
        
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mBatch.end();
    }
    
    private static InputListener getStageInputListener(final GameOverScreen loseGameScreen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    loseGameScreen.mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private CustomButtonAction getContinueAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                boolean isHighScore = mGameData.getHighScoresData().insertHighScore(
                        mNameTextField.getText(), mGameData.getScore());
                
                if (isHighScore) {
                    mGame.setScreen(HellHopper.HIGH_SCORE_SCREEN_NAME);
                } else {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                }
            }
        };
    }
}
