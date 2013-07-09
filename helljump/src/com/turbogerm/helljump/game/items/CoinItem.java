package com.turbogerm.helljump.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.resources.ResourceNames;

public final class CoinItem extends ItemBase {
    
    private static final int COPPER_COIN_SCORE = 400;
    private static final int SILVER_COIN_SCORE = 1000;
    private static final int GOLD_COIN_SCORE = 2000;
    
    private static final float ROTATION_SPEED = 90.0f;
    
    private final int mCoinScore;
    
    private final Circle mCollisionCircle;
    
    public CoinItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, getImageName(itemData), startStep, assetManager);
        
        mCoinScore = getCoinScore(itemData);
        
        mCollisionCircle = new Circle();
        
        setPickedUpText(String.format("+%d PTS", mCoinScore));
        
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
    
    private static String getImageName(ItemData itemData) {
        String coinType = itemData.getProperty(ItemData.COIN_TYPE_PROPERTY);
        if (ItemData.COIN_TYPE_COPPER_PROPERTY_VALUE.equals(coinType)) {
            return ResourceNames.ITEM_COIN_COPPER_IMAGE_NAME;
        } else if (ItemData.COIN_TYPE_SILVER_PROPERTY_VALUE.equals(coinType)) {
            return ResourceNames.ITEM_COIN_SILVER_IMAGE_NAME;
        } else {
            return ResourceNames.ITEM_COIN_GOLD_IMAGE_NAME;
        }
    }
    
    private static int getCoinScore(ItemData itemData) {
        String coinType = itemData.getProperty(ItemData.COIN_TYPE_PROPERTY);
        if (ItemData.COIN_TYPE_COPPER_PROPERTY_VALUE.equals(coinType)) {
            return COPPER_COIN_SCORE;
        } else if (ItemData.COIN_TYPE_SILVER_PROPERTY_VALUE.equals(coinType)) {
            return SILVER_COIN_SCORE;
        } else {
            return GOLD_COIN_SCORE;
        }
    }
    
    @Override
    public int getEffect() {
        return SCORE_EFFECT;
    }
    
    @Override
    public Object getValue() {
        return mCoinScore;
    }
}
