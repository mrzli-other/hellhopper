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
package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.hellhopper.ResourceNames;

public final class RisePositionScroll {
    
    private static final float POSITION_SCROLL_LINE_WIDTH = 5.0f;
    private static final float POSITION_SCROLL_LINE_HEIGHT = GameArea.GAME_AREA_HEIGHT - 60.0f;
    private static final float POSITION_SCROLL_LINE_X = GameArea.GAME_AREA_WIDTH - POSITION_SCROLL_LINE_WIDTH - 5.0f;
    private static final float POSITION_SCROLL_LINE_Y = 10.0f;
    private static final float MIN_POSITION_SCROLL_BOX_SIZE = 5.0f;
    private static final float POSITION_SCROLL_END_LINE_HEIGHT = 4.0f;
    
    private final Texture mPositionScrollLineTexture;
    private final Texture mPositionScrollLineAboveTexture;
    private final Texture mPositionScrollBoxTexture;
    private final Texture mPositionScrollEndLineTexture;
    
    private float mRiseHeight;
    private float mEffectivePositionScrollLineHeight;
    
    public RisePositionScroll(AssetManager assetManager) {
        mPositionScrollLineTexture = assetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE);
        mPositionScrollLineAboveTexture = assetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE);
        mPositionScrollBoxTexture = assetManager.get(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE);
        mPositionScrollEndLineTexture = assetManager.get(ResourceNames.GAME_POSITION_SCROLL_END_LINE_TEXTURE);
    }
    
    public void setRiseHeight(float riseHeight) {
        mRiseHeight = riseHeight;
        mEffectivePositionScrollLineHeight = (mRiseHeight / (mRiseHeight + GameArea.GAME_AREA_HEIGHT)) *
                POSITION_SCROLL_LINE_HEIGHT;
    }
    
    public void render(SpriteBatch batch, float visibleAreaPosition) {
        batch.draw(mPositionScrollLineTexture,
                POSITION_SCROLL_LINE_X, POSITION_SCROLL_LINE_Y,
                POSITION_SCROLL_LINE_WIDTH, mEffectivePositionScrollLineHeight);
        
        batch.draw(mPositionScrollLineAboveTexture,
                POSITION_SCROLL_LINE_X, POSITION_SCROLL_LINE_Y + mEffectivePositionScrollLineHeight,
                POSITION_SCROLL_LINE_WIDTH, POSITION_SCROLL_LINE_HEIGHT - mEffectivePositionScrollLineHeight);
        
        float positionScrollBoxY = POSITION_SCROLL_LINE_Y +
                visibleAreaPosition / mRiseHeight * mEffectivePositionScrollLineHeight;
        float positionScrollBoxHeight = Math.max(
                GameArea.GAME_AREA_HEIGHT / mRiseHeight * mEffectivePositionScrollLineHeight,
                MIN_POSITION_SCROLL_BOX_SIZE);
        batch.draw(mPositionScrollBoxTexture,
                POSITION_SCROLL_LINE_X, positionScrollBoxY,
                POSITION_SCROLL_LINE_WIDTH, positionScrollBoxHeight);
        
        float positionScrollEndLineY = POSITION_SCROLL_LINE_Y + mEffectivePositionScrollLineHeight -
                POSITION_SCROLL_END_LINE_HEIGHT;
        batch.draw(mPositionScrollEndLineTexture,
                POSITION_SCROLL_LINE_X, positionScrollEndLineY,
                POSITION_SCROLL_LINE_WIDTH, POSITION_SCROLL_END_LINE_HEIGHT);
    }
}
