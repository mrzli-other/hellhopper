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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.util.ColorInterpolator;
import com.turbogerm.hellhopper.util.GameUtils;
import com.turbogerm.hellhopper.util.Pools;

public final class GameCharacter {
    
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.5f;
    private static final float CHARACTER_CENTER_X_OFFSET = WIDTH / 2.0f;
    public static final float COLLISION_WIDTH = WIDTH * 0.6f;
    public static final float COLLISION_WIDTH_OFFSET = (WIDTH - COLLISION_WIDTH) / 2.0f;
    public static final float COLLISION_LINE_LENGTH = COLLISION_WIDTH + COLLISION_WIDTH_OFFSET;
    
    private static final float BODY_OFFSET_X = 0.05f;
    private static final float BODY_OFFSET_Y = 0.0f;
    private static final float BODY_WIDTH = 0.9f;
    private static final float BODY_HEIGHT = 0.8f;
    private static final float HEAD_OFFSET_X = 0.0f;
    private static final float HEAD_OFFSET_Y = 0.35f;
    private static final float HEAD_WIDTH = 1.0f;
    private static final float HEAD_HEIGHT = 1.15f;
    private static final float EYES_OFFSET_X = 0.075f;
    private static final float EYES_OFFSET_Y = 0.65f;
    private static final float EYES_WIDTH = 0.85f;
    private static final float EYES_HEIGHT = 0.45f;
    
    private static final Color DEFAULT_BODY_COLOR;
    private static final Color DEFAULT_HEAD_COLOR;
    private static final Color DEFAULT_EYES_COLOR;
    private static final Color ENEMY_DEATH_COLOR;
    private static final Color FIRE_DEATH_COLOR;
    
    private static final float EPSILON = 1e-5f;
    
    public static final float JUMP_SPEED = 21.25f;
    private static final float GRAVITY = 35.0f;
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_REACHED_COUTDOWN_DURATION = 3.0f;
    
    private static final float DYING_ANIMATION_DURATION = 2.0f;
    private static final float DYING_DURATION = DYING_ANIMATION_DURATION + 1.0f;
    
    private final Sprite mBodySprite;
    private final Sprite mHeadSprite;
    private final Sprite[] mEyesSprites;
    
    private final Color mBodyColor;
    private final Color mHeadColor;
    
    private final Vector2 mPosition;
    private final Vector2 mSpeed;
    private final Rectangle mRect;
    
    private float mRiseHeight;
    
    private boolean mIsEndReached;
    private float mEndReachedCountdown;
    private boolean mIsDead;
    private boolean mIsDyingFromEnemy;
    private boolean mIsDyingFromFire;
    private float mDyingElapsed;
    
    private final CharCollisionData mCharCollisionData;
    private final CollisionEffects mCollisionEffects;
    
    private final Sound mJumpSound;
    private final Sound mJumpBoostSound;
    
    private final ColorInterpolator mColorInterpolator;
    
    private final BlinkingStateMachine mBlinkingStateMachine;
    
    // TODO: for testing
    public int deathCount;
    
    static {
        DEFAULT_BODY_COLOR = new Color(0.14f, 0.36f, 0.43f, 1.0f);
        DEFAULT_HEAD_COLOR = new Color(0.57f, 0.74f, 0.79f, 1.0f);
        DEFAULT_EYES_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
        ENEMY_DEATH_COLOR = Color.RED;
        FIRE_DEATH_COLOR = Color.BLACK;
    }
    
    public GameCharacter(AssetManager assetManager) {
        
        Texture bodyTexture = assetManager.get(ResourceNames.CHARACTER_BODY_TEXTURE);
        mBodySprite = new Sprite(bodyTexture);
        mBodySprite.setSize(BODY_WIDTH, BODY_HEIGHT);
        
        Texture headTexture = assetManager.get(ResourceNames.CHARACTER_HEAD_TEXTURE);
        mHeadSprite = new Sprite(headTexture);
        mHeadSprite.setSize(HEAD_WIDTH, HEAD_HEIGHT);
        
        mEyesSprites = new Sprite[ResourceNames.CHARACTER_EYES_TEXTURE_COUNT];
        for (int i = 0; i < mEyesSprites.length; i++) {
            mEyesSprites[i] = new Sprite((Texture) assetManager.get(ResourceNames.getCharacterEyesTexture(i)));
            mEyesSprites[i].setSize(EYES_WIDTH, EYES_HEIGHT);
            mEyesSprites[i].setColor(DEFAULT_EYES_COLOR);
        }
        
        mBodyColor = new Color();
        mHeadColor = new Color();
        
        mPosition = new Vector2();
        mSpeed = new Vector2();
        mRect = new Rectangle(0.0f, 0.0f, WIDTH, HEIGHT);
        
        mCharCollisionData = new CharCollisionData();
        mCollisionEffects = new CollisionEffects();
        
        mJumpSound = assetManager.get(ResourceNames.SOUND_JUMP);
        mJumpBoostSound = assetManager.get(ResourceNames.SOUND_JUMP_BOOST);
        
        mColorInterpolator = new ColorInterpolator();
        
        mBlinkingStateMachine = new BlinkingStateMachine();
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mBodyColor.set(DEFAULT_BODY_COLOR);
        mHeadColor.set(DEFAULT_HEAD_COLOR);
        
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_COUTDOWN_DURATION;
        
        mPosition.set(GameArea.GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mSpeed.set(0.0f, JUMP_SPEED);
        
        mIsDead = false;
        mIsDyingFromEnemy = false;
        mIsDyingFromFire = false;
        mDyingElapsed = 0.0f;
        
        deathCount = 0;
    }
    
    public boolean preUpdate(float visibleAreaPosition, float delta) {
        
        if (mIsDead) {
            return false;
        }
        
        if (isDying()) {
            mDyingElapsed += delta;
            if (mDyingElapsed >= DYING_DURATION) {
                mIsDead = true;
                return false;
            }
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
                deathCount++;
                //return false;
            }
        }
        
        return true;
    }
    
    public void updateStep(float horizontalSpeed,
            PlatformToCharCollisionData platformToCharCollisionData,
            Array<RiseSection> activeRiseSections,
            Array<PlatformBase> visiblePlatforms,
            Array<EnemyBase> visibleEnemies,
            float delta) {
        
        if (isDying()) {
            return;
        }
        
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
            processCollision(activeRiseSections);
        }
        
        mPosition.x = GameUtils.getPositiveModulus(
                mPosition.x + CHARACTER_CENTER_X_OFFSET, GameArea.GAME_AREA_WIDTH) - CHARACTER_CENTER_X_OFFSET;
        
        if (mPosition.y > mRiseHeight) {
            mIsEndReached = true;
        }
        
        mRect.x = mPosition.x;
        mRect.y = mPosition.y;
        
        for (EnemyBase enemyBase : visibleEnemies) {
            if (enemyBase.isCollision(mRect)) {
                mIsDyingFromEnemy = true;
                deathCount++;
                return;
            }
        }
        
        mBlinkingStateMachine.update(delta);
    }
    
    public void render(SpriteBatch batch) {
        
        if (isDying()) {
            Color deathColor = mIsDyingFromEnemy ? ENEMY_DEATH_COLOR : FIRE_DEATH_COLOR;
            float t = MathUtils.clamp(mDyingElapsed / DYING_ANIMATION_DURATION, 0.0f, 1.0f);
            mBodyColor.set(mColorInterpolator.interpolateColor(DEFAULT_BODY_COLOR, deathColor, t));
            mHeadColor.set(mColorInterpolator.interpolateColor(DEFAULT_BODY_COLOR, deathColor, t));
        }
        
        mBodySprite.setColor(mBodyColor);
        mHeadSprite.setColor(mHeadColor);
        
        mBodySprite.setPosition(mPosition.x + BODY_OFFSET_X, mPosition.y + BODY_OFFSET_Y);
        mBodySprite.draw(batch);
        
        mHeadSprite.setPosition(mPosition.x + HEAD_OFFSET_X, mPosition.y + HEAD_OFFSET_Y);
        mHeadSprite.draw(batch);
        
        int eyesSpriteIndex = mBlinkingStateMachine.getTextureIndex();
        mEyesSprites[eyesSpriteIndex].setPosition(mPosition.x + EYES_OFFSET_X, mPosition.y + EYES_OFFSET_Y);
        mEyesSprites[eyesSpriteIndex].draw(batch);
    }
    
    private void processCollision(Array<RiseSection> activeRiseSections) {
        
        mCharCollisionData.collisionPlatform.getCollisionEffects(
                mCharCollisionData.collisionPointX, mCollisionEffects);
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.BURN)) {
            mIsDyingFromFire = true;
            deathCount++;
            mCollisionEffects.clear();
            return;
        }
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.JUMP_BOOST)) {
            mSpeed.y = mCollisionEffects.getValue(CollisionEffects.JUMP_BOOST,
                    CollisionEffects.JUMP_BOOST_SPEED_INDEX);
            float volume = mCollisionEffects.getValue(CollisionEffects.JUMP_BOOST,
                    CollisionEffects.JUMP_BOOST_SOUND_VOLUME_INDEX);
            mJumpBoostSound.play(volume);
        } else {
            mSpeed.y = JUMP_SPEED;
            mJumpSound.play();
        }
        
        RiseSection riseSection = getRiseSection(
                mCharCollisionData.collisionPlatform.getRiseSectionId(), activeRiseSections);
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.REPOSITION_PLATFORMS)) {
            riseSection.applyEffect(CollisionEffects.REPOSITION_PLATFORMS);
        }
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.VISIBLE_ON_JUMP)) {
            riseSection.applyEffect(CollisionEffects.VISIBLE_ON_JUMP);
        }
        
        mCollisionEffects.clear();
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
    
    private static RiseSection getRiseSection(int id, Array<RiseSection> riseSections) {
        for (RiseSection riseSection : riseSections) {
            if (riseSection.getId() == id) {
                return riseSection;
            }
        }
        
        return null;
    }
    
    private boolean isDying() {
        // TODO: uncomment this after testing
        //return mIsDyingFromEnemy || mIsDyingFromFire;
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
    
    private static class BlinkingStateMachine {
        
        private static final int OPEN = 0;
        private static final int CLOSED = 1;
        private static final int DOUBLE_BLINK_OPEN = 2;
        private static final int DOUBLE_BLINK_CLOSED_1 = 3;
        private static final int DOUBLE_BLINK_CLOSED_2 = 4;
        //private static final int STATE_COUNT = 5;
        
        private static final float MIN_OPEN_DURATION = 2.0f;
        private static final float MAX_OPEN_DURATION = 5.0f;
        private static final float CLOSED_DURATION = 0.2f;
        private static final float DOUBLE_BLINK_OPEN_DURATION = 0.2f;
        private static final float DOUBLE_BLINK_CLOSED_DURATION = 0.1f;
        
        private static final float DOUBLE_BLINK_CHANCE = 0.3f;
        
        private int mCurrentState;
        private float mCurrentStateDuration;
        private float mCurrentStateElapsed;
        
        public BlinkingStateMachine() {
            mCurrentState = OPEN;
            mCurrentStateElapsed = 0.0f;
            updateStateDuration();
        }
        
        public void update(float delta) {
            mCurrentStateElapsed += delta;
            
            if (mCurrentStateElapsed >= mCurrentStateDuration) {
                mCurrentStateElapsed -= mCurrentStateDuration;
                changeState();
                updateStateDuration();
            }
        }
        
        public int getTextureIndex() {
            if (mCurrentState == OPEN || mCurrentState == DOUBLE_BLINK_OPEN) {
                return 0;
            } else {
                return 1;
            }
        }
        
        private void changeState() {
            mCurrentState = getNextState(mCurrentState);
        }
        
        private static int getNextState(int currentState) {
            switch (currentState) {
                case OPEN:
                    if (isDoubleBlink()) {
                        return DOUBLE_BLINK_CLOSED_1;
                    } else {
                        return CLOSED;
                    }
                    
                case CLOSED:
                    return OPEN;
                    
                case DOUBLE_BLINK_OPEN:
                    return DOUBLE_BLINK_CLOSED_2;
                    
                case DOUBLE_BLINK_CLOSED_1:
                    return DOUBLE_BLINK_OPEN;
                    
                case DOUBLE_BLINK_CLOSED_2:
                    return OPEN;
                
                default:
                    return OPEN;
            }
        }
        
        private void updateStateDuration() {
            mCurrentStateDuration = getStateDuration(mCurrentState);
        }
        
        private static float getStateDuration(int state) {
            switch (state) {
                case OPEN:
                    return MathUtils.random(MIN_OPEN_DURATION, MAX_OPEN_DURATION);
                    
                case CLOSED:
                    return CLOSED_DURATION;
                    
                case DOUBLE_BLINK_OPEN:
                    return DOUBLE_BLINK_OPEN_DURATION;
                    
                case DOUBLE_BLINK_CLOSED_1:
                case DOUBLE_BLINK_CLOSED_2:
                    return DOUBLE_BLINK_CLOSED_DURATION;
                
                default:
                    return 0.0f;
            }
        }
        
        private static boolean isDoubleBlink() {
            return MathUtils.random() < DOUBLE_BLINK_CHANCE;
        }
    }
}
