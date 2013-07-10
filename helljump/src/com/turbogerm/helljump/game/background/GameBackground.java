package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
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
    
    public GameBackground(Array<ColorPositionPair> backgroundColorSpectrum, boolean isForGameArea,
            CameraData cameraData, AssetManager assetManager) {
        
        mSpectrumColorInterpolator = new SpectrumColorInterpolator(backgroundColorSpectrum);
        
        Rectangle viewport = cameraData.getViewport();
        
        TextureAtlas graphicsGuiAtlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mBackgroundColorSprite = graphicsGuiAtlas.createSprite(ResourceNames.GUI_GENERAL_WHITE_IMAGE_NAME);
        mBackgroundColorSprite.setBounds(viewport.x, viewport.y, viewport.width, viewport.height);
        
        TextureAtlas backgroundAtlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        mBackgroundSprite = backgroundAtlas.createSprite(ResourceNames.BACKGROUND_IMAGE_NAME);
        
        if (isForGameArea) {
            GameUtils.multiplySpriteSize(mBackgroundColorSprite, GameAreaUtils.PIXEL_TO_METER);
            GameUtils.multiplySpriteSize(mBackgroundSprite, GameAreaUtils.PIXEL_TO_METER);
            mBackgroundSprite.setPosition(
                    viewport.x * GameAreaUtils.PIXEL_TO_METER,
                    viewport.y * GameAreaUtils.PIXEL_TO_METER);
        } else {
            mBackgroundSprite.setPosition(viewport.x, viewport.y);
        }
    }
    
    public void setSpectrumFraction(float spectrumFraction) {
        mBackgroundColorSprite.setColor(mSpectrumColorInterpolator.getBackgroundColor(spectrumFraction));
    }
    
    public void render(SpriteBatch batch, float offsetY) {
        mBackgroundColorSprite.setPosition(mBackgroundColorSprite.getX(), offsetY);
        mBackgroundSprite.setPosition(mBackgroundSprite.getX(), offsetY);
        
        render(batch);
    }
    
    public void render(SpriteBatch batch) {
        mBackgroundColorSprite.draw(batch);
        mBackgroundSprite.draw(batch);
    }
}
