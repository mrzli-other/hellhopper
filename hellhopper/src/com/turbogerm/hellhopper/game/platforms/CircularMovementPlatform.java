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
package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.util.GameUtils;

final class CircularMovementPlatform extends PlatformBase {
    
    private static final Vector2 PLATFORM_CENTER_OFFSET;
    
    private static final float ENGINE_WIDTH = 16.0f;
    private static final float ENGINE_HEIGHT = 16.0f;
    private static final float ENGINE_X_OFFSET = (PlatformData.PLATFORM_WIDTH - ENGINE_WIDTH) / 2.0f;
    private static final float ENGINE_Y_OFFSET = 0.0f;
    
    // public static final String ENGINE_ATLAS_IMAGE_NAME = "enginecircular";
    // public static final float ENGINE_FRAME_DURATION = 0.05f;
    
    private final Sprite mEngineSprite;
    // private final Animation mEngineAnimation;
    // private float mEngineAnimationTime;
    
    private final ParticleEffect mEngineEffect;
    
    private final float mRadius;
    private final float mSpeed;
    private final Vector2 mPosition;
    
    private final float mAngleSpeed;
    private float mAngle;
    private final Vector2 mRotationCenter;
    
    static {
        PLATFORM_CENTER_OFFSET = new Vector2(PlatformData.PLATFORM_WIDTH / 2.0f, PlatformData.PLATFORM_HEIGHT / 2.0f);
    }
    
    public CircularMovementPlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData.getPlatformPositions(startStep), ResourceNames.getRandomPlatformNormalTexture(),
                assetManager, true);
        
        Texture engineTexture = assetManager.get(ResourceNames.PLATFORM_ENGINE_CIRCULAR_TEXTURE);
        mEngineSprite = new Sprite(engineTexture);
        mEngineSprite.setBounds(
                mInitialPosition.x + ENGINE_X_OFFSET, mInitialPosition.y + ENGINE_Y_OFFSET,
                ENGINE_WIDTH, ENGINE_HEIGHT);
        
        // TextureAtlas engineAtlas = assetManager.get(ResourceNames.PLATFORM_ENGINE_CIRCULAR_TEXTURE_ATLAS);
        // Array<AtlasRegion> engineAtlasRegions = engineAtlas.findRegions(ENGINE_ATLAS_IMAGE_NAME);
        // mEngineAnimation = new Animation(ENGINE_FRAME_DURATION, engineAtlasRegions, Animation.LOOP_PINGPONG);
        // mEngineAnimationTime = 0.0f;
        
        mEngineEffect = new ParticleEffect((ParticleEffect) assetManager.get(ResourceNames.PARTICLE_ENGINE));
        
        mRadius = Float.parseFloat(platformData.getProperty(PlatformData.CIRCULAR_MOVEMENT_RADIUS_PROPERTY));
        mSpeed = Float.parseFloat(platformData.getProperty(PlatformData.MOVEMENT_SPEED_PROPERTY));
        mPosition = new Vector2(mInitialPosition);
        mAngleSpeed = mSpeed / mRadius * MathUtils.radDeg;
        mAngle = 0.0f;
        mRotationCenter = new Vector2(
                mInitialPosition.x + PLATFORM_CENTER_OFFSET.x + mRadius,
                mInitialPosition.y + PLATFORM_CENTER_OFFSET.y);
    }
    
    @Override
    public void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        float travelledAngle = mAngleSpeed * delta;
        mAngle += travelledAngle;
        mAngle = GameUtils.getPositiveModulus(mAngle, 360.0f);
        
        mPosition.x = mRotationCenter.x + MathUtils.cosDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.x;
        mPosition.y = mRotationCenter.y + MathUtils.sinDeg(mAngle) * mRadius - PLATFORM_CENTER_OFFSET.y;
        
        // mEngineAnimationTime += delta;
    }
    
    @Override
    public void render(SpriteBatch batch, float delta) {
        super.render(batch, delta);
        
        mEngineSprite.setPosition(mPosition.x + ENGINE_X_OFFSET, mPosition.y + ENGINE_Y_OFFSET);
        mEngineSprite.draw(batch);
        
        // TextureRegion engineAnimationFrame = mEngineAnimation.getKeyFrame(mEngineAnimationTime);
        // batch.draw(engineAnimationFrame, mPosition.x + ENGINE_X_OFFSET, mPosition.y + ENGINE_Y_OFFSET,
        // ENGINE_WIDTH, ENGINE_HEIGHT);
        
        mEngineEffect.setPosition(
                mEngineSprite.getX() + ENGINE_WIDTH / 2.0f, mEngineSprite.getY() + ENGINE_HEIGHT / 2.0f);
        mEngineEffect.draw(batch, delta);
    }
    
    @Override
    public Vector2 getPosition() {
        return mPosition;
    }
}
