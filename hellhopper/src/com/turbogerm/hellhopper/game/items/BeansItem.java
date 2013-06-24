package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

public final class BeansItem extends ItemBase {
    
    public static final int NUM_FARTS = 20;
    
    private final Rectangle mCollisionRect;
    
    public BeansItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_BEANS_TEXTURE, startStep, assetManager);
        
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
        return FART_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return NUM_FARTS;
    }
}
