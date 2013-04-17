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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.init.InitData;
import com.turbogerm.hellhopper.init.SystemPlatformType;
import com.turbogerm.hellhopper.util.GameUtils;

public final class GameArea {
    
    public static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH;
    public static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT;
    
    public static final float CHARACTER_WIDTH = 40.0f;
    public static final float CHARACTER_HEIGHT = 60.0f;
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
    
    private static final int VISIBLE_PLATFORMS_INITIAL_CAPACITY = 50;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final InitData mInitData;
    
    private final Texture mCharacterTexture;
    private final Texture mPositionScrollLineTexture;
    private final Texture mPositionScrollBoxTexture;
    private final Texture mPositionScrollEndLineTexture;
    private final Texture mEndLineTexture;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mScore;
    
    private float mVisibleAreaPosition;
    private final Vector2 mCharacterPosition;
    private final Vector2 mCharacterSpeed;
    
    private int mMinVisiblePlatformIndex;
    private final Array<PlatformBase> mVisiblePlatforms;
    
    private final Rectangle mCharacterCollisionRect;
    private final Vector2 mCharacterCollisionLineStart;
    private final Vector2 mCharacterCollisionLineEnd;
    private final Vector2 mCollisionPoint;
    
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
        mPositionScrollLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE);
        mPositionScrollBoxTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE);
        mPositionScrollEndLineTexture = mAssetManager.get(ResourceNames.GAME_POSITION_SCROLL_END_LINE_TEXTURE);
        mEndLineTexture = mAssetManager.get(ResourceNames.GAME_END_LINE_TEXTURE);
        
        mCharacterPosition = new Vector2();
        mCharacterSpeed = new Vector2();
        
        mVisiblePlatforms = new Array<PlatformBase>(false, VISIBLE_PLATFORMS_INITIAL_CAPACITY);
        
        mCharacterCollisionRect = new Rectangle();
        mCharacterCollisionLineStart = new Vector2();
        mCharacterCollisionLineEnd = new Vector2();
        mCollisionPoint = new Vector2();
        
        mBackgroundColorInterpolator = new BackgroundColorInterpolator();
        mBackgroundColor = new Color();
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_COUTDOWN_DURATION;
        mEndJumpSpeed = JUMP_SPEED;
        mRise = RiseGenerator.generate(mAssetManager);
        mRiseHeight = mRise.getHeight();
        mBackgroundColorInterpolator.setRiseHeight(mRiseHeight);
        mScore = 0;
        mVisibleAreaPosition = 0.0f;
        mCharacterPosition.set(GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mCharacterSpeed.set(0.0f, JUMP_SPEED);
        mMinVisiblePlatformIndex = 0;
        updateVisiblePlatforms();
        mBackgroundColor.set(Color.BLACK);
    }
    
    public void update(float delta) {
        
        updateVisiblePlatforms();
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.update(delta);
        }
        
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
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.render(mBatch, mVisibleAreaPosition);
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
        
        float positionScrollBoxY = POSITION_SCROLL_LINE_Y + mVisibleAreaPosition / mRiseHeight *
                effectivePositionScrollLineHeight;
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
    
    private void updateVisiblePlatforms() {
        mVisiblePlatforms.clear();
        
        boolean isFirstVisible = true;
        Array<PlatformBase> platforms = mRise.getPlatforms();
        for (int i = mMinVisiblePlatformIndex; i < platforms.size; i++) {
            PlatformBase platform = platforms.get(i);
            if (platform.isVisible(mVisibleAreaPosition)) {
                if (isFirstVisible) {
                    mMinVisiblePlatformIndex = i;
                    isFirstVisible = false;
                }
                mVisiblePlatforms.add(platform);
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
        mCharacterCollisionRect.set(minX, minY, maxX - minX, maxY - minY);
        mCharacterCollisionLineStart.set(mCharacterPosition);
        mCharacterCollisionLineEnd.set(newX, newY);
        
        for (PlatformBase platform : mVisiblePlatforms) {
            if (platform.isCollision(
                    mCharacterCollisionRect, mCharacterCollisionLineStart, mCharacterCollisionLineEnd,
                    mCollisionPoint)) {
                mCharacterPosition.set(mCollisionPoint);
                return true;
            }
        }
        
        return false;
    }
    
    private void updatePositions(float delta, boolean isCollision, float jumpSpeed) {
        if (isCollision) {
            mCharacterSpeed.y = jumpSpeed;
        } else {
            mCharacterPosition.x += mCharacterSpeed.x * delta;
            mCharacterPosition.y += mCharacterSpeed.y * delta;
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
