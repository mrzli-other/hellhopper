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
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.debug.PerformanceData;
import com.turbogerm.hellhopper.game.generator.RiseGenerator;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.util.Pools;

public final class GameArea {
    
    private static final float METER_TO_PIXEL = 40.0f;
    private static final float PIXEL_TO_METER = 1.0f / METER_TO_PIXEL;
    
    public static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH * PIXEL_TO_METER;
    public static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT * PIXEL_TO_METER;
    
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    
    private static final float DEFAULT_HORIZONTAL_SPEED = 10.0f;
    private static final float ACCELEROMETER_SPEED_MULTIPLIER = 3.75f;
    
    private static final float MAX_DELTA = 0.1f;
    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_STEP = 1.0f / UPDATE_RATE;
    
    private static final float END_LINE_HEIGHT = 0.1f;
    
    private static final float ACTIVE_PLATFORMS_AREA_PADDING = 5.0f;
    
    private static final int VISIBLE_PLATFORMS_INITIAL_CAPACITY = 50;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final PerformanceData mPerformanceData;
    
    private final Texture mEndLineTexture;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mScore;
    
    private float mVisibleAreaPosition;
    private final GameCharacter mCharacter;
    private final PlatformToCharCollisionData mPlatformToCharCollisionData;
    
    private float mDeltaAccumulator;
    
    private int mMinVisiblePlatformIndex;
    private final Array<PlatformBase> mVisiblePlatforms;
    
    private boolean mIsGameOver;
    
    private final BackgroundColorInterpolator mBackgroundColorInterpolator;
    private final Color mBackgroundColor;
    
    
    public GameArea(AssetManager assetManager) {
        
        mAssetManager = assetManager;
        mBatch = new SpriteBatch();
        mPerformanceData = new PerformanceData(mBatch);
        
        mEndLineTexture = mAssetManager.get(ResourceNames.GAME_END_LINE_TEXTURE);
        
        mCharacter = new GameCharacter(mAssetManager);
        mPlatformToCharCollisionData = new PlatformToCharCollisionData();
        
        mVisiblePlatforms = new Array<PlatformBase>(false, VISIBLE_PLATFORMS_INITIAL_CAPACITY);
        
        mBackgroundColorInterpolator = new BackgroundColorInterpolator();
        mBackgroundColor = new Color();
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        
        mRise = RiseGenerator.generate(mAssetManager);
        mRiseHeight = mRise.getHeight();
        
        mScore = 0;
        
        mVisibleAreaPosition = 0.0f;
        mCharacter.reset(mRiseHeight);
        
        mDeltaAccumulator = 0.0f;
        
        mMinVisiblePlatformIndex = 0;
        
        mBackgroundColorInterpolator.setRiseHeight(mRiseHeight);
        mBackgroundColor.set(Color.BLACK);
    }
    
    public void update(float delta) {
        
        if (delta > MAX_DELTA) {
            delta = MAX_DELTA;
        }
        
        mIsGameOver = !mCharacter.preUpdate(mVisibleAreaPosition, delta);
        if (mIsGameOver) {
            return;
        }
        
        float horizontalSpeed = getHorizontalSpeed();
        
        mDeltaAccumulator += delta;
        while (mDeltaAccumulator >= UPDATE_STEP) {
            updateStep(horizontalSpeed, UPDATE_STEP);
            mDeltaAccumulator -= UPDATE_STEP;
        }
        
        updateStep(horizontalSpeed, mDeltaAccumulator);
        mDeltaAccumulator = 0.0f;
        
        float effectiveCharPositionY = Math.min(mCharacter.getPosition().y, mRiseHeight);
        mScore = Math.max(mScore, (int) (effectiveCharPositionY * METER_TO_PIXEL));
        
        mBackgroundColor.set(mBackgroundColorInterpolator.getBackgroundColor(mVisibleAreaPosition));
    }
    
    public void render(float delta) {
        
        mBatch.getProjectionMatrix().setToOrtho2D(0.0f, mVisibleAreaPosition, GAME_AREA_WIDTH, GAME_AREA_HEIGHT);
        mBatch.begin();
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.render(mBatch, delta);
        }
        
        mBatch.draw(mEndLineTexture, 0.0f, mRiseHeight - END_LINE_HEIGHT, GAME_AREA_WIDTH, END_LINE_HEIGHT);
        
        mCharacter.render(mBatch);
        
        mBatch.end();
    }
    
    private void updateStep(float horizontalSpeed, float delta) {
        
        updateVisiblePlatformsList();
        
        updatePlatforms(delta);
        
        mCharacter.updateStep(horizontalSpeed, mPlatformToCharCollisionData, mVisiblePlatforms, delta);
        
        mVisibleAreaPosition = Math.max(
                mVisibleAreaPosition, mCharacter.getPosition().y -
                GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION);
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
    
    private void updateVisiblePlatformsList() {
        mVisiblePlatforms.clear();
        Array<PlatformBase> allPlatforms = mRise.getPlatforms();
        
        boolean isFirstVisible = true;
        for (int i = mMinVisiblePlatformIndex; i < allPlatforms.size; i++) {
            PlatformBase platform = allPlatforms.get(i);
            if (platform.isActive(mVisibleAreaPosition, ACTIVE_PLATFORMS_AREA_PADDING)) {
                if (isFirstVisible) {
                    mMinVisiblePlatformIndex = i;
                    isFirstVisible = false;
                }
                mVisiblePlatforms.add(platform);
            }
        }
    }
    
    private void updatePlatforms(float delta) {
        Vector2 c1 = Pools.obtainVector();
        Vector2 c2 = Pools.obtainVector();
        mPlatformToCharCollisionData.reset();
        
        Vector2 charPosition = mCharacter.getPosition();
        
        c1.set(charPosition.x + GameCharacter.COLLISION_WIDTH_OFFSET - PlatformData.PLATFORM_WIDTH, charPosition.y);
        c2.set(charPosition.x + GameCharacter.COLLISION_LINE_LENGTH, charPosition.y);
        
        // only check for collision when character is going down
        mPlatformToCharCollisionData.isEnabled = mCharacter.getSpeed().y < 0.0f;
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.update(delta, c1, c2, mPlatformToCharCollisionData);
        }
        
        Pools.freeVector(c1);
        Pools.freeVector(c2);
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
    
    public float getRiseHeight() {
        return mRiseHeight;
    }
    
    public float getVisibleAreaPosition() {
        return mVisibleAreaPosition;
    }
    
    public PerformanceData getPerformanceData() {
        return mPerformanceData;
    }
}
