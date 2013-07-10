package com.turbogerm.helljump.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.Pools;
import com.turbogerm.helljump.dataaccess.PlatformData;
import com.turbogerm.helljump.game.character.GameCharacter;
import com.turbogerm.helljump.game.enemies.EnemyBase;
import com.turbogerm.helljump.game.items.ItemBase;
import com.turbogerm.helljump.game.platforms.PlatformBase;

final class GameActiveAreaObjects {
    
    private static final int ACTIVE_RISE_SECTIONS_INITIAL_CAPACITY = 5;
    private static final int VISIBLE_PLATFORMS_INITIAL_CAPACITY = 50;
    private static final int VISIBLE_ENEMIES_INITIAL_CAPACITY = 10;
    private static final int VISIBLE_ITEMS_INITIAL_CAPACITY = 5;
    
    private static final float VISIBLE_PLATFORMS_AREA_PADDING = 2.0f;
    
    private final Array<RiseSection> mActiveRiseSections;
    private final Array<PlatformBase> mVisiblePlatforms;
    private final Array<EnemyBase> mVisibleEnemies;
    private final Array<ItemBase> mVisibleItems;
    
    private final PlatformToCharCollisionData mPlatformToCharCollisionData;
    
    public GameActiveAreaObjects() {
        mActiveRiseSections = new Array<RiseSection>(true, ACTIVE_RISE_SECTIONS_INITIAL_CAPACITY);
        mVisiblePlatforms = new Array<PlatformBase>(true, VISIBLE_PLATFORMS_INITIAL_CAPACITY);
        mVisibleEnemies = new Array<EnemyBase>(true, VISIBLE_ENEMIES_INITIAL_CAPACITY);
        mVisibleItems = new Array<ItemBase>(true, VISIBLE_ITEMS_INITIAL_CAPACITY);
        
        mPlatformToCharCollisionData = new PlatformToCharCollisionData();
    }
    
    public void render(SpriteBatch batch) {
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.render(batch);
        }
        
        for (EnemyBase enemy : mVisibleEnemies) {
            enemy.render(batch);
        }
        
        for (ItemBase item : mVisibleItems) {
            item.render(batch);
        }
    }
    
    public void renderText(SpriteBatch batch, float visibleAreaPosition, BitmapFont itemFont) {
        for (ItemBase item : mVisibleItems) {
            item.renderText(batch, visibleAreaPosition, itemFont);
        }
    }
    
    public void update(Rise rise, GameCharacter character, float visibleAreaPosition, float delta) {
        upadteActiveAndVisibleObjects(rise, visibleAreaPosition);
        
        updatePlatforms(character, delta);
        updateEnemies(character, delta);
        updateItems(character, delta);
    }
    
    private void upadteActiveAndVisibleObjects(Rise rise, float visibleAreaPosition) {
        mActiveRiseSections.clear();
        mVisiblePlatforms.clear();
        mVisibleEnemies.clear();
        mVisibleItems.clear();
        
        Array<RiseSection> allRiseSections = rise.getRiseSections();
        
        for (RiseSection riseSection : allRiseSections) {
            if (riseSection.getEndY() > visibleAreaPosition &&
                    riseSection.getStartY() < visibleAreaPosition + GameArea.GAME_AREA_HEIGHT) {
                mActiveRiseSections.add(riseSection);
                
                Array<PlatformBase> allPlatforms = riseSection.getPlatforms();
                for (PlatformBase platform : allPlatforms) {
                    if (platform.isActive(visibleAreaPosition, VISIBLE_PLATFORMS_AREA_PADDING)) {
                        mVisiblePlatforms.add(platform);
                    }
                }
                
                Array<EnemyBase> allEnemies = riseSection.getEnemies();
                for (EnemyBase enemy : allEnemies) {
                    mVisibleEnemies.add(enemy);
                }
                
                Array<ItemBase> allItems = riseSection.getItems();
                for (ItemBase item : allItems) {
                    mVisibleItems.add(item);
                }
            }
        }
    }
    
    private void updatePlatforms(GameCharacter character, float delta) {
        Vector2 c1 = Pools.obtainVector();
        Vector2 c2 = Pools.obtainVector();
        mPlatformToCharCollisionData.reset();
        
        Vector2 charPosition = character.getPosition();
        
        c1.set(charPosition.x + GameCharacter.COLLISION_WIDTH_OFFSET - PlatformData.PLATFORM_WIDTH, charPosition.y);
        c2.set(charPosition.x + GameCharacter.COLLISION_LINE_LENGTH, charPosition.y);
        
        // only check for collision when character is going down
        mPlatformToCharCollisionData.isEnabled = character.getSpeed().y < 0.0f;
        
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<PlatformBase> platforms = riseSection.getPlatforms();
            for (PlatformBase platform : platforms) {
                platform.update(delta, c1, c2, mPlatformToCharCollisionData);
            }
        }
        
        Pools.freeVector(c1);
        Pools.freeVector(c2);
    }
    
    private void updateEnemies(GameCharacter character, float delta) {
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<EnemyBase> enemies = riseSection.getEnemies();
            for (EnemyBase enemy : enemies) {
                enemy.update(delta);
            }
        }
    }
    
    private void updateItems(GameCharacter character, float delta) {
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<ItemBase> items = riseSection.getItems();
            for (ItemBase item : items) {
                item.update(delta);
            }
        }
    }
    
    public Array<RiseSection> getActiveRiseSections() {
        return mActiveRiseSections;
    }
    
    public Array<PlatformBase> getVisiblePlatforms() {
        return mVisiblePlatforms;
    }
    
    public Array<EnemyBase> getVisibleEnemies() {
        return mVisibleEnemies;
    }
    
    public Array<ItemBase> getVisibleItems() {
        return mVisibleItems;
    }
    
    public PlatformToCharCollisionData getPlatformToCharCollisionData() {
        return mPlatformToCharCollisionData;
    }
    
}
