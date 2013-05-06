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
package com.turbogerm.hellhopper.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.dataaccess.PlatformFeatureData;

final class JumpBoostPlatformFeature extends PlatformFeatureBase {
    
    private static final float JUMP_BOOST_WIDTH = 0.5f;
    
    // private static final String JUMP_BOOST_IMAGE_NAME = "jumpboostlow";
    // private static final float JUMP_BOOST_FRAME_DURATION = 1.0f / 60.0f;
    private static final float JUMP_BOOST_FRAME_WIDTH = 1.25f;
    // private static final float JUMP_BOOST_FRAME_HEIGHT = 1.0f;
    
    // private final Animation mJumpBoostAnimation;
    // private float mJumpBoostAnimationTime = 0.0f;
    
    private final Sprite mJumpBoostCraterSprite;
    
    private final float mPositionOffset;
    
    public JumpBoostPlatformFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        float positionFraction = Float.parseFloat(featureData
                .getProperty(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY));
        mPositionOffset = (PlatformData.PLATFORM_WIDTH - JUMP_BOOST_WIDTH) * positionFraction -
                (JUMP_BOOST_FRAME_WIDTH - JUMP_BOOST_WIDTH) / 2.0f;
        
        // String powerString = featureData.getProperty(PlatformFeatureData.JUMP_BOOST_POWER_PROPERTY);
        
        // TextureAtlas jumpBoostAtlas = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_TEXTURE_ATLAS);
        // Array<AtlasRegion> engineAtlasRegions = jumpBoostAtlas.findRegions(JUMP_BOOST_IMAGE_NAME);
        // mJumpBoostAnimation = new Animation(JUMP_BOOST_FRAME_DURATION, engineAtlasRegions, Animation.LOOP);
        // mJumpBoostAnimationTime = 0.0f;
        
        Texture jumpBoostCraterTexture = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_TEXTURE);
        mJumpBoostCraterSprite = new Sprite(jumpBoostCraterTexture);
        mJumpBoostCraterSprite.setSize(0.5f, 0.2f);
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 platformPosition, float delta) {
        
        // mJumpBoostAnimationTime += delta;
        
        // TextureRegion engineAnimationFrame = mJumpBoostAnimation.getKeyFrame(mJumpBoostAnimationTime);
        // batch.draw(engineAnimationFrame,
        // platformPosition.x + mPositionOffset,
        // platformPosition.y + 0.0f, // PlatformData.PLATFORM_HEIGHT,
        // JUMP_BOOST_FRAME_WIDTH, JUMP_BOOST_FRAME_HEIGHT);
        
        mJumpBoostCraterSprite.setPosition(
                platformPosition.x + mPositionOffset + 0.25f,
                platformPosition.y + PlatformData.PLATFORM_HEIGHT);
        mJumpBoostCraterSprite.draw(batch);
    }
    
    private static String getParticlePath(String powerString) {
        if (PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE.equals(powerString)) {
            return ResourceNames.PARTICLE_JUMP_BOOST_LOW;
        } else if (PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE.equals(powerString)) {
            return ResourceNames.PARTICLE_JUMP_BOOST_MEDIUM;
        } else {
            return ResourceNames.PARTICLE_JUMP_BOOST_HIGH;
        }
    }
}
