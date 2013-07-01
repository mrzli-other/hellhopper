package com.turbogerm.hellhopper.screens.splash;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.util.GameUtils;

public final class SplashFade {
    
    private static final float FADE_IN_DURATION = 1.5f;
    private static final float DELAY_DURATION = 5.0f;
    private static final float FADE_OUT_DURATION = 1.5f;
    
    private static final float DELAY_START = FADE_IN_DURATION;
    private static final float FADE_OUT_START = DELAY_START + DELAY_DURATION;
    private static final float TOTAL_DURATION = FADE_OUT_START + FADE_OUT_DURATION;
    
    private final Sprite mBlackSprite;
    
    private float mInternalTime;
    
    public SplashFade(AssetManager assetManager) {
        Texture blackTexture = assetManager.get(ResourceNames.GENERAL_BLACK_TEXTURE);
        mBlackSprite = new Sprite(blackTexture);
        mBlackSprite.setBounds(0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
    }
    
    public void reset() {
        mInternalTime = 0.0f;
    }
    
    public void update(float delta) {
        if (mInternalTime < TOTAL_DURATION) {
            mInternalTime += delta;
        } else {
            mInternalTime = TOTAL_DURATION;
        }
    }
    
    public void render(SpriteBatch batch) {
        GameUtils.setSpriteAlpha(mBlackSprite, getAlpha());
        mBlackSprite.draw(batch);
    }
    
    private float getAlpha() {
        float alpha;
        if (mInternalTime <= DELAY_START) {
            alpha = (DELAY_START - mInternalTime) / FADE_IN_DURATION;
        } else if (mInternalTime <= FADE_OUT_START) {
            alpha = 0.0f;
        } else {
            alpha = (mInternalTime - FADE_OUT_START) / FADE_OUT_DURATION;
        }
        
        return MathUtils.clamp(alpha, 0.0f, 1.0f);
    }
    
    public boolean isFinished() {
        return mInternalTime >= TOTAL_DURATION;
    }
}
