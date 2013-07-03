package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.hellhopper.dataaccess.PlatformData;

public final class PlatformFactory {
    
    public static PlatformBase create(int riseSectionId, PlatformData platformData, int startStep,
            AssetManager assetManager) {
        
        String platformType = platformData.getPlatformType();
        if (PlatformData.NORMAL_TYPE.equals(platformType)) {
            return new NormalPlatform(riseSectionId, platformData, startStep, assetManager);
        } else if (PlatformData.CRUMBLE_TYPE.equals(platformType)) {
            return new CrumblePlatform(riseSectionId, platformData, startStep, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid platform type: %s", platformType);
            return null;
        }
    }
}
