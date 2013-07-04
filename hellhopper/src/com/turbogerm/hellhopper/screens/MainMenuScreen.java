package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.turbogerm.germlibrary.controls.CustomButtonAction;
import com.turbogerm.germlibrary.controls.CustomImageButton;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.screens.general.LibGdxLogo;
import com.turbogerm.hellhopper.screens.general.ScreenBackground;
import com.turbogerm.hellhopper.screens.general.TurboGermLogo;

public final class MainMenuScreen extends ScreenBase {
    
    private static final float TITLE_X = 30.0f;
    private static final float TITLE_Y = 719.0f;
    
    private static final float BUTTON_X = 105.0f;
    private static final float START_BUTTON_Y = 500.0f;
    private static final float HIGH_SCORE_BUTTON_Y = 400.0f;
    private static final float CREDITS_BUTTON_Y = 300.0f;
    
    private final ScreenBackground mScreenBackground;
    
    private final Texture mTitleTexture;
    private final TurboGermLogo mTurboGermLogo;
    private final LibGdxLogo mLibGdxLogo;
    
    public MainMenuScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mScreenBackground = new ScreenBackground(mAssetManager);
        
        mTitleTexture = mAssetManager.get(ResourceNames.GUI_MAIN_MENU_TITLE_TEXTURE);
        
        mTurboGermLogo = new TurboGermLogo(mAssetManager);
        mLibGdxLogo = new LibGdxLogo(mAssetManager);
        
        // menu buttons
        CustomImageButton startButton = new CustomImageButton(
                BUTTON_X, START_BUTTON_Y,
                ResourceNames.GUI_MAIN_MENU_BUTTON_START_UP_TEXTURE,
                ResourceNames.GUI_MAIN_MENU_BUTTON_START_DOWN_TEXTURE,
                getStartAction(),
                mAssetManager);
        startButton.addToStage(mGuiStage);
        
        CustomImageButton highScoreButton = new CustomImageButton(
                BUTTON_X, HIGH_SCORE_BUTTON_Y,
                ResourceNames.GUI_MAIN_MENU_BUTTON_HIGH_SCORE_UP_TEXTURE,
                ResourceNames.GUI_MAIN_MENU_BUTTON_HIGH_SCORE_DOWN_TEXTURE,
                getHighScoreAction(),
                mAssetManager);
        highScoreButton.addToStage(mGuiStage);
        
        CustomImageButton creditsButton = new CustomImageButton(
                BUTTON_X, CREDITS_BUTTON_Y,
                ResourceNames.GUI_MAIN_MENU_BUTTON_CREDITS_UP_TEXTURE,
                ResourceNames.GUI_MAIN_MENU_BUTTON_CREDITS_DOWN_TEXTURE,
                getCreditsAction(),
                mAssetManager);
        creditsButton.addToStage(mGuiStage);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
    }
    
    @Override
    protected void updateImpl(float delta) {
        mScreenBackground.update(delta);
    }
    
    @Override
    public void renderImpl() {
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mBatch.draw(mTitleTexture, TITLE_X, TITLE_Y);
        mTurboGermLogo.render(mBatch);
        mLibGdxLogo.render(mBatch);
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
    
    private CustomButtonAction getStartAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellHopper.PLAY_SCREEN_NAME);
            }
        };
    }
    
    private CustomButtonAction getHighScoreAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellHopper.HIGH_SCORE_SCREEN_NAME);
            }
        };
    }
    
    private CustomButtonAction getCreditsAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellHopper.CREDITS_SCREEN_NAME);
            }
        };
    }
}