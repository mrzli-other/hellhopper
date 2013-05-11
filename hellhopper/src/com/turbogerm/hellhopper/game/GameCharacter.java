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
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.util.GameUtils;
import com.turbogerm.hellhopper.util.Pools;

public final class GameCharacter {
    
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.5f;
    private static final float CHARACTER_CENTER_X_OFFSET = WIDTH / 2.0f;
    public static final float COLLISION_WIDTH = WIDTH * 0.6f;
    public static final float COLLISION_WIDTH_OFFSET = (WIDTH - COLLISION_WIDTH) / 2.0f;
    public static final float COLLISION_LINE_LENGTH = COLLISION_WIDTH + COLLISION_WIDTH_OFFSET; 
    
    private static final float EPSILON = 1e-5f;
    
    public static final float JUMP_SPEED = 21.25f;
    private static final float GRAVITY = 35.0f;
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_REACHED_COUTDOWN_DURATION = 3.0f;
    
    private final Texture mCharacterTexture;
    
    private final Vector2 mPosition;
    private final Vector2 mSpeed;
    
    private float mRiseHeight;
    
    private boolean mIsEndReached;
    private float mEndReachedCountdown;
    private boolean mIsDead;
    
    private final CharCollisionData mCharCollisionData;
    private final CollisionEffect mCollisionEffect;
    
    public GameCharacter(AssetManager assetManager) {
        mCharacterTexture = assetManager.get(ResourceNames.GAME_CHARACTER_TEXTURE);
        
        mPosition = new Vector2();
        mSpeed = new Vector2();
        
        mCharCollisionData = new CharCollisionData();
        mCollisionEffect = new CollisionEffect();
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_COUTDOWN_DURATION;
        
        mPosition.set(GameArea.GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mSpeed.set(0.0f, JUMP_SPEED);
        
        mIsDead = false;
    }
    
    public boolean preUpdate(float visibleAreaPosition, float delta) {
        
        if (mIsDead) {
            return false;
        }
        
        if (mIsEndReached) {
            if (mEndReachedCountdown <= 0.0f) {
                return false;
            } else if (mPosition.y <= mRiseHeight) {
                mPosition.y = mRiseHeight + EPSILON;
                mSpeed.y = Math.abs(mSpeed.y * END_RESTITUTION_MULTIPLIER);
                mSpeed.y = Math.max(mSpeed.y - END_RESTITUTION_SPEED_DECREASE, 0.0f);
            }
            
            mEndReachedCountdown -= delta;
        } else {
            if (mPosition.y <= 0.0f) {
                mPosition.y = 0.0f;
                mSpeed.y = JUMP_SPEED;
            } else if (mPosition.y < visibleAreaPosition) {
                // TODO: only for testing; remove next line and uncomment following
                mSpeed.y = JUMP_SPEED;
                //return false;
            }
        }
        
        return true;
    }
    
    public void updateStep(float horizontalSpeed,
            PlatformToCharCollisionData platformToCharCollisionData,
            Array<PlatformBase> visiblePlatforms,
            float delta) {
        
        boolean isCollision = false;
        if (!platformToCharCollisionData.isCollision) {
            Vector2 cpNext = Pools.obtainVector();
            cpNext.set(mPosition.x + mSpeed.x * delta, mPosition.y + mSpeed.y * delta);
            Vector2 intersection = Pools.obtainVector();
            
            if (isCollisionWithPlatform(visiblePlatforms, mPosition, cpNext, intersection,
                    mCharCollisionData)) {
                mPosition.set(intersection);
                isCollision = true;
            } else {
                mPosition.set(cpNext);
                float speedY = Math.max(mSpeed.y - GRAVITY * delta, -JUMP_SPEED);
                mSpeed.set(horizontalSpeed, speedY);
            }
            
            Pools.freeVector(cpNext);
            Pools.freeVector(intersection);
        } else {
            mPosition.y = platformToCharCollisionData.collisionPoint.y;
            mCharCollisionData.collisionPlatform = platformToCharCollisionData.collisionPlatform;
            mCharCollisionData.collisionPointX = platformToCharCollisionData.collisionPoint.x;
            isCollision = true;
        }
        
        mSpeed.x = horizontalSpeed;
        
        if (isCollision) {
            processCollision();
        }
        
        mPosition.x = GameUtils.getPositiveModulus(
                mPosition.x + CHARACTER_CENTER_X_OFFSET, GameArea.GAME_AREA_WIDTH) - CHARACTER_CENTER_X_OFFSET;
        
        if (mPosition.y > mRiseHeight) {
            mIsEndReached = true;
        }
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(mCharacterTexture, mPosition.x, mPosition.y, WIDTH, HEIGHT);
    }
    
    private boolean processCollision() {
        
        mCharCollisionData.collisionPlatform.fillCollisionEffect(
                mCharCollisionData.collisionPointX, mCollisionEffect);
        
        switch (mCollisionEffect.getEffect()) {
            case CollisionEffect.NONE:
                mSpeed.y = JUMP_SPEED;
                break;
                
            case CollisionEffect.JUMP_BOOST:
                mSpeed.y = mCollisionEffect.getValue();
                break;
                
            case CollisionEffect.BURN:
                mIsDead = true;
                break;
            
            default:
                break;
        }
        
        return true;
    }
    
    private static boolean isCollisionWithPlatform(
            Array<PlatformBase> platforms,
            Vector2 c1, Vector2 c2, Vector2 intersection, CharCollisionData charCollisionData) {
        
        // only check for collision when character is going down
        if (c2.y >= c1.y) {
            return false;
        }
        
        for (PlatformBase platform : platforms) {
            if (platform.isCollision(c1, c2, intersection)) {
                charCollisionData.collisionPlatform = platform;
                charCollisionData.collisionPointX = intersection.x;
                return true;
            }
        }
        
        return false;
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }
    
    public Vector2 getSpeed() {
        return mSpeed;
    }
    
    private static class CharCollisionData {
        
        public PlatformBase collisionPlatform;
        public float collisionPointX;
    }
}
