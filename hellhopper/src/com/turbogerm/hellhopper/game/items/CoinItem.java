package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

public final class CoinItem extends ItemBase {
    
    private final Rectangle mCollisionRect;
    
    public CoinItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, getTexturePath(itemData), startStep, assetManager);
        
        float x = mSprite.getX();
        float y = mSprite.getY();
        float width = mSprite.getWidth();
        float height = mSprite.getHeight();
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private static String getTexturePath(ItemData itemData) {
        String coinType = itemData.getProperty(ItemData.COIN_TYPE_PROPERTY);
        if (ItemData.COIN_TYPE_COPPER_PROPERTY_VALUE.equals(coinType)) {
            return ResourceNames.ITEM_COIN_COPPER_TEXTURE;
        } else if (ItemData.COIN_TYPE_SILVER_PROPERTY_VALUE.equals(coinType)) {
            return ResourceNames.ITEM_COIN_SILVER_TEXTURE;
        } else {
            return ResourceNames.ITEM_COIN_GOLD_TEXTURE;
        }
    }
}
