/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
