package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.hellhopper.ResourceNames;

final class RockBackgroundObject extends BackgroundObjectBase {
    public RockBackgroundObject(AssetManager assetManager) {
        super(ResourceNames.getRandomBackgroundRockTexture(), assetManager);
    }
}
