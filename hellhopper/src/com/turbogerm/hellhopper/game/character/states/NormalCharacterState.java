package com.turbogerm.hellhopper.game.character.states;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.CollisionEffects;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.character.GameCharacter;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.util.GameUtils;
import com.turbogerm.hellhopper.util.Pools;

final class NormalCharacterState extends CharacterStateBase {
    
    private boolean mIsDying;
    
    private final Rectangle mRect;
    
    private final CharCollisionData mCharCollisionData;
    private final CollisionEffects mCollisionEffects;
    
    private final Sound mJumpSound;
    private final Sound mJumpBoostSound;
    
    public NormalCharacterState(CharacterStateManager characterStateManager, AssetManager assetManager) {
        super(characterStateManager);
        
        mRect = new Rectangle(0.0f, 0.0f, GameCharacter.WIDTH, GameCharacter.HEIGHT);
        
        mCharCollisionData = new CharCollisionData();
        mCollisionEffects = new CollisionEffects();
        
        mJumpSound = assetManager.get(ResourceNames.SOUND_JUMP);
        mJumpBoostSound = assetManager.get(ResourceNames.SOUND_JUMP_BOOST);
    }
    
    @Override
    public void reset() {
        mIsDying = false;
    }
    
    @Override
    public void update(CharacterStateUpdateData updateData) {
        
        Vector2 position = updateData.characterPosition;
        Vector2 speed = updateData.characterSpeed;
        
        handleFall(position, speed, updateData.visibleAreaPosition);
        handleCollisionWithPlatform(
                position,
                speed,
                updateData.platformToCharCollisionData,
                updateData.activeRiseSections,
                updateData.visiblePlatforms,
                updateData.delta);
        
        speed.x = updateData.horizontalSpeed;
        position.x = GameUtils.getPositiveModulus(
                position.x + GameCharacter.CHARACTER_CENTER_X_OFFSET, GameArea.GAME_AREA_WIDTH) -
                GameCharacter.CHARACTER_CENTER_X_OFFSET;
        
        if (mIsDying) {
            return;
        }
        
        handleCollisionWithEnemy(position, updateData.visibleEnemies);
        if (position.y > updateData.riseHeight) {
            changeState(CharacterStateManager.END_CHARACTER_STATE);
        }
    }
    
    private void handleFall(Vector2 position, Vector2 speed, float visibleAreaPosition) {
        if (position.y <= 0.0f) {
            position.y = 0.0f;
            speed.y = GameCharacter.JUMP_SPEED;
        } else if (position.y < visibleAreaPosition) {
            mIsDying = true;
            changeState(CharacterStateManager.DYING_FALL_CHARACTER_STATE);
        }
    }
    
    private void handleCollisionWithPlatform(
            Vector2 position,
            Vector2 speed,
            PlatformToCharCollisionData platformToCharCollisionData,
            Array<RiseSection> activeRiseSections,
            Array<PlatformBase> visiblePlatforms,
            float delta) {
        
        if (!platformToCharCollisionData.isCollision || mIsDying) {
            Vector2 cpNext = Pools.obtainVector();
            cpNext.set(position.x + speed.x * delta, position.y + speed.y * delta);
            Vector2 intersection = Pools.obtainVector();
            
            if (!mIsDying && isCollisionWithPlatform(
                    visiblePlatforms, position, cpNext, intersection, mCharCollisionData)) {
                position.set(intersection);
                handleCollisionWithPlatform(position, speed, activeRiseSections);
            } else {
                position.set(cpNext);
                speed.y = Math.max(speed.y - GameCharacter.GRAVITY * delta, -GameCharacter.JUMP_SPEED);
            }
            
            Pools.freeVector(cpNext);
            Pools.freeVector(intersection);
        } else {
            position.y = platformToCharCollisionData.collisionPoint.y;
            mCharCollisionData.collisionPlatform = platformToCharCollisionData.collisionPlatform;
            mCharCollisionData.collisionPointX = platformToCharCollisionData.collisionPoint.x;
            handleCollisionWithPlatform(position, speed, activeRiseSections);
        }
    }
    
    private void handleCollisionWithPlatform(
            Vector2 position,
            Vector2 speed,
            Array<RiseSection> activeRiseSections) {
        
        mCharCollisionData.collisionPlatform.getCollisionEffects(
                mCharCollisionData.collisionPointX, mCollisionEffects);
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.BURN)) {
            mIsDying = true;
            mCollisionEffects.clear();
            changeState(CharacterStateManager.DYING_FIRE_CHARACTER_STATE);
            return;
        }
        
        if (mCollisionEffects.isEffectActive(CollisionEffects.JUMP_BOOST)) {
            speed.y = mCollisionEffects.getValue(CollisionEffects.JUMP_BOOST,
                    CollisionEffects.JUMP_BOOST_SPEED_INDEX);
            float volume = mCollisionEffects.getValue(CollisionEffects.JUMP_BOOST,
                    CollisionEffects.JUMP_BOOST_SOUND_VOLUME_INDEX);
            mJumpBoostSound.play(volume);
        } else {
            speed.y = GameCharacter.JUMP_SPEED;
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
    
    private void handleCollisionWithEnemy(Vector2 position, Array<EnemyBase> visibleEnemies) {
        mRect.x = position.x;
        mRect.y = position.y;
        
        for (EnemyBase enemyBase : visibleEnemies) {
            if (enemyBase.isCollision(mRect)) {
                changeState(CharacterStateManager.DYING_ENEMY_CHARACTER_STATE);
                return;
            }
        }
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
    
    private static class CharCollisionData {
        public PlatformBase collisionPlatform;
        public float collisionPointX;
    }
}
