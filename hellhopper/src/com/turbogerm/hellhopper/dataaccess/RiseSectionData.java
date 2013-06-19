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
