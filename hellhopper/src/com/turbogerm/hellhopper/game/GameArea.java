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
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.GameMathUtils;

public final class GameArea {
    
    private static final float CHARACTER_WIDTH = 40.0f;
    private static final float CHARACTER_HEIGHT = 60.0f;
    private static final float CHARACTER_RENDER_X_OFFSET = CHARACTER_WIDTH / 2.0f;
    
    private static final float PAD_WIDTH = 80.0f;
    private static final float PAD_HEIGHT = 20.0f;
    
    private static final float CHARACTER_JUMP_SPEED = 800.0f;
    private static final float GRAVITY = 1600.0f;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    
    private Texture mCharacterTexture;
    private Texture mPadTexture;
    
    private int mScore;
    
    private float mAreaHeight;
    private final Vector2 mCharacterPosition;
    private final Vector2 mCharacterSpeed;
    
    private boolean mIsGameOver;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        
        mCharacterTexture = mAssetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        mPadTexture = mAssetManager.get(ResourceNames.GAME_PAD_TEXTURE);
        
        mCharacterPosition = new Vector2();
        mCharacterSpeed = new Vector2();
        
        reset();
    }
    
    public void reset() {
        mScore = 0;
        mAreaHeight = 0.0f;
        mCharacterPosition.set(HellHopper.VIEWPORT_WIDTH / 2.0f, 0.0f);
        mCharacterSpeed.set(200.0f, CHARACTER_JUMP_SPEED);
    }
    
    public void update(float delta) {
        
        mCharacterSpeed.y -= GRAVITY * delta;
        
        if (mCharacterPosition.y <= 0.0f) {
            mCharacterPosition.y = 0.0f;
            mCharacterSpeed.y = CHARACTER_JUMP_SPEED;
        }
        
        mCharacterPosition.x += mCharacterSpeed.x * delta;
        mCharacterPosition.y += mCharacterSpeed.y * delta;
        
        mCharacterPosition.x = GameMathUtils.getPositiveModulus(mCharacterPosition.x, HellHopper.VIEWPORT_WIDTH);
    }
    
    public void render() {
        mBatch.draw(mCharacterTexture,
                mCharacterPosition.x - CHARACTER_RENDER_X_OFFSET,
                mCharacterPosition.y,
                CHARACTER_WIDTH, CHARACTER_HEIGHT);
    }
    
    public int getScore() {
        return mScore;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
}
