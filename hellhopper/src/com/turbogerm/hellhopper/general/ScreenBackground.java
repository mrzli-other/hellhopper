package com.turbogerm.hellhopper.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.util.ColorPositionPair;
import com.turbogerm.hellhopper.util.SpectrumColorInterpolator;

public final class ScreenBackground {
    
    private static final float SPECTRUM_TRAVERSAL_HALF_PERIOD = 30.0f;
    private static final float SPECTRUM_TRAVERSAL_PERIOD = SPECTRUM_TRAVERSAL_HALF_PERIOD * 2.0f;
    
    private final SpectrumColorInterpolator mSpectrumColorInterpolator;
    private final Color mBackgroundColor;
    
    private final Texture mBackgroundTexture;
    private final Vector2 mBackgroundTextureSize;
    
    private float mInternalTime;
    
    public ScreenBackground(AssetManager assetManager) {
        mSpectrumColorInterpolator = new SpectrumColorInterpolator(getBackgroundColorSpectrum());
        mBackgroundColor = new Color();
        
        mBackgroundTexture = assetManager.get(ResourceNames.BACKGROUND_TEXTURE);
        mBackgroundTextureSize = new Vector2(
                mBackgroundTexture.getWidth(),
                mBackgroundTexture.getHeight());
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
        mBackgroundColor.set(mSpectrumColorInterpolator.getBackgroundColor(spectrumFraction));
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(mBackgroundTexture, 0.0f, 0.0f, mBackgroundTextureSize.x, mBackgroundTextureSize.y);
    }
    
    private static Array<ColorPositionPair> getBackgroundColorSpectrum() {
        Array<ColorPositionPair> colorPositionPairs = new Array<ColorPositionPair>(true, 3);
        
        colorPositionPairs.add(new ColorPositionPair(new Color(0.0f, 0.0f, 0.0f, 1.0f), 0.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.6f, 0.0f, 0.0f, 1.0f), 5.0f));
        colorPositionPairs.add(new ColorPositionPair(new Color(0.91f, 0.6f, 0.09f, 1.0f), 10.0f));
        
        return colorPositionPairs;
    }
    
    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
}
