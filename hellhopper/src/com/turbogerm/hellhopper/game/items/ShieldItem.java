package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

public final class ShieldItem extends ItemBase {
    
    private static final float SHIELD_DURATION = 15.0f;
    
    private final Rectangle mCollisionRect;
    
    public ShieldItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_SHIELD_TEXTURE, startStep, assetManager);
        
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
    
    @Override
    public int getEffect() {
        return SHIELD_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return SHIELD_DURATION;
    }
}
