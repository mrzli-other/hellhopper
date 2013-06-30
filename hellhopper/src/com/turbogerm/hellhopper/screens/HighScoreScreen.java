package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.gamedata.HighScoreData;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class HighScoreScreen extends ScreenBase {
    
    private static final float HIGH_SCORE_PADDING = 10.0f;
    private static final float HIGH_SCORE_HEIGHT = 35.0f;
    private static final float HIGH_SCORE_INDEX_WIDTH = 30.0f;
    private static final float HIGH_SCORE_NAME_WIDTH = 240.0f;
    private static final float HIGH_SCORE_VALUE_WIDTH =
            HellHopper.VIEWPORT_WIDTH - HIGH_SCORE_INDEX_WIDTH - HIGH_SCORE_NAME_WIDTH - 4.0f * HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_INDEX_X = HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_NAME_X =
            HIGH_SCORE_INDEX_X + HIGH_SCORE_INDEX_WIDTH + HIGH_SCORE_PADDING;
    private static final float HIGH_SCORE_VALUE_X =
            HIGH_SCORE_NAME_X + HIGH_SCORE_NAME_WIDTH + HIGH_SCORE_PADDING;
    
    public HighScoreScreen(HellHopper game) {
        super(game);
        
        mClearColor = Color.DARK_GRAY;
        
        mGuiStage.addListener(getStageInputListener(this));
    }
    
    @Override
    public void show() {
        super.show();
        
        mGuiStage.clear();
        
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("large-font");
        
        Array<HighScoreData> highScores = mGameData.getHighScoresData().getHighScores();
        for (int i = 0; i < highScores.size; i++) {
            float highScoreY = HellHopper.VIEWPORT_HEIGHT - (i + 1) * HIGH_SCORE_HEIGHT;
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
        final float buttonX = (HellHopper.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
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
                    screen.mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
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
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                }
            }
        };
    }
}
