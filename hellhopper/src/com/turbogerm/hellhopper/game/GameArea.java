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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.init.InitData;
import com.turbogerm.hellhopper.init.PlatformType;
import com.turbogerm.hellhopper.util.GameMathUtils;

public final class GameArea {
    
    private static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH;
    private static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT;
    
    private static final float CHARACTER_WIDTH = 40.0f;
    private static final float CHARACTER_HEIGHT = 60.0f;
    private static final float CHARACTER_CENTER_X_OFFSET = CHARACTER_WIDTH / 2.0f;
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    
    private static final float JUMP_SPEED = 840.0f;
    private static final float GRAVITY = 1400.0f;
    private static final float DEFAULT_HORIZONTAL_SPEED = 400.0f;
    private static final float ACCELEROMETER_SPEED_MULTIPLIER = 150.0f;
    
    private static final float POSITION_SCROLL_LINE_WIDTH = 5.0f;
    private static final float POSITION_SCROLL_LINE_HEIGHT = GAME_AREA_HEIGHT - 60.0f;
    private static final float POSITION_SCROLL_LINE_X = GAME_AREA_WIDTH - POSITION_SCROLL_LINE_WIDTH - 5.0f;
    private static final float POSITION_SCROLL_LINE_Y = 10.0f;
    private static final float MIN_POSITION_SCROLL_BOX_SIZE = 10.0f;
    
    private static final int VISIBLE_PAD_POSITIONS_INITIAL_CAPACITY = 50;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final InitData mInitData;
    
    private Texture mCharacterTexture;
    private Texture mPadTexture;
    private Texture mPositionScrollLineTexture;
    private Texture mPositionScrollBoxTexture;
    
    private GameAreaPath mGameAreaPath;
    
    private int mScore;
    
    private float mAreaPosition;
    private final Vector2 mCharacterPosition;
    private final Vector2 mCharacterSpeed;
    
    private int mMinPadAreaStartIndex;
    private final Array<Vector2> mVisiblePadPositions;
    
    private final Rectangle[] mTempRects;
    private final Vector2[] mTempVecs;
    
    private boolean mIsGameOver;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch, InitData initData) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        mInitData = initData;
        
        mCharacterTexture = mAssetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        mPadTexture = mAssetManager.get(ResourceNames.GAME_PAD_TEXTURE);
        mPositionScrollLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE);
        mPositionScrollBoxTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE);
        
        mCharacterPosition = new Vector2();
        mCharacterSpeed = new Vector2();
        
        mVisiblePadPositions = new Array<Vector2>(false, VISIBLE_PAD_POSITIONS_INITIAL_CAPACITY);
        
        final int numTempRects = 2;
        mTempRects = new Rectangle[numTempRects];
        for (int i = 0; i < numTempRects; i++) {
            mTempRects[i] = new Rectangle();
        }
        
        final int numTempVecs = 4;
        mTempVecs = new Vector2[numTempVecs];
        for (int i = 0; i < numTempVecs; i++) {
            mTempVecs[i] = new Vector2();
        }
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        mGameAreaPath = GameAreaGenerator.generateGameAreaPads();
        mScore = 0;
        mAreaPosition = 0.0f;
        mCharacterPosition.set(GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mCharacterSpeed.set(0.0f, JUMP_SPEED);
        mMinPadAreaStartIndex = 0;
        updateVisiblePadPositions();
    }
    
    public void update(float delta) {
        
        updateVisiblePadPositions();
        
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
        mCharacterSpeed.y = Math.max(mCharacterSpeed.y, -JUMP_SPEED);
        
        boolean isCollision = isCollisionWithPad(delta);
        if (mCharacterPosition.y <= 0.0f) {
            mCharacterPosition.y = 0.0f;
            mCharacterSpeed.y = JUMP_SPEED;
        } else if (mCharacterPosition.y < mAreaPosition && !isCollision) {
            mIsGameOver = true;
            return;
        }
        
        mCharacterPosition.x += mCharacterSpeed.x * delta;
        mCharacterPosition.y += mCharacterSpeed.y * delta;
        if (isCollision) {
            mCharacterSpeed.y = JUMP_SPEED;
        }
        
        mCharacterPosition.x = GameMathUtils.getPositiveModulus(
                mCharacterPosition.x + CHARACTER_CENTER_X_OFFSET, GAME_AREA_WIDTH) -
                CHARACTER_CENTER_X_OFFSET;
        
        mAreaPosition = Math.max(mAreaPosition, mCharacterPosition.y -
                GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION);
        mScore = Math.max(mScore, (int) mCharacterPosition.y);
    }
    
    public void render() {
        mBatch.draw(mCharacterTexture,
                mCharacterPosition.x, mCharacterPosition.y - mAreaPosition,
                CHARACTER_WIDTH, CHARACTER_HEIGHT);
        
        for (Vector2 padPosition : mVisiblePadPositions) {
            mBatch.draw(mPadTexture,
                    padPosition.x, padPosition.y - mAreaPosition,
                    GameAreaGenerator.PAD_WIDTH, GameAreaGenerator.PAD_HEIGHT);
        }
        
        float totalGamePathHeight = mGameAreaPath.getTotalHeight();
        float effectivePositionScrollLineHeight = (totalGamePathHeight / (totalGamePathHeight + GAME_AREA_HEIGHT)) *
                POSITION_SCROLL_LINE_HEIGHT;
        mBatch.draw(mPositionScrollLineTexture,
                POSITION_SCROLL_LINE_X, POSITION_SCROLL_LINE_Y,
                POSITION_SCROLL_LINE_WIDTH, effectivePositionScrollLineHeight);
        
        float positionScrollBoxY = mAreaPosition / totalGamePathHeight * effectivePositionScrollLineHeight;
        float positionScrollBoxHeight = GAME_AREA_HEIGHT / totalGamePathHeight * effectivePositionScrollLineHeight;
        mBatch.draw(mPositionScrollBoxTexture,
                POSITION_SCROLL_LINE_X, positionScrollBoxY,
                POSITION_SCROLL_LINE_WIDTH, positionScrollBoxHeight);
    }
    
    private void updateVisiblePadPositions() {
        mVisiblePadPositions.clear();
        
        Array<Vector2> padPositions = mGameAreaPath.getPadPositions();
        for (int i = mMinPadAreaStartIndex; i < padPositions.size; i++) {
            Vector2 padPosition = padPositions.get(i);
            if (padPosition.y + GameAreaGenerator.PAD_HEIGHT < mAreaPosition) {
                mMinPadAreaStartIndex = i + 1;
            } else if (padPosition.y <= mAreaPosition + GAME_AREA_HEIGHT) {
                mVisiblePadPositions.add(padPosition);
            } else {
                break;
            }
        }
    }
    
    private boolean isCollisionWithPad(float delta) {
        if (mCharacterSpeed.y >= 0) {
            return false;
        }
        
        float newX = mCharacterPosition.x + mCharacterSpeed.x * delta;
        float newY = mCharacterPosition.y + mCharacterSpeed.y * delta;
        
        float minX = Math.min(mCharacterPosition.x, newX);
        float minY = Math.min(mCharacterPosition.y, newY);
        float maxX = Math.max(mCharacterPosition.x, newX);
        float maxY = Math.max(mCharacterPosition.y, newY);
        mTempRects[0].set(minX, minY, maxX - minX, maxY - minY);
        mTempVecs[0].set(mCharacterPosition);
        mTempVecs[1].set(newX, newY);
        
        for (Vector2 padPosition : mVisiblePadPositions) {
            mTempRects[1].set(padPosition.x - CHARACTER_WIDTH, padPosition.y,
                    GameAreaGenerator.PAD_WIDTH + CHARACTER_WIDTH, GameAreaGenerator.PAD_HEIGHT);
            if (Intersector.intersectRectangles(mTempRects[0], mTempRects[1])) {
                mTempVecs[2].set(padPosition.x - CHARACTER_WIDTH,
                        padPosition.y + GameAreaGenerator.PAD_HEIGHT);
                mTempVecs[3].set(padPosition.x + GameAreaGenerator.PAD_WIDTH,
                        padPosition.y + GameAreaGenerator.PAD_HEIGHT);
                if (Intersector.intersectSegments(mTempVecs[0], mTempVecs[1], mTempVecs[2], mTempVecs[3], null)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public int getScore() {
        return mScore;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
    
//    private static VerticalPosition {
//        
//    }
}
