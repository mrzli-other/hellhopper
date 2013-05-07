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
    
    private static final float CRATER_WIDTH = 0.5f;
    private static final float CRATER_HEIGHT = 0.2f;
    
    private static final float DISCHARGE_WIDTH = 0.2f;
    private static final float DISCHARGE_HEIGHT = 0.375f;
    
    private final Sprite mCraterSprite;
    private final Vector2 mCraterOffset;
    
    private final Sprite mDischargeSprite;
    private final Vector2 mDischargeOffset;
    
    public JumpBoostPlatformFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        Texture craterTexture = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_TEXTURE);
        mCraterSprite = new Sprite(craterTexture);
        mCraterSprite.setSize(CRATER_WIDTH, CRATER_HEIGHT);
        
        Texture dischargeTexture = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_TEXTURE);
        mDischargeSprite = new Sprite(dischargeTexture);
        mDischargeSprite.setSize(DISCHARGE_WIDTH, DISCHARGE_HEIGHT);
        
        float positionFraction = Float.parseFloat(featureData
                .getProperty(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY));
        mCraterOffset = new Vector2(
                (PlatformData.PLATFORM_WIDTH - CRATER_WIDTH) * positionFraction,
                PlatformData.PLATFORM_HEIGHT);
        
        mDischargeOffset = new Vector2(
                mCraterOffset.x + (CRATER_WIDTH - DISCHARGE_WIDTH) / 2.0f,
                mCraterOffset.y + CRATER_HEIGHT - DISCHARGE_HEIGHT + 0.5f);
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 platformPosition, float delta) {
        
        mCraterSprite.setPosition(
                platformPosition.x + mCraterOffset.x,
                platformPosition.y + mCraterOffset.y);
        mCraterSprite.draw(batch);
        
        mDischargeSprite.setPosition(
                platformPosition.x + mDischargeOffset.x,
                platformPosition.y + mDischargeOffset.y);
        mDischargeSprite.draw(batch);
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
