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
package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;

public final class ShieldEffect {
    
    private static final float OFFSET_X = -0.125f;
    private static final float OFFSET_Y = -0.11f;
    private static final float WIDTH = 1.25f;
    private static final float HEIGHT = 1.8f;
    
    private static final float HIGH_ALPHA = 1.0f;
    private static final float LOW_ALPHA = 0.4f;
    private static final float MID_ALPHA = (HIGH_ALPHA + LOW_ALPHA) / 2.0f;
    private static final float ALPHA_HALF_RANGE = (HIGH_ALPHA - LOW_ALPHA) / 2.0f;
    private static final float ALPHA_CHANGE_PERIOD = 0.5f;
    
    private final Sprite mSprite;
    
    private float mTime;
    
    public ShieldEffect(AssetManager assetManager) {
        Texture shieldEffectTexture = assetManager.get(ResourceNames.CHARACTER_SHIELD_EFFECT_TEXTURE);
        mSprite = new Sprite(shieldEffectTexture);
        mSprite.setSize(WIDTH, HEIGHT);
        
        mTime = 0.0f;
    }
    
    public void update(float delta) {
        mTime += delta;
    }
    
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        float alpha = MID_ALPHA + ALPHA_HALF_RANGE * MathUtils.sinDeg(mTime / ALPHA_CHANGE_PERIOD * 360.0f);
        
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch, alpha);
    }
}
