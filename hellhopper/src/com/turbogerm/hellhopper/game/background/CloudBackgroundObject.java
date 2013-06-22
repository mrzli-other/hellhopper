package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.hellhopper.ResourceNames;

final class CloudBackgroundObject extends BackgroundObjectBase {
    
    public CloudBackgroundObject(AssetManager assetManager) {
        super(ResourceNames.getRandomBackgroundCloudTexture(), assetManager);
    }
}
