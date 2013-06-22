package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.HellHopper;

public final class SplashScreen extends ScreenBase {
    
    private final Texture mBackgroundTexture;
    private final Image mBlackImage;
    
    public SplashScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.GUI_BACKGROUND_TEXTURE);
        
        Texture splashTexture = mAssetManager.get(ResourceNames.GUI_BLACK_TEXTURE);
        mBlackImage = new Image(splashTexture);
        mBlackImage.setBounds(0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        mBlackImage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        
        mGuiStage.addActor(mBlackImage);
    }
    
    @Override
    public void show() {
        super.show();
        
        mBlackImage.clearActions();
        SequenceAction action = Actions.sequence(
                Actions.fadeOut(2.5f), Actions.delay(2.5f), Actions.fadeIn(1.5f), getCompletedAction());
        mBlackImage.addAction(action);
    }
    
    @Override
    public void renderImpl(float delta) {
        mBatch.begin();
        mBatch.draw(mBackgroundTexture, 0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        mBatch.end();
    }
    
    private Action getCompletedAction() {
        return new Action() {
            
            @Override
            public boolean act(float delta) {
                mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                return true;
            }
        };
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER || keycode == Keys.SPACE || keycode == Keys.ESCAPE) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT || button == Buttons.RIGHT) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
}