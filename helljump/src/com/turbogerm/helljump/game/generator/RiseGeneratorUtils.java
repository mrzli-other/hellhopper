/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
