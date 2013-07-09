package com.turbogerm.helljump.game;

import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.game.enemies.EnemyBase;
import com.turbogerm.helljump.game.items.ItemBase;
import com.turbogerm.helljump.game.platforms.PlatformBase;

public final class RiseSection {
    
    private final int mId;
    private final String mName;
    private final int mDifficulty;
    
    private final float mStartY;
    private final float mEndY; 
    private final float mHeight;
    private final Array<PlatformBase> mPlatforms;
    private final Array<EnemyBase> mEnemies;
    private final Array<ItemBase> mItems;
    
    public RiseSection(int id, String name, int difficulty, float startY, float height,
            Array<PlatformBase> platforms, Array<EnemyBase> enemies, Array<ItemBase> items) {
        mId = id;
        mName = name;
        mDifficulty = difficulty;
        mStartY = startY;
        mHeight = height;
        mEndY = mStartY + mHeight;
        mPlatforms = platforms;
        mEnemies = enemies;
        mItems = items;
    }
    
    public void applyEffect(int collisionEffect) {
        for (PlatformBase platform : mPlatforms) {
            platform.applyEffect(collisionEffect);
        }
    }
    
    public int getId() {
        return mId;
    }
    
    public String getName() {
        return mName;
    }
    
    public int getDifficulty() {
        return mDifficulty;
    }
    
    public float getStartY() {
        return mStartY;
    }
    
    public float getEndY() {
        return mEndY;
    }
    
    public float getHeight() {
        return mHeight;
    }
    
    public Array<PlatformBase> getPlatforms() {
        return mPlatforms;
    }
    
    public Array<EnemyBase> getEnemies() {
        return mEnemies;
    }
    
    public Array<ItemBase> getItems() {
        return mItems;
    }
}
