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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.init.InitData;
import com.turbogerm.hellhopper.init.SystemPlatformType;
import com.turbogerm.hellhopper.util.GameUtils;

public final class GameArea {
    
    private static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH;
    private static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT;
    
    private static final float CHARACTER_WIDTH = 40.0f;
    private static final float CHARACTER_HEIGHT = 60.0f;
    private static final float CHARACTER_CENTER_X_OFFSET = CHARACTER_WIDTH / 2.0f;
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    
    private static final float JUMP_SPEED = 850.0f;
    private static final float GRAVITY = 1400.0f;
    private static final float DEFAULT_HORIZONTAL_SPEED = 400.0f;
    private static final float ACCELEROMETER_SPEED_MULTIPLIER = 150.0f;
    
    private static final float POSITION_SCROLL_LINE_WIDTH = 5.0f;
    private static final float POSITION_SCROLL_LINE_HEIGHT = GAME_AREA_HEIGHT - 60.0f;
    private static final float POSITION_SCROLL_LINE_X = GAME_AREA_WIDTH - POSITION_SCROLL_LINE_WIDTH - 5.0f;
    private static final float POSITION_SCROLL_LINE_Y = 10.0f;
    private static final float MIN_POSITION_SCROLL_BOX_SIZE = 5.0f;
    
    private static final float END_REACHED_COUTDOWN_DURATION = 3.0f; 
    
    private static final int VISIBLE_PLATFORM_POSITIONS_INITIAL_CAPACITY = 50;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final InitData mInitData;
    
    private Texture mCharacterTexture;
    private Texture mPlatformTexture;
    private Texture mPositionScrollLineTexture;
    private Texture mPositionScrollBoxTexture;
    private Texture mEndLineTexture;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mScore;
    
    private float mVisibleAreaPosition;
    private final Vector2 mCharacterPosition;
    private final Vector2 mCharacterSpeed;
    
    private int mMinVisiblePlatformIndex;
    private final Array<Vector2> mVisiblePlatformPositions;
    
    private final Rectangle[] mTempRects;
    private final Vector2[] mTempVecs;
    
    private boolean mIsGameOver;
    private boolean mIsEndReached;
    private float mEndReachedCountdown;
    private float mEndJumpSpeed;
    
    private final BackgroundColorInterpolator mBackgroundColorInterpolator;
    private final Color mBackgroundColor;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch, InitData initData) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        mInitData = initData;
        
        mCharacterTexture = mAssetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        mPlatformTexture = mAssetManager.get(ResourceNames.GAME_PLATFORM_TEXTURE);
        mPositionScrollLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE);
        mPositionScrollBoxTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE);
        mEndLineTexture = mAssetManager.get(ResourceNames.GAME_END_LINE_TEXTURE);
        
        mCharacterPosition = new Vector2();
        mCharacterSpeed = new Vector2();
        
        mVisiblePlatformPositions = new Array<Vector2>(false, VISIBLE_PLATFORM_POSITIONS_INITIAL_CAPACITY);
        
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
        
        mBackgroundColorInterpolator = new BackgroundColorInterpolator();
        mBackgroundColor = new Color();
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_COUTDOWN_DURATION;
        mEndJumpSpeed = JUMP_SPEED;
        mRise = RiseGenerator.generate();
        mRiseHeight = mRise.getHeight();
        mBackgroundColorInterpolator.setRiseHeight(mRiseHeight);
        mScore = 0;
        mVisibleAreaPosition = 0.0f;
        mCharacterPosition.set(GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mCharacterSpeed.set(0.0f, JUMP_SPEED);
        mMinVisiblePlatformIndex = 0;
        updateVisiblePlatformPositions();
        mBackgroundColor.set(Color.BLACK);
    }
    
    public void update(float delta) {
        
        updateVisiblePlatformPositions();
        
        if (mInitData.getSystemPlatformType() == SystemPlatformType.Desktop) {
            if (Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
                mCharacterSpeed.x = -DEFAULT_HORIZONTAL_SPEED;
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
                mCharacterSpeed.x = DEFAULT_HORIZONTAL_SPEED;
            } else {
                mCharacterSpeed.x = 0.0f;
            }
        } else if (mInitData.getSystemPlatformType() == SystemPlatformType.Android) {
            mCharacterSpeed.x = -Gdx.input.getAccelerometerX() * ACCELEROMETER_SPEED_MULTIPLIER;
        }
        
        mCharacterSpeed.y -= GRAVITY * delta;
        mCharacterSpeed.y = Math.max(mCharacterSpeed.y, -JUMP_SPEED);
        
        if (mIsEndReached) {
            if (mEndReachedCountdown <= 0.0f) {
                mIsGameOver = true;
                return;
            }
            boolean isCollision;
            if (mCharacterPosition.y <= mRiseHeight) {
                isCollision = true;
                mEndJumpSpeed /= 1.4f;
            } else {
                isCollision = false;
            }
            updatePositions(delta, isCollision, mEndJumpSpeed);
            mCharacterPosition.y = Math.max(mCharacterPosition.y, mRiseHeight);
            mEndReachedCountdown -= delta;
        } else {
            boolean isCollision = isCollisionWithPlatform(delta);
            if (mCharacterPosition.y <= 0.0f) {
                mCharacterPosition.y = 0.0f;
                mCharacterSpeed.y = JUMP_SPEED;
            } else if (mCharacterPosition.y < mVisibleAreaPosition && !isCollision) {
                mIsGameOver = true;
                return;
            }
            
            updatePositions(delta, isCollision, JUMP_SPEED);
            
            if (mCharacterPosition.y > mRiseHeight) {
                mIsEndReached = true;
                mScore = Math.max(mScore, (int) mRiseHeight);
            } else {
                mScore = Math.max(mScore, (int) mCharacterPosition.y);
            }
        }
        
        mBackgroundColor.set(mBackgroundColorInterpolator.getBackgroundColor(mVisibleAreaPosition));
    }
    
    public void render() {
        for (Vector2 padPosition : mVisiblePlatformPositions) {
            mBatch.draw(mPlatformTexture,
                    padPosition.x, padPosition.y - mVisibleAreaPosition,
                    RiseGenerator.PLATFORM_WIDTH, RiseGenerator.PLATFORM_HEIGHT);
        }
        
        mBatch.draw(mEndLineTexture, 0.0f, mRiseHeight - 4.0f - mVisibleAreaPosition, GAME_AREA_WIDTH, 4.0f);
        
        mBatch.draw(mCharacterTexture,
                mCharacterPosition.x, mCharacterPosition.y - mVisibleAreaPosition,
                CHARACTER_WIDTH, CHARACTER_HEIGHT);
        
        float effectivePositionScrollLineHeight = (mRiseHeight / (mRiseHeight + GAME_AREA_HEIGHT)) *
                POSITION_SCROLL_LINE_HEIGHT;
        mBatch.draw(mPositionScrollLineTexture,
                POSITION_SCROLL_LINE_X, POSITION_SCROLL_LINE_Y,
                POSITION_SCROLL_LINE_WIDTH, effectivePositionScrollLineHeight);
        
        float positionScrollBoxY = mVisibleAreaPosition / mRiseHeight * effectivePositionScrollLineHeight;
        float positionScrollBoxHeight = Math.max(
                GAME_AREA_HEIGHT / mRiseHeight * effectivePositionScrollLineHeight, MIN_POSITION_SCROLL_BOX_SIZE);
        mBatch.draw(mPositionScrollBoxTexture,
                POSITION_SCROLL_LINE_X, positionScrollBoxY,
                POSITION_SCROLL_LINE_WIDTH, positionScrollBoxHeight);
    }
    
    private void updateVisiblePlatformPositions() {
        mVisiblePlatformPositions.clear();
        
        Array<Vector2> padPositions = mRise.getPlatformPositions();
        for (int i = mMinVisiblePlatformIndex; i < padPositions.size; i++) {
            Vector2 padPosition = padPositions.get(i);
            if (padPosition.y + RiseGenerator.PLATFORM_HEIGHT < mVisibleAreaPosition) {
                mMinVisiblePlatformIndex = i + 1;
            } else if (padPosition.y <= mVisibleAreaPosition + GAME_AREA_HEIGHT) {
                mVisiblePlatformPositions.add(padPosition);
            } else {
                break;
            }
        }
    }
    
    private boolean isCollisionWithPlatform(float delta) {
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
        
        for (Vector2 padPosition : mVisiblePlatformPositions) {
            mTempRects[1].set(padPosition.x - CHARACTER_WIDTH, padPosition.y,
                    RiseGenerator.PLATFORM_WIDTH + CHARACTER_WIDTH, RiseGenerator.PLATFORM_HEIGHT);
            if (Intersector.intersectRectangles(mTempRects[0], mTempRects[1])) {
                mTempVecs[2].set(padPosition.x - CHARACTER_WIDTH,
                        padPosition.y + RiseGenerator.PLATFORM_HEIGHT);
                mTempVecs[3].set(padPosition.x + RiseGenerator.PLATFORM_WIDTH,
                        padPosition.y + RiseGenerator.PLATFORM_HEIGHT);
                if (Intersector.intersectSegments(mTempVecs[0], mTempVecs[1], mTempVecs[2], mTempVecs[3], null)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private void updatePositions(float delta, boolean isCollision, float jumpSpeed) {
        mCharacterPosition.x += mCharacterSpeed.x * delta;
        mCharacterPosition.y += mCharacterSpeed.y * delta;
        if (isCollision) {
            mCharacterSpeed.y = jumpSpeed;
        }
        
        mCharacterPosition.x = GameUtils.getPositiveModulus(
                mCharacterPosition.x + CHARACTER_CENTER_X_OFFSET, GAME_AREA_WIDTH) -
                CHARACTER_CENTER_X_OFFSET;
        
        mVisibleAreaPosition = Math.max(mVisibleAreaPosition, mCharacterPosition.y -
                GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION);
    }
    
    public int getScore() {
        return mScore;
    }
    
    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
}
