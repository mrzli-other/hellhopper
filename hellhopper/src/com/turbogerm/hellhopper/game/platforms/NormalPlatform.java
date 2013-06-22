package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.hellhopper.dataaccess.PlatformData;

final class NormalPlatform extends PlatformBase {
    
    public NormalPlatform(int riseSectionId, PlatformData platformData, int startStep, AssetManager assetManager) {
        super(riseSectionId, platformData, startStep, assetManager);
    }
}
