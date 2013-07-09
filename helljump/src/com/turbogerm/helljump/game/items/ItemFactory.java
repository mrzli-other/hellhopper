package com.turbogerm.helljump.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.germlibrary.util.ExceptionThrower;
import com.turbogerm.helljump.dataaccess.ItemData;

public final class ItemFactory {
    
    public static ItemBase create(ItemData itemData, int startStep, AssetManager assetManager) {
        
        String type = itemData.getType();
        if (ItemData.BEANS_TYPE.equals(type)) {
            return new BeansItem(itemData, startStep, assetManager);
        } else if (ItemData.SHIELD_TYPE.equals(type)) {
            return new ShieldItem(itemData, startStep, assetManager);
        } else if (ItemData.JUMP_SUIT_TYPE.equals(type)) {
            return new JumpSuitItem(itemData, startStep, assetManager);
        } else if (ItemData.LIFE_TYPE.equals(type)) {
            return new LifeItem(itemData, startStep, assetManager);
        } else if (ItemData.COIN_TYPE.equals(type)) {
            return new CoinItem(itemData, startStep, assetManager);
        } else if (ItemData.SIGNET_TYPE.equals(type)) {
            return new SignetItem(itemData, startStep, assetManager);
        } else if (ItemData.BLACK_BOX_TYPE.equals(type)) {
            return new BlackBoxItem(itemData, startStep, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid item type: %s", type);
            return null;
        }
    }
}
