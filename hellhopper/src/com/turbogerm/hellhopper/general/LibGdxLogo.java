package com.turbogerm.hellhopper.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class LibGdxLogo {
    
    private static final float RIGHT_OFFSET_X = 5.0f;
    private static final float OFFSET_Y = 5.0f;
    
    private final Sprite mSprite;
    
    public LibGdxLogo(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.GENERAL_LIBGDX_LOGO_TEXTURE);
        mSprite = new Sprite(texture);
        
        float offsetX = HellHopper.VIEWPORT_WIDTH - mSprite.getWidth() - RIGHT_OFFSET_X;
        mSprite.setPosition(offsetX, OFFSET_Y);
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
}
