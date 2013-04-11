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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.init.InitData;
import com.turbogerm.hellhopper.init.PlatformType;
import com.turbogerm.hellhopper.util.GameMathUtils;
import com.turbogerm.hellhopper.util.Logger;

public final class GameArea {
    
    private static final float CHARACTER_WIDTH = 40.0f;
    private static final float CHARACTER_HEIGHT = 60.0f;
    private static final float CHARACTER_CENTER_X_OFFSET = CHARACTER_WIDTH / 2.0f;
    
    private static final float JUMP_SPEED = 840.0f;
    private static final float GRAVITY = 1400.0f;
    private static final float DEFAULT_HORIZONTAL_SPEED = 800.0f;
    private static final float ACCELEROMETER_SPEED_MULTIPLIER = 250.0f;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final InitData mInitData;
    
    private Texture mCharacterTexture;
    private Texture mPadTexture;
    
    private GameAreaPads mGameAreaPads;
    
    private int mScore;
    
    private float mAreaPosition;
    private final Vector2 mCharacterPosition;
    private final Vector2 mCharacterSpeed;
    
    private boolean mIsGameOver;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch, InitData initData) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        mInitData = initData;
        
        mCharacterTexture = mAssetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        mPadTexture = mAssetManager.get(ResourceNames.GAME_PAD_TEXTURE);
        
        mCharacterPosition = new Vector2();
        mCharacterSpeed = new Vector2();
        
        reset();
    }
    
    public void reset() {
        mGameAreaPads = GameAreaGenerator.generateGameAreaPads(); 
        mScore = 0;
        mAreaPosition = 0.0f;
        mCharacterPosition.set(HellHopper.VIEWPORT_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mCharacterSpeed.set(0.0f, JUMP_SPEED);
    }
    
    public void update(float delta) {
        
        if (mInitData.getPlatformType() == PlatformType.Desktop) {
            if (Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
                mCharacterSpeed.x = -DEFAULT_HORIZONTAL_SPEED;
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
                mCharacterSpeed.x = DEFAULT_HORIZONTAL_SPEED;
            } else {
                mCharacterSpeed.x = 0.0f;
            }
        } else if (mInitData.getPlatformType() == PlatformType.Android) {
            mCharacterSpeed.x = -Gdx.input.getAccelerometerX() * ACCELEROMETER_SPEED_MULTIPLIER;
        }
        
        mCharacterSpeed.y -= GRAVITY * delta;
        
        if (mCharacterPosition.y <= 0.0f) {
            mCharacterPosition.y = 0.0f;
            mCharacterSpeed.y = JUMP_SPEED;
        }
        
        mCharacterSpeed.y = Math.max(mCharacterSpeed.y, -JUMP_SPEED);
        
        mCharacterPosition.x += mCharacterSpeed.x * delta;
        mCharacterPosition.y += mCharacterSpeed.y * delta;
        
        mCharacterPosition.x = GameMathUtils.getPositiveModulus(
                mCharacterPosition.x + CHARACTER_CENTER_X_OFFSET, HellHopper.VIEWPORT_WIDTH) -
                CHARACTER_CENTER_X_OFFSET;
        
        mScore = Math.max(mScore, (int)mCharacterPosition.y);
    }
    
    public void render() {
        mBatch.draw(mCharacterTexture,
                mCharacterPosition.x, mCharacterPosition.y - mAreaPosition,
                CHARACTER_WIDTH, CHARACTER_HEIGHT);
        
        Array<Vector2> padPositions = mGameAreaPads.getPadPositions();
        for (Vector2 padPosition : padPositions) {
            mBatch.draw(mPadTexture,
                    padPosition.x, padPosition.y - mAreaPosition,
                    GameAreaGenerator.PAD_WIDTH, GameAreaGenerator.PAD_HEIGHT);
        }
    }
    
    public int getScore() {
        return mScore;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
}
