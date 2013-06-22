package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.utils.Array;

public final class RiseSectionData extends RiseSectionDataBase {
    
    private final int mStepRange;
    private final Array<PlatformData> mPlatformsData;
    private final Array<EnemyData> mEnemiesData;
    private final Array<ItemData> mItemsData;
    
    public RiseSectionData(String type, String name, int stepRange, int difficulty,
            Array<PlatformData> platformsData, Array<EnemyData> enemiesData, Array<ItemData> itemsData) {
        super(type, name, difficulty);
        
        mStepRange = stepRange;
        mPlatformsData = platformsData;
        mEnemiesData = enemiesData;
        mItemsData = itemsData;
    }
    
    @Override
    public boolean isMetadata() {
        return false;
    }
    
    public int getStepRange() {
        return mStepRange;
    }
    
    public Array<PlatformData> getPlatformsData() {
        return mPlatformsData;
    }
    
    public Array<EnemyData> getEnemiesData() {
        return mEnemiesData;
    }
    
    public Array<ItemData> getItemsData() {
        return mItemsData;
    }
}
