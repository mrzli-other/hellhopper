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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.resources.ResourceNames;

public final class CharacterEyesGraphicsFart extends CharacterGraphicsBase {
    
    private static final float OPENED_OFFSET_X = 0.15f;
    private static final float OPENED_OFFSET_Y = 0.66f;
    private static final float OPENED_WIDTH = 0.7f;
    private static final float OPENED_HEIGHT = 0.3f;
    
    private static final float CLOSED_OFFSET_X = 0.15f;
    private static final float CLOSED_OFFSET_Y = 0.74f;
    private static final float CLOSED_WIDTH = 0.7f;
    private static final float CLOSED_HEIGHT = 0.25f;
    
    private static final float CLOSED_DURATION = 0.3f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite mOpenedSprite;
    private final Sprite mClosedSprite;
    
    private float mClosedElapsed;
    
    static {
        DEFAULT_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    public CharacterEyesGraphicsFart(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        
        mOpenedSprite = atlas.createSprite(ResourceNames.CHARACTER_EYES_FART_OPENED_IMAGE_NAME);
        mOpenedSprite.setSize(OPENED_WIDTH, OPENED_HEIGHT);
        mOpenedSprite.setColor(DEFAULT_COLOR);
        
        mClosedSprite = atlas.createSprite(ResourceNames.CHARACTER_EYES_FART_CLOSED_IMAGE_NAME);
        mClosedSprite.setSize(CLOSED_WIDTH, CLOSED_HEIGHT);
        mClosedSprite.setColor(DEFAULT_COLOR);
    }
    
    @Override
    public void update(float delta) {
        if (mClosedElapsed < CLOSED_DURATION) {
            mClosedElapsed += delta;
        }
    }
    
    @Override
    public void reset() {
        mClosedElapsed = CLOSED_DURATION;
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        if (mClosedElapsed < CLOSED_DURATION) {
            mClosedSprite.setPosition(characterPosition.x + CLOSED_OFFSET_X, characterPosition.y + CLOSED_OFFSET_Y);
            mClosedSprite.draw(batch);
        } else {
            mOpenedSprite.setPosition(characterPosition.x + OPENED_OFFSET_X, characterPosition.y + OPENED_OFFSET_Y);
            mOpenedSprite.draw(batch);
        }
        
    }
    
    public void closeEyes() {
        mClosedElapsed = 0.0f;
    }
}
