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
