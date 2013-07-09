package com.turbogerm.helljump.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.resources.ResourceNames;

final class ShieldItem extends ItemBase {
    
    private static final float SHIELD_DURATION = 15.0f;
    
    private final Circle mCollisionCircle;
    
    public ShieldItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_SHIELD_IMAGE_NAME, startStep, assetManager);
        
        mCollisionCircle = new Circle();
        
        setPickedUpText("SHIELD");
        
        updatePositionImpl();
    }
    
    @Override
    protected void updatePositionImpl() {
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionCircle.set(mPosition.x + mRadius, mPosition.y + mRadius, mRadius);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapCircleRectangle(mCollisionCircle, rect);
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
