package com.turbogerm.helljump.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorPositionPair;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.background.GameBackground;

public final class ScreenBackground {
    
    private static final float SPECTRUM_TRAVERSAL_HALF_PERIOD = 30.0f;
    private static final float SPECTRUM_TRAVERSAL_PERIOD = SPECTRUM_TRAVERSAL_HALF_PERIOD * 2.0f;
    
    private final GameBackground mGameBackground;
    
    private float mInternalTime;
    
    public ScreenBackground(CameraData cameraData, AssetManager assetManager) {
        mGameBackground = new GameBackground(getBackgroundColorSpectrum(), false, cameraData, assetManager);
    }
    
    public void reset() {
        mInternalTime = 0;
    }
    
    public void update(float delta) {
        mInternalTime += delta;
        mInternalTime %= SPECTRUM_TRAVERSAL_PERIOD;
        float spectrumFraction;
        if (mInternalTime <= SPECTRUM_TRAVERSAL_HALF_PERIOD) {
            spectrumFraction = mInternalTime / SPECTRUM_TRAVERSAL_HALF_PERIOD;
        } else {
            spectrumFraction = (SPECTRUM_TRAVERSAL_PERIOD - mInternalTime) / SPECTRUM_TRAVERSAL_HALF_PERIOD;
        }
        mGameBackground.setSpectrumFraction(spectrumFraction);
    }
    
    public void render(SpriteBatch batch) {
        mGameBackground.render(batch, 0.0f);
    }
    
    private static Array<ColorPositionPair> getBackgroundColorSpectrum() {
        Array<ColorPositionPair> colorPositionPairs = new Array<ColorPositionPair>(true, 4);
        
        colorPositionPairs.add(new ColorPositionPair(new Color(0.3f, 0.0f, 0.0f, 1.0f), 0.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.6f, 0.0f, 0.0f, 1.0f), 5.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.91f, 0.6f, 0.09f, 1.0f), 10.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.1f, 1.0f, 0.00f, 1.0f), 15.0f));
        
        return colorPositionPairs;
    }
}
