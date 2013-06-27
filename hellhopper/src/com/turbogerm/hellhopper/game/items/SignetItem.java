package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

final class SignetItem extends ItemBase {
    
    private static final float ROTATION_SPEED = 60.0f;
    
    private final Circle mCollisionCircle;
    
    public SignetItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, ResourceNames.ITEM_SIGNET_TEXTURE, startStep, assetManager);
        
        mCollisionCircle = new Circle();
        
        setPickedUpText("");
        
        updatePositionImpl();
    }
    
    @Override
    protected void updateImpl(float delta) {
        mSprite.rotate(ROTATION_SPEED * delta);
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
        return SIGNET_EFFECT;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
