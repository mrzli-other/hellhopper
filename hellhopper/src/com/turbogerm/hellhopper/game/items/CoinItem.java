package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.ItemData;

public final class CoinItem extends ItemBase {
    
    private static final int COPPER_COIN_SCORE = 2000;
    private static final int SILVER_COIN_SCORE = 5000;
    private static final int GOLD_COIN_SCORE = 12000;
    
    private final int mCoinScore;
    
    private final Rectangle mCollisionRect;
    
    public CoinItem(ItemData itemData, int startStep, AssetManager assetManager) {
        super(itemData, getTexturePath(itemData), startStep, assetManager);
        
        mCoinScore = getCoinScore(itemData);
        
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
