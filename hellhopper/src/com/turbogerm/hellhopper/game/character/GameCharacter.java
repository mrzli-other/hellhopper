package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.game.RiseSection;
import com.turbogerm.hellhopper.game.character.states.CharacterStateManager;
import com.turbogerm.hellhopper.game.character.states.CharacterStateRenderData;
import com.turbogerm.hellhopper.game.character.states.CharacterStateUpdateData;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.items.ItemBase;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;

public final class GameCharacter {
    
    public static final float WIDTH = 1.0f;
    public static final float HEIGHT = 1.5f;
    public static final float CHARACTER_CENTER_X_OFFSET = WIDTH / 2.0f;
    public static final float COLLISION_WIDTH = WIDTH * 0.6f;
    public static final float COLLISION_WIDTH_OFFSET = (WIDTH - COLLISION_WIDTH) / 2.0f;
    public static final float COLLISION_LINE_LENGTH = COLLISION_WIDTH + COLLISION_WIDTH_OFFSET;
    
    public static final float JUMP_SPEED = 21.25f;
    public static final float GRAVITY = 35.0f;
    
    private final Vector2 mPosition;
    private final Vector2 mSpeed;
    
    private float mRiseHeight;
    
    private final CharacterStateManager mCharacterStateManager;
    private final CharacterEffects mCharacterEffects;
    
    private final CharacterStateUpdateData mCharacterStateUpdateData;
    private final CharacterStateRenderData mCharacterStateRenderData;
    
    public GameCharacter(AssetManager assetManager) {
        
        mPosition = new Vector2();
        mSpeed = new Vector2();
        
        mCharacterStateManager = new CharacterStateManager(assetManager);
        mCharacterEffects = new CharacterEffects();
        
        mCharacterStateUpdateData = new CharacterStateUpdateData();
        mCharacterStateRenderData = new CharacterStateRenderData();
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        mPosition.set(GameArea.GAME_AREA_WIDTH / 2.0f - CHARACTER_CENTER_X_OFFSET, 0.0f);
        mSpeed.set(0.0f, JUMP_SPEED);
        
        mCharacterStateManager.reset();
        mCharacterEffects.reset();
    }
    
    public void update(float horizontalSpeed,
            PlatformToCharCollisionData platformToCharCollisionData,
            Array<RiseSection> activeRiseSections,
            Array<PlatformBase> visiblePlatforms,
            Array<EnemyBase> visibleEnemies,
            Array<ItemBase> visibleItems,
            float visibleAreaPosition,
            float delta) {
        
        mCharacterStateUpdateData.characterPosition = mPosition;
        mCharacterStateUpdateData.characterSpeed = mSpeed;
        mCharacterStateUpdateData.horizontalSpeed = horizontalSpeed;
        mCharacterStateUpdateData.platformToCharCollisionData = platformToCharCollisionData;
        mCharacterStateUpdateData.activeRiseSections = activeRiseSections;
        mCharacterStateUpdateData.visiblePlatforms = visiblePlatforms;
        mCharacterStateUpdateData.visibleEnemies = visibleEnemies;
        mCharacterStateUpdateData.visibleItems = visibleItems;
        mCharacterStateUpdateData.riseHeight = mRiseHeight;
        mCharacterStateUpdateData.visibleAreaPosition = visibleAreaPosition;
        mCharacterStateUpdateData.characterEffects = mCharacterEffects;
        mCharacterStateUpdateData.delta = delta;
        
        mCharacterStateManager.getCurrentState().update(mCharacterStateUpdateData);
    }
    
    public void render(SpriteBatch batch) {
        mCharacterStateRenderData.batch = batch;
        mCharacterStateRenderData.characterPosition = mPosition;
        mCharacterStateRenderData.characterEffects = mCharacterEffects;
        
        mCharacterStateManager.getCurrentState().render(mCharacterStateRenderData);
    }
    
    public boolean isFinished() {
        return mCharacterStateManager.getCurrentState().isFinished();
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }
    
    public Vector2 getSpeed() {
        return mSpeed;
    }
    
    public int getScore() {
        return mCharacterEffects.getScore();
    }
}
