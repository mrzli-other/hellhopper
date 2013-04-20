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

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.util.GameUtils;
import com.turbogerm.hellhopper.util.Pools;

public final class GameArea {
    
    public static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH;
    public static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT;
    
    public static final float CHARACTER_WIDTH = 40.0f;
    public static final float CHARACTER_HEIGHT = 60.0f;
    private static final float CHARACTER_CENTER_X_OFFSET = CHARACTER_WIDTH / 2.0f;
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    
    private static final float MAX_DELTA = 0.1f;
    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_STEP = 1.0f / UPDATE_RATE;
    
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
    
    private static final int VISIBLE_PLATFORMS_INITIAL_CAPACITY = 50;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    
    private final Texture mCharacterTexture;
    private final Texture mPositionScrollLineTexture;
    private final Texture mPositionScrollBoxTexture;
    private final Texture mPositionScrollEndLineTexture;
    private final Texture mEndLineTexture;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mScore;
    
    private float mVisibleAreaPosition;
    private final Vector2 mCharPosition;
    private final Vector2 mCharSpeed;
    
    private float mDeltaAccumulator;
    
    private int mMinVisiblePlatformIndex;
    private final Array<PlatformBase> mVisiblePlatforms;
    
    private boolean mIsGameOver;
    private boolean mIsEndReached;
    private float mEndReachedCountdown;
    
    private final BackgroundColorInterpolator mBackgroundColorInterpolator;
    private final Color mBackgroundColor;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        
        mCharacterTexture = mAssetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        mPositionScrollLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE);
        mPositionScrollBoxTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE);
        mPositionScrollEndLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_END_LINE_TEXTURE);
        mEndLineTexture = mAssetManager.get(ResourceNames.GAME_END_LINE_TEXTURE);
        
        mCharPosition = new Vector2();
        mCharSpeed = new Vector2();
        
        mVisiblePlatforms = new Array<PlatformBase>(false, VISIBLE_PLATFORMS_INITIAL_CAPACITY);
        
        mBackgroundColorInterpolator = new BackgroundColorInterpolator();
        mBackgroundColor = new Color();
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_COUTDOWN_DURATION;
        
        mRise = RiseGenerator.generate(mAssetManager);
        mRiseHeight = mRise.getHeight();
        
        mScore = 0;
        
        mVisibleAreaPosition = 0.0f;
        mCharPosition.set(GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mCharSpeed.set(0.0f, JUMP_SPEED);
        
        mDeltaAccumulator = 0.0f;
        
        mMinVisiblePlatformIndex = 0;
        
        mBackgroundColorInterpolator.setRiseHeight(mRiseHeight);
        mBackgroundColor.set(Color.BLACK);
    }
    
    public void update(float delta) {
        
        if (delta > MAX_DELTA) {
            delta = MAX_DELTA;
        }
        
        float horizontalSpeed = getHorizontalSpeed();
        
        if (mIsEndReached) {
            if (mEndReachedCountdown <= 0.0f) {
                mIsGameOver = true;
                return;
            } else if (mCharPosition.y <= mRiseHeight) {
                mCharPosition.y = mRiseHeight + 0.001f;
                mCharSpeed.y = Math.abs(mCharSpeed.y / 1.5f);
                mCharSpeed.y = Math.max(mCharSpeed.y - 30.0f, 0.0f);
            }
            
            mEndReachedCountdown -= delta;
        } else {
            if (mCharPosition.y <= 0.0f) {
                mCharPosition.y = 0.0f;
                mCharSpeed.y = JUMP_SPEED;
            } else if (mCharPosition.y < mVisibleAreaPosition) {
                mIsGameOver = true;
                return;
            }
        }
        
        mDeltaAccumulator += delta;
        while (mDeltaAccumulator >= UPDATE_STEP) {
            updateStep(JUMP_SPEED, horizontalSpeed, UPDATE_STEP);
            mDeltaAccumulator -= UPDATE_STEP;
        }
        
        updateStep(JUMP_SPEED, horizontalSpeed, mDeltaAccumulator);
        mDeltaAccumulator = 0.0f;
        
        if (mCharPosition.y > mRiseHeight) {
            mIsEndReached = true;
            mScore = Math.max(mScore, (int) mRiseHeight);
        } else {
            mScore = Math.max(mScore, (int) mCharPosition.y);
        }
        
        mBackgroundColor.set(mBackgroundColorInterpolator.getBackgroundColor(mVisibleAreaPosition));
    }
    
    public void render() {
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.render(mBatch, mVisibleAreaPosition);
        }
        
        final float endLineHeight = 4.0f;
        mBatch.draw(mEndLineTexture, 0.0f,
                mRiseHeight - endLineHeight - mVisibleAreaPosition, GAME_AREA_WIDTH, endLineHeight);
        
        mBatch.draw(mCharacterTexture,
                mCharPosition.x, mCharPosition.y - mVisibleAreaPosition,
                CHARACTER_WIDTH, CHARACTER_HEIGHT);
        
        float effectivePositionScrollLineHeight = (mRiseHeight / (mRiseHeight + GAME_AREA_HEIGHT)) *
                POSITION_SCROLL_LINE_HEIGHT;
        mBatch.draw(mPositionScrollLineTexture,
                POSITION_SCROLL_LINE_X, POSITION_SCROLL_LINE_Y,
                POSITION_SCROLL_LINE_WIDTH, effectivePositionScrollLineHeight);
        
        float positionScrollBoxY = POSITION_SCROLL_LINE_Y +
                mVisibleAreaPosition / mRiseHeight * effectivePositionScrollLineHeight;
        float positionScrollBoxHeight = Math.max(
                GAME_AREA_HEIGHT / mRiseHeight * effectivePositionScrollLineHeight, MIN_POSITION_SCROLL_BOX_SIZE);
        mBatch.draw(mPositionScrollBoxTexture,
                POSITION_SCROLL_LINE_X, positionScrollBoxY,
                POSITION_SCROLL_LINE_WIDTH, positionScrollBoxHeight);
        
        float positionScrollEndLineHeight = 4.0f;
        float positionScrollEndLineY = POSITION_SCROLL_LINE_Y + effectivePositionScrollLineHeight -
                positionScrollEndLineHeight;
        mBatch.draw(mPositionScrollEndLineTexture,
                POSITION_SCROLL_LINE_X, positionScrollEndLineY,
                POSITION_SCROLL_LINE_WIDTH, positionScrollEndLineHeight);
    }
    
    private void updateStep(float jumpSpeed, float horizontalSpeed, float delta) {
        
        mMinVisiblePlatformIndex = updateVisiblePlatformsList(
                mVisiblePlatforms, mRise.getPlatforms(),
                mVisibleAreaPosition, mMinVisiblePlatformIndex);
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.update(delta);
        }
        
        Vector2 cpNext = Pools.obtainVector();
        cpNext.set(mCharPosition.x + mCharSpeed.x * delta, mCharPosition.y + mCharSpeed.y * delta);
        Vector2 intersection = Pools.obtainVector();
        
        if (isCollisionWithPlatform(mVisiblePlatforms, mCharPosition, cpNext, intersection)) {
            mCharPosition.set(intersection);
            mCharSpeed.set(horizontalSpeed, jumpSpeed);
        } else {
            mCharPosition.set(cpNext);
            float speedY = Math.max(mCharSpeed.y - GRAVITY * delta, -JUMP_SPEED);
            mCharSpeed.set(horizontalSpeed, speedY);
        }
        
        Pools.freeVector(cpNext);
        Pools.freeVector(intersection);
        
        mCharPosition.x = GameUtils.getPositiveModulus(
                mCharPosition.x + CHARACTER_CENTER_X_OFFSET, GAME_AREA_WIDTH) - CHARACTER_CENTER_X_OFFSET;
        
        mVisibleAreaPosition = Math.max(
                mVisibleAreaPosition, mCharPosition.y - GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION);
    }
    
    private float getHorizontalSpeed() {
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
                return -DEFAULT_HORIZONTAL_SPEED;
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
                return DEFAULT_HORIZONTAL_SPEED;
            } else {
                return 0.0f;
            }
        } else if (Gdx.app.getType() == ApplicationType.Android) {
            return -Gdx.input.getAccelerometerX() * ACCELEROMETER_SPEED_MULTIPLIER;
        } else {
            return 0.0f;
        }
    }
    
    private static int updateVisiblePlatformsList(
            Array<PlatformBase> visiblePlatforms, Array<PlatformBase> allPlatforms,
            float visibleAreaPosition, int minVisiblePlatformIndex) {
        visiblePlatforms.clear();
        
        boolean isFirstVisible = true;
        for (int i = minVisiblePlatformIndex; i < allPlatforms.size; i++) {
            PlatformBase platform = allPlatforms.get(i);
            if (platform.isVisible(visibleAreaPosition)) {
                if (isFirstVisible) {
                    minVisiblePlatformIndex = i;
                    isFirstVisible = false;
                }
                visiblePlatforms.add(platform);
            }
        }
        
        return minVisiblePlatformIndex;
    }
    
    private static boolean isCollisionWithPlatform(
            Array<PlatformBase> platforms,
            Vector2 c1, Vector2 c2, Vector2 intersection) {
        
        // only check for collision when character is falling
        if (c2.y >= c1.y) {
            return false;
        }
        
        for (PlatformBase platform : platforms) {
            if (platform.isCollision(c1, c2, intersection)) {
                return true;
            }
        }
        
        return false;
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
