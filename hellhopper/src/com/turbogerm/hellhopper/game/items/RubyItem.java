package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

final class RubyItem extends ItemBase {
    
    private final Rectangle mCollisionRect;
    
    public RubyItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_RUBY_TEXTURE, startStep, assetManager);
        
        mCollisionRect = new Rectangle();
        
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
        return RUBY_EFFECT;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
