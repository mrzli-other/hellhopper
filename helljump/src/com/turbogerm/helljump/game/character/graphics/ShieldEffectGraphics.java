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
package com.turbogerm.helljump.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public final class ShieldEffectGraphics extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = -0.125f;
    private static final float OFFSET_Y = -0.11f;
    private static final float WIDTH = 1.25f;
    private static final float HEIGHT = 1.8f;
    
    private static final float HIGH_ALPHA = 1.0f;
    private static final float LOW_ALPHA = 0.4f;
    private static final float MID_ALPHA = (HIGH_ALPHA + LOW_ALPHA) / 2.0f;
    private static final float ALPHA_HALF_RANGE = (HIGH_ALPHA - LOW_ALPHA) / 2.0f;
    private static final float ALPHA_CHANGE_PERIOD = 0.3f;
    
    private static final float SHIELD_EFFECT_FINISHING_DURATION = 3.0f;
    
    private final Sprite mSprite;
    
    private float mInternalAnimationTime;
    private float mShieldEffectRemaining;
    
    public ShieldEffectGraphics(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.CHARACTER_SHIELD_EFFECT_IMAGE_NAME);
        mSprite.setSize(WIDTH, HEIGHT);
    }
    
    @Override
    public void reset() {
        mInternalAnimationTime = 0.0f;
        mShieldEffectRemaining = 0.0f;
    }
    
    @Override
    public void update(float delta) {
        mInternalAnimationTime += delta;
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        if (mShieldEffectRemaining <= 0.0f) {
            return;
        }
        
        if (mShieldEffectRemaining <= SHIELD_EFFECT_FINISHING_DURATION) {
            float alpha = MID_ALPHA + ALPHA_HALF_RANGE *
                    MathUtils.sinDeg(mInternalAnimationTime / ALPHA_CHANGE_PERIOD * 360.0f);
            GameUtils.setSpriteAlpha(mSprite, alpha);
        } else if (mSprite.getColor().a != 1.0f) {
            GameUtils.setSpriteAlpha(mSprite, 1.0f);
        }
        
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
    
    public void setShieldEffectRemaining(float shieldEffectRemaining) {
        mShieldEffectRemaining = shieldEffectRemaining;
    }
}
