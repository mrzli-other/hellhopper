package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.hellhopper.dataaccess.EnemyData;

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
