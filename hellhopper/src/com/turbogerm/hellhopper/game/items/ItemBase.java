package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.game.GameAreaUtils;

public abstract class ItemBase {
    
    protected final Sprite mSprite;
    
    public ItemBase(ItemData itemData, String texturePath, int startStep, AssetManager assetManager) {
        
        Texture texture = assetManager.get(texturePath);
        Vector2 initialPosition = itemData.getPosition(startStep);
        
        mSprite = new Sprite(texture);
        mSprite.setBounds(
                initialPosition.x, initialPosition.y,
                texture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                texture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        mSprite.setOrigin(mSprite.getWidth() / 2.0f, mSprite.getHeight() / 2.0f);
    }
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
}
