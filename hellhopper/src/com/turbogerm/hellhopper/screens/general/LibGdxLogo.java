package com.turbogerm.hellhopper.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class LibGdxLogo {
    
    private static final float RIGHT_OFFSET_X = 5.0f;
    private static final float OFFSET_Y = 5.0f;
    
    private final Sprite mSprite;
    
    public LibGdxLogo(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.GUI_GENERAL_LIBGDX_LOGO_IMAGE_NAME);
        
        float offsetX = HellHopper.VIEWPORT_WIDTH - mSprite.getWidth() - RIGHT_OFFSET_X;
        mSprite.setPosition(offsetX, OFFSET_Y);
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
}
