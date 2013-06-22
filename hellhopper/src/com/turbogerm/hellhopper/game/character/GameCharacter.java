package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.CollisionEffects;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.items.ItemBase;
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
    
    public static final float JUMP_SPEED = 21.25f;
    private static final float GRAVITY = 35.0f;
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_REACHED_COUTDOWN_DURATION = 3.0f;
    
    private static final float DYING_ANIMATION_DURATION = 2.0f;
    private static final float DYING_DURATION = DYING_ANIMATION_DURATION + 1.0f;
    
    private final CharacterGraphics mCharacterGraphics;
    
    private final Vector2 mPosition;
    private final Vector2 mSpeed;
    private final Rectangle mRect;
    
    private final ShieldEffect mShieldEffect;
    
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
    
    // TODO: for testing
    public int deathCount;
    
    public GameCharacter(AssetManager assetManager) {
        
        mCharacterGraphics = new CharacterGraphics(assetManager);
        
        mShieldEffect = new ShieldEffect(assetManager);
        
        mPosition = new Vector2();
        mSpeed = new Vector2();
        mRect = new Rectangle(0.0f, 0.0f, WIDTH, HEIGHT);
        
        mCharCollisionData = new CharCollisionData();
        mCollisionEffects = new CollisionEffects();
        
        mJumpSound = assetManager.get(ResourceNames.SOUND_JUMP);
        mJumpBoostSound = assetManager.get(ResourceNames.SOUND_JUMP_BOOST);
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mCharacterGraphics.reset();
        
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
                mPosition.y = mRiseHeight + GameUtils.EPSILON;
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
            Array<ItemBase> visibleItems,
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
                
                // TODO: only for testing, revert
                float speedY = Math.max(mSpeed.y - GRAVITY * delta, -JUMP_SPEED);
                //float speedY = JUMP_SPEED;
                
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
        
        mShieldEffect.update(delta);
        
        mCharacterGraphics.update(delta);
    }
    
    public void render(SpriteBatch batch) {
        mCharacterGraphics.render(batch, mPosition);
        mShieldEffect.render(batch, mPosition);
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
}
