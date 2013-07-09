package com.turbogerm.helljump.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.helljump.dataaccess.PlatformData;

final class NormalPlatform extends PlatformBase {
    
    public NormalPlatform(int riseSectionId, PlatformData platformData, int startStep, AssetManager assetManager) {
        super(riseSectionId, platformData, startStep, assetManager);
    }
}
