package com.turbogerm.hellhopper.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class TurboGermLogo {
    
    private static final float OFFSET_X = 5.0f;
    private static final float OFFSET_Y = 5.0f;
    
    private final Sprite mSprite;
    
    public TurboGermLogo(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.GENERAL_TURBO_GERM_LOGO_TEXTURE);
        mSprite = new Sprite(texture);
        mSprite.setPosition(OFFSET_X, OFFSET_Y);
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
}
