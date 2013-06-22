package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.util.ExceptionThrower;

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
        } else if (ItemData.RUBY_TYPE.equals(type)) {
            return new RubyItem(itemData, startStep, assetManager);
        } else if (ItemData.BLACK_BOX_TYPE.equals(type)) {
            return new BlackBoxItem(itemData, startStep, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid item type: %s", type);
            return null;
        }
    }
}
