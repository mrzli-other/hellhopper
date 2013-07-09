package com.turbogerm.helljump.game.generator;

import java.util.Comparator;

import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.dataaccess.EnemyData;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.dataaccess.PlatformData;

public final class RiseGeneratorUtils {
    
    private static final Comparator<PlatformData> PLATFORM_DATA_COMPARATOR;
    private static final Comparator<EnemyData> ENEMY_DATA_COMPARATOR;
    private static final Comparator<ItemData> ITEM_DATA_COMPARATOR;
    
    static {
        PLATFORM_DATA_COMPARATOR = new Comparator<PlatformData>() {
            @Override
            public int compare(PlatformData p1, PlatformData p2) {
                if (p1.getStep() < p2.getStep()) {
                    return -1;
                } else if (p1.getStep() > p2.getStep()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        
        ENEMY_DATA_COMPARATOR = new Comparator<EnemyData>() {
            @Override
            public int compare(EnemyData e1, EnemyData e2) {
                if (e1.getStep() < e2.getStep()) {
                    return -1;
                } else if (e1.getStep() > e2.getStep()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        
        ITEM_DATA_COMPARATOR = new Comparator<ItemData>() {
            @Override
            public int compare(ItemData i1, ItemData i2) {
                if (i1.getStep() < i2.getStep()) {
                    return -1;
                } else if (i1.getStep() > i2.getStep()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }
    
    public static void sortPlatforms(Array<PlatformData> platformDataList) {
        platformDataList.sort(PLATFORM_DATA_COMPARATOR);
    }
    
    public static void sortEnemies(Array<EnemyData> enemyDataList) {
        enemyDataList.sort(ENEMY_DATA_COMPARATOR);
    }
    
    public static void sortItems(Array<ItemData> itemDataList) {
        itemDataList.sort(ITEM_DATA_COMPARATOR);
    }
}
