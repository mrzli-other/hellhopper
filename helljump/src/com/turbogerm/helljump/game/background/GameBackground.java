/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
