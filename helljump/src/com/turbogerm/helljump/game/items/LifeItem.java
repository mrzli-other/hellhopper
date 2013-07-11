package com.turbogerm.helljump.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.resources.ResourceNames;

final class LifeItem extends ItemBase {
    
    private final Rectangle mCollisionRect;
    
    public LifeItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_LIFE_IMAGE_NAME, startStep, assetManager);
        
        mCollisionRect = new Rectangle();
        
        setPickedUpText("+1 LIFE");
        
        updatePositionImpl();
    }
    
    @Override
    protected void updatePositionImpl() {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionRect.set(mPosition.x, mPosition.y, mSize.x, mSize.y);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    @Override
    public int getEffect() {
        return EXTRA_LIFE_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return null;
    }
}