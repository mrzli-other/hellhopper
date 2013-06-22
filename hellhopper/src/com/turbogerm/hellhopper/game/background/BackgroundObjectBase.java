package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.turbogerm.hellhopper.game.GameAreaUtils;

abstract class BackgroundObjectBase {
    
    private final Sprite mSprite;
    
    public BackgroundObjectBase(String texturePath, AssetManager assetManager) {
        
        Texture texture = assetManager.get(texturePath);
        mSprite = new Sprite(texture);
        mSprite.setSize(texture.getWidth() * GameAreaUtils.PIXEL_TO_METER * BackgroundLayer.SPRITE_SCALE_MULTIPLIER,
                texture.getHeight() * GameAreaUtils.PIXEL_TO_METER * BackgroundLayer.SPRITE_SCALE_MULTIPLIER);
    }
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
}
