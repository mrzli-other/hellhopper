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
package com.turbogerm.helljump.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.helljump.dataaccess.EnemyData;

public final class EnemyFactory {
    
    public static EnemyBase create(EnemyData enemyData, int startStep, AssetManager assetManager) {
        
        String enemyType = enemyData.getType();
        if (EnemyData.SAW_TYPE.equals(enemyType)) {
            return new SawEnemy(enemyData, startStep, assetManager);
        } else if (EnemyData.IMP_TYPE.equals(enemyType)) {
            return new ImpEnemy(enemyData, startStep, assetManager);
        } else if (EnemyData.LOCO_TYPE.equals(enemyType)) {
            return new LocoEnemy(enemyData, startStep, assetManager);
        } else if (EnemyData.KNIGHT_TYPE.equals(enemyType)) {
            return new KnightEnemy(enemyData, startStep, assetManager);
        } else if (EnemyData.EVIL_TWIN_TYPE.equals(enemyType)) {
            return new EvilTwinEnemy(enemyData, startStep, assetManager);
        } else if (EnemyData.COOL_CLERK_TYPE.equals(enemyType)) {
            return new CoolClerkEnemy(enemyData, startStep, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid enemy type: %s", enemyType);
            return null;
        }
    }
}
