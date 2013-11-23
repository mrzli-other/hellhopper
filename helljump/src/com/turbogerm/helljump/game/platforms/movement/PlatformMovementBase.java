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
package com.turbogerm.helljump.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.dataaccess.PlatformData;
import com.turbogerm.helljump.game.CollisionEffects;
import com.turbogerm.helljump.game.platforms.features.PlatformModifier;
import com.turbogerm.helljump.resources.ResourceNames;

public abstract class PlatformMovementBase {
    
    protected static final Vector2 PLATFORM_CENTER_OFFSET;
    
    private static final float ENGINE_WIDTH = 0.4f;
    private static final float ENGINE_HEIGHT = 0.4f;
    private static final float ENGINE_X_OFFSET = (PlatformData.PLATFORM_WIDTH - ENGINE_WIDTH) / 2.0f;
    private static final float ENGINE_Y_OFFSET = 0.0f;
    
    private final Sprite mEngineSprite;
    private final ParticleEffect mEngineEffect;
    
    protected final Vector2 mPosition;
    
    static {
        PLATFORM_CENTER_OFFSET = new Vector2(PlatformData.PLATFORM_WIDTH / 2.0f, PlatformData.PLATFORM_HEIGHT / 2.0f);
    }
    
    public PlatformMovementBase(Vector2 initialPosition, String engineImageName, String particleName,
            AssetManager assetManager) {
        
        TextureAtlas platformsAtlas = assetManager.get(ResourceNames.PLATFORMS_ATLAS);
        mEngineSprite = platformsAtlas.createSprite(engineImageName);
        mEngineSprite.setSize(ENGINE_WIDTH, ENGINE_HEIGHT);
        
        mEngineEffect = new ParticleEffect((ParticleEffect) assetManager.get(particleName));
        
        mPosition = new Vector2(initialPosition);
    }
    
    public final void update(float delta) {
        mEngineEffect.update(delta);
        updateImpl(delta);
    }
    
    protected void updateImpl(float delta) {
    }
    
    public void render(SpriteBatch batch, float alpha) {
        mEngineSprite.setPosition(mPosition.x + ENGINE_X_OFFSET, mPosition.y + ENGINE_Y_OFFSET);
        mEngineSprite.draw(batch);
        
        GameUtils.setSpriteAlpha(mEngineSprite, alpha);
        
        mEngineEffect.setPosition(
                mEngineSprite.getX() + ENGINE_WIDTH / 2.0f, mEngineSprite.getY() + ENGINE_HEIGHT / 2.0f);
        mEngineEffect.draw(batch);
    }
    
    public void applyModifier(PlatformModifier modifier) {
    }
    
    public void applyContact(CollisionEffects collisionEffects) {
    }
    
    public void applyEffect(int collisionEffect) {
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }
    
    public abstract boolean hasVerticalMovement();
}
