/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.game.GameAreaUtils;

public abstract class ItemBase {
    
    protected final Sprite mSprite;
    
    public ItemBase(ItemData itemData, String texturePath, int startStep, AssetManager assetManager) {
        
        Texture texture = assetManager.get(texturePath);
        Vector2 initialPosition = itemData.getPosition(startStep);
        
        mSprite = new Sprite(texture);
        mSprite.setBounds(
                initialPosition.x, initialPosition.y,
                texture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                texture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        mSprite.setOrigin(mSprite.getWidth() / 2.0f, mSprite.getHeight() / 2.0f);
    }
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
}
