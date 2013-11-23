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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public final class EndBackgroundScene {
    
    private static final float GROUND_OFFSET_Y = -220.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_X = 60.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_Y = 250.0f * GameAreaUtils.PIXEL_TO_METER;
    
    private final int MIN_SHEEP = 2;
    private final int MAX_SHEEP = 4;
    
    private final float SUN_ROTATION_SPEED = 5.0f;
    
    private final Rectangle mCameraRect;
    
    private float mRiseHeight;
    
    private final Sprite mSkySprite;
    private final Sprite mSunSprite;
    private final Sprite mCloudsSprite;
    private final Sprite mMountainsSprite;
    private final Sprite mGroundSprite;
    
    private final Sheep[] mSheep;
    private int mNumSheep;
    
    public EndBackgroundScene(CameraData cameraData, AssetManager assetManager) {
        
        mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        
        TextureAtlas atlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        
        mSkySprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SKY_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSkySprite, GameAreaUtils.PIXEL_TO_METER);
        
        mSunSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SUN_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSunSprite, GameAreaUtils.PIXEL_TO_METER);
        GameUtils.setSpriteOriginCenter(mSunSprite);
        
        mCloudsSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_CLOUDS_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mCloudsSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mMountainsSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_MOUNTAINS_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mMountainsSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mGroundSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_GROUND_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mGroundSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mSheep = new Sheep[MAX_SHEEP];
        for (int i = 0; i < MAX_SHEEP; i++) {
            mSheep[i] = new Sheep(cameraData, assetManager);
        }
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        for (int i = 0; i < MAX_SHEEP; i++) {
            mSheep[i].reset(riseHeight);
        }
        
        mNumSheep = MathUtils.random(MIN_SHEEP, MAX_SHEEP);
    }
    
    public void update(float delta) {
        mSunSprite.rotate(SUN_ROTATION_SPEED * delta);
        
        for (int i = 0; i < mNumSheep; i++) {
            mSheep[i].update(delta);
        }
    }
    
    public void render(SpriteBatch batch) {
        
        setWideSpritePosition(mSkySprite, 0.0f);
        mSunSprite.setPosition(SUN_OFFSET_X, mRiseHeight + SUN_OFFSET_Y);
        setWideSpritePosition(mCloudsSprite, 0.0f);
        setWideSpritePosition(mMountainsSprite, 0.0f);
        setWideSpritePosition(mGroundSprite, GROUND_OFFSET_Y);
        
        mSkySprite.draw(batch);
        mSunSprite.draw(batch);
        mCloudsSprite.draw(batch);
        mMountainsSprite.draw(batch);
        
        for (int i = 0; i < mNumSheep; i++) {
            mSheep[i].render(batch);
        }
        
        mGroundSprite.draw(batch);
    }
    
    private void setWideSpritePosition(Sprite sprite, float offsetY) {
        float spriteX = mCameraRect.x + (mCameraRect.width - sprite.getWidth()) / 2.0f;
        float spriteY = mRiseHeight + offsetY;
        
        sprite.setPosition(spriteX, spriteY);
    }
}
