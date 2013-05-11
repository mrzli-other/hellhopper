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
import com.turbogerm.hellhopper.game.CollisionEffect;
import com.turbogerm.hellhopper.game.GameCharacter;

final class JumpBoostPlatformFeature extends PlatformFeatureBase {
    
    private static final float CRATER_WIDTH = 0.5f;
    private static final float CRATER_HEIGHT = 0.2f;
    
    private static final float DISCHARGE_WIDTH = 0.75f;
    private static final float DISCHARGE_HEIGHT = 0.6f;
    
    private static final float LOW_POWER_MULTIPLIER = 1.3f;
    private static final float MEDIUM_POWER_MULTIPLIER = 1.6f;
    private static final float HIGH_POWER_MULTIPLIER = 1.8f;
    
    private static final float DISCHARGE_DURATION = 1.0f;
    private static final int NUM_DISCHARGE_SPRITES = 5;
    private static final float DISCHARGE_INTERVAL = 0.02f;
    
    private final Sprite mCraterSprite;
    private final Vector2 mCraterOffset;
    
    private final Sprite[] mDischargeSprites;
    private final Vector2 mDischargeInitialOffset;
    private float mDischargeElapsed;
    private boolean mIsDischarging;
    
    private final float mJumpBoostSpeed;
    
    public JumpBoostPlatformFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        Texture craterTexture = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_TEXTURE);
        mCraterSprite = new Sprite(craterTexture);
        mCraterSprite.setSize(CRATER_WIDTH, CRATER_HEIGHT);
        
        Texture dischargeTexture = assetManager.get(ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_TEXTURE);
        mDischargeSprites = new Sprite[NUM_DISCHARGE_SPRITES];
        for (int i = 0; i < NUM_DISCHARGE_SPRITES; i++) {
            mDischargeSprites[i] = new Sprite(dischargeTexture);
            mDischargeSprites[i].setSize(DISCHARGE_WIDTH, DISCHARGE_HEIGHT);
        }
        
        String powerString = featureData.getProperty(PlatformFeatureData.JUMP_BOOST_POWER_PROPERTY);
        mJumpBoostSpeed = getJumpBoostSpeed(powerString);
        
        float positionFraction = Float.parseFloat(featureData
                .getProperty(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY));
        mCraterOffset = new Vector2(
                (PlatformData.PLATFORM_WIDTH - CRATER_WIDTH) * positionFraction,
                PlatformData.PLATFORM_HEIGHT);
        
        mDischargeInitialOffset = new Vector2(
                mCraterOffset.x + (CRATER_WIDTH - DISCHARGE_WIDTH) / 2.0f,
                mCraterOffset.y + CRATER_HEIGHT - DISCHARGE_HEIGHT + 0.5f);
        
        mDischargeElapsed = DISCHARGE_DURATION;
        mIsDischarging = false;
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 platformPosition, float delta) {
        
        if (mIsDischarging) {
            mDischargeElapsed += delta;
            
            boolean isDischarging = false;
            for (int i = 0; i < NUM_DISCHARGE_SPRITES; i++) {
                if (displayDischarge(batch, platformPosition, i)) {
                    isDischarging = true;
                }
            }
            
            mIsDischarging = isDischarging;
        }
        
        mCraterSprite.setPosition(
                platformPosition.x + mCraterOffset.x,
                platformPosition.y + mCraterOffset.y);
        mCraterSprite.draw(batch);
    }
    
    @Override
    public boolean isContact(float relativeCollisionPointX) {
        float charX1 = relativeCollisionPointX + GameCharacter.COLLISION_WIDTH_OFFSET;
        float charX2 = charX1 + GameCharacter.COLLISION_WIDTH;
        
        float featureX1 = mCraterOffset.x;
        float featureX2 = featureX1 + CRATER_WIDTH;
        
        // http://eli.thegreenplace.net/2008/08/15/intersection-of-1d-segments/
        return charX2 >= featureX1 && featureX2 >= charX1;
    }
    
    @Override
    public void applyContact(CollisionEffect collisionEffect) {
        collisionEffect.set(CollisionEffect.JUMP_BOOST, mJumpBoostSpeed);
        startDischarge();
    }
    
    private void startDischarge() {
        mDischargeElapsed = 0.0f;
        mIsDischarging = true;
    }
    
    private boolean displayDischarge(SpriteBatch batch, Vector2 platformPosition, int index) {
        float currDischargeElapsed = mDischargeElapsed - index * DISCHARGE_INTERVAL;
        
        if (currDischargeElapsed < 0.0f) {
            return true;
        }
        
        if (currDischargeElapsed >= DISCHARGE_DURATION) {
            return false;
        }
        
        float dischargeOffsetY = mDischargeInitialOffset.y + currDischargeElapsed * mJumpBoostSpeed;
        float dischargeAlpha = 1.0f - currDischargeElapsed / DISCHARGE_DURATION; 
        
        mDischargeSprites[index].setPosition(
                platformPosition.x + mDischargeInitialOffset.x,
                platformPosition.y + dischargeOffsetY);
        mDischargeSprites[index].draw(batch, dischargeAlpha);
        
        return true;
    }
    
    private static float getJumpBoostSpeed(String powerString) {
        if (PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE.equals(powerString)) {
            return GameCharacter.JUMP_SPEED * LOW_POWER_MULTIPLIER;
        } else if (PlatformFeatureData.JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE.equals(powerString)) {
            return GameCharacter.JUMP_SPEED * MEDIUM_POWER_MULTIPLIER;
        } else {
            return GameCharacter.JUMP_SPEED * HIGH_POWER_MULTIPLIER;
        }
    }
}
