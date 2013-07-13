package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorPositionPair;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.germlibrary.util.SpectrumColorInterpolator;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public final class GameBackground {
    
    private final SpectrumColorInterpolator mSpectrumColorInterpolator;
    
    private final Sprite mBackgroundColorSprite;
    private final Sprite mBackgroundSprite;
    
    private final Rectangle mCameraRect;
    
    private final Color mBackgroundColor;
    
    public GameBackground(Array<ColorPositionPair> backgroundColorSpectrum, boolean isForGameArea,
            CameraData cameraData, AssetManager assetManager) {
        
        mSpectrumColorInterpolator = new SpectrumColorInterpolator(backgroundColorSpectrum);
        
        TextureAtlas graphicsGuiAtlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mBackgroundColorSprite = graphicsGuiAtlas.createSprite(ResourceNames.GUI_GENERAL_WHITE_IMAGE_NAME);
        
        TextureAtlas backgroundAtlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        mBackgroundSprite = backgroundAtlas.createSprite(ResourceNames.BACKGROUND_IMAGE_NAME);
        
        if (isForGameArea) {
            GameUtils.multiplySpriteSize(mBackgroundSprite, GameAreaUtils.PIXEL_TO_METER);
            mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        } else {
            mCameraRect = cameraData.getGuiCameraRect();
        }
        
        mBackgroundColor = new Color();
    }
    
    public void setSpectrumFraction(float spectrumFraction) {
        mBackgroundColor.set(mSpectrumColorInterpolator.getBackgroundColor(spectrumFraction));
        mBackgroundColorSprite.setColor(mBackgroundColor);
    }
    
    public void render(SpriteBatch batch, float offsetY) {
        mBackgroundColorSprite.setBounds(
                mCameraRect.x, mCameraRect.y + offsetY, mCameraRect.width, mCameraRect.height);
        
        float backgroundSpriteX = mCameraRect.x + (mCameraRect.width - mBackgroundSprite.getWidth()) / 2.0f;
        float backgroundSpriteY = mCameraRect.y +
                (mCameraRect.height - mBackgroundSprite.getHeight()) / 2.0f + offsetY;
        mBackgroundSprite.setPosition(backgroundSpriteX, backgroundSpriteY);
        
        mBackgroundColorSprite.draw(batch);
        mBackgroundSprite.draw(batch);
    }
    
    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
}
