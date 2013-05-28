/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.game.GameArea;

final class BackgroundLayer {
    
    static final float SPRITE_SCALE_MULTIPLIER = 2.0f;
    
    private final float mScale;
    
    private final float mLayerDisplayX;
    private final float mLayerDisplayWidth;
    private final float mLayerDisplayHeight;
    
    private float mLayerDisplayY;
    
    private float mRiseHeight;
    
    private final Array<BackgroundObjectBase> mBackgroundObjects;
    
    public BackgroundLayer(AssetManager assetManager, float distance, int numClouds, int numRocks) {
        mScale = 1.0f / distance / SPRITE_SCALE_MULTIPLIER;
        
        mLayerDisplayWidth = GameArea.GAME_AREA_WIDTH / mScale;
        mLayerDisplayHeight = GameArea.GAME_AREA_HEIGHT / mScale;
        mLayerDisplayX = (GameArea.GAME_AREA_WIDTH - mLayerDisplayWidth) / 2.0f;
        mLayerDisplayY = 0.0f;
        
        mBackgroundObjects = new Array<BackgroundObjectBase>(true, numClouds + numRocks);
        for (int i = 0; i < numClouds; i++) {
            mBackgroundObjects.add(new CloudBackgroundObject(assetManager));
        }
        for (int i = 0; i < numRocks; i++) {
            mBackgroundObjects.add(new RockBackgroundObject(assetManager));
        }
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
    }
    
    public void update(float visibleAreaPosition, float delta) {
        mLayerDisplayY = visibleAreaPosition;// * mScale;
    }
    
    public void render(SpriteBatch batch) {
        batch.getProjectionMatrix().setToOrtho2D(
                mLayerDisplayX, mLayerDisplayY, mLayerDisplayWidth, mLayerDisplayHeight);
        batch.begin();
        
        for (BackgroundObjectBase backgroundObject : mBackgroundObjects) {
            backgroundObject.render(batch);
        }
        
        batch.end();
    }
}
