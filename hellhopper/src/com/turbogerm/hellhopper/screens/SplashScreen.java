package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.screens.general.LibGdxLogo;
import com.turbogerm.hellhopper.screens.general.ScreenBackground;
import com.turbogerm.hellhopper.screens.general.TurboGermLogo;
import com.turbogerm.hellhopper.screens.splash.SplashFade;
import com.turbogerm.hellhopper.screens.splash.SplashTitle;

public final class SplashScreen extends ScreenBase {
    
    private static final float PLATFORM_Y = 200.0f;
    
    private final ScreenBackground mScreenBackground;
    private final Sprite mPlatformSprite;
    private final SplashTitle mSplashTitle;
    private final TurboGermLogo mTurboGermLogo;
    private final LibGdxLogo mLibGdxLogo;
    
    private final SplashFade mSplashFade;
    
    public SplashScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mScreenBackground = new ScreenBackground(mAssetManager);
        
        Texture platformTexture = mAssetManager.get(ResourceNames.GUI_SPLASH_PLATFORM_TEXTURE);
        mPlatformSprite = new Sprite(platformTexture);
        float platformX = (HellHopper.VIEWPORT_WIDTH - mPlatformSprite.getWidth()) / 2.0f;
        mPlatformSprite.setPosition(platformX, PLATFORM_Y);
        
        mSplashTitle = new SplashTitle(mAssetManager);
        mTurboGermLogo = new TurboGermLogo(mAssetManager);
        mLibGdxLogo = new LibGdxLogo(mAssetManager);
        
        mSplashFade = new SplashFade(mAssetManager);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
        mSplashTitle.reset(PLATFORM_Y + mPlatformSprite.getHeight());
        mSplashFade.reset();
    }
    
    @Override
    public void renderImpl(float delta) {
        
        update(delta);
        
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mPlatformSprite.draw(mBatch);
        mSplashTitle.render(mBatch);
        mTurboGermLogo.render(mBatch);
        mLibGdxLogo.render(mBatch);
        mSplashFade.render(mBatch);
        mBatch.end();
    }
    
    public void update(float delta) {
        if (mSplashFade.isFinished()) {
            mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
        }
        
        mScreenBackground.update(delta);
        mSplashTitle.update(delta);
        mSplashFade.update(delta);
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