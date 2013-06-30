package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class BeansItem extends ItemBase {
    
    private static final int NUM_FARTS = 10;
    
    private final Rectangle mCollisionRect;
    
    public BeansItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_BEANS_TEXTURE, startStep, assetManager);
        
        mCollisionRect = new Rectangle();
        
        setPickedUpText("FARTING");
        
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
        return FART_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return NUM_FARTS;
    }
}
