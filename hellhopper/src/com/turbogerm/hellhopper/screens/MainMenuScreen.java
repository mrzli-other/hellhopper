package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.screens.general.LibGdxLogo;
import com.turbogerm.hellhopper.screens.general.ScreenBackground;
import com.turbogerm.hellhopper.screens.general.TurboGermLogo;

public final class MainMenuScreen extends ScreenBase {
    
    private final ScreenBackground mScreenBackground;
    
    private final TurboGermLogo mTurboGermLogo;
    private final LibGdxLogo mLibGdxLogo;
    
    public MainMenuScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mScreenBackground = new ScreenBackground(mAssetManager);
        
        mTurboGermLogo = new TurboGermLogo(mAssetManager);
        mLibGdxLogo = new LibGdxLogo(mAssetManager);
        
        TextButtonStyle menuTextButtonStyle = new TextButtonStyle(mGuiSkin.get(TextButtonStyle.class));
        menuTextButtonStyle.font = mGuiSkin.getFont("xxxl-font");
        
        // menu buttons
        final float buttonWidth = 360.0f;
        final float buttonHeight = 80.0f;
        final float buttonPadding = 80.0f;
        
        final float buttonX = (HellHopper.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
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
    public void show() {
        super.show();
        
        mScreenBackground.reset();
    }
    
    @Override
    public void renderImpl(float delta) {
        
        mScreenBackground.update(delta);
        
        mBatch.begin();
        mScreenBackground.render(mBatch);
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
    
    private InputListener getStartInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(HellHopper.PLAY_SCREEN_NAME);
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
                    mGame.setScreen(HellHopper.HIGH_SCORE_SCREEN_NAME);
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
                    mGame.setScreen(HellHopper.INFO_SCREEN_NAME);
                }
            }
        };
    }
}