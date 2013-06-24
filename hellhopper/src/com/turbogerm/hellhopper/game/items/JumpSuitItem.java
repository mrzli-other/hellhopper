package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

public final class JumpSuitItem extends ItemBase {
    
    private static final float HIGH_JUMP_DURATION = 12.0f;
    
    private final Rectangle mCollisionRect;
    
    public JumpSuitItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_JUMP_SUIT_TEXTURE, startStep, assetManager);
        
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
        return HIGH_JUMP_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return HIGH_JUMP_DURATION;
    }
}
