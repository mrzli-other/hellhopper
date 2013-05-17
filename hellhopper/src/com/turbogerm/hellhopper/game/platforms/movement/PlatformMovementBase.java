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
package com.turbogerm.hellhopper.game.platforms.movement;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.util.GameUtils;

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
    
    public PlatformMovementBase(Vector2 initialPosition, String engineTextureName, String particleName,
            AssetManager assetManager) {
        
        Texture engineTexture = assetManager.get(engineTextureName);
        mEngineSprite = new Sprite(engineTexture);
        mEngineSprite.setSize(ENGINE_WIDTH, ENGINE_HEIGHT);
        
        // TextureAtlas engineAtlas = assetManager.get(ResourceNames.PLATFORM_ENGINE_CIRCULAR_TEXTURE_ATLAS);
        // Array<AtlasRegion> engineAtlasRegions = engineAtlas.findRegions(ENGINE_ATLAS_IMAGE_NAME);
        // mEngineAnimation = new Animation(ENGINE_FRAME_DURATION, engineAtlasRegions, Animation.LOOP_PINGPONG);
        // mEngineAnimationTime = 0.0f;
        
        mEngineEffect = new ParticleEffect((ParticleEffect) assetManager.get(particleName));
        
        mPosition = new Vector2(initialPosition);
    }
    
    public void updatePosition(float delta) {
        // mEngineAnimationTime += delta;
    }
    
    public void render(SpriteBatch batch, float alpha, float delta) {
        mEngineSprite.setPosition(mPosition.x + ENGINE_X_OFFSET, mPosition.y + ENGINE_Y_OFFSET);
        mEngineSprite.draw(batch);
        
        GameUtils.setSpriteAlpha(mEngineSprite, alpha);
        
        // TextureRegion engineAnimationFrame = mEngineAnimation.getKeyFrame(mEngineAnimationTime);
        // batch.draw(engineAnimationFrame, mPosition.x + ENGINE_X_OFFSET, mPosition.y + ENGINE_Y_OFFSET,
        // ENGINE_WIDTH, ENGINE_HEIGHT);
        
        mEngineEffect.setPosition(
                mEngineSprite.getX() + ENGINE_WIDTH / 2.0f, mEngineSprite.getY() + ENGINE_HEIGHT / 2.0f);
        mEngineEffect.draw(batch, delta);
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }
    
    public abstract boolean hasVerticalMovement();
}
