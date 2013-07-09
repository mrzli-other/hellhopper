package com.turbogerm.helljump.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.dataaccess.PlatformData;
import com.turbogerm.helljump.dataaccess.PlatformFeatureData;
import com.turbogerm.helljump.game.CollisionEffects;
import com.turbogerm.helljump.game.character.GameCharacter;
import com.turbogerm.helljump.resources.ResourceNames;

final class JumpBoostPlatformFeature extends PlatformFeatureBase {
    
    private static final float RENDER_PRECEDENCE = 1.0f;
    private static final float CONTACT_PRECEDENCE = 1.0f;
    
    private static final float CRATER_LOW_WIDTH = 0.5f;
    private static final float CRATER_MEDIUM_WIDTH = 0.75f;
    private static final float CRATER_HIGH_WIDTH = 1.0f;
    private static final float CRATER_HEIGHT = 0.225f;
    private static final float CRATER_IN_PLATFORM_DEPTH = 0.025f;
    
    private static final float DISCHARGE_LOW_WIDTH = 0.6f;
    private static final float DISCHARGE_LOW_HEIGHT = 0.5f;
    private static final float DISCHARGE_MEDIUM_WIDTH = 0.9f;
    private static final float DISCHARGE_MEDIUM_HEIGHT = 0.75f;
    private static final float DISCHARGE_HIGH_WIDTH = 1.2f;
    private static final float DISCHARGE_HIGH_HEIGHT = 1.0f;
    
    private static final float LOW_POWER_MULTIPLIER = 1.3f;
    private static final float MEDIUM_POWER_MULTIPLIER = 1.6f;
    private static final float HIGH_POWER_MULTIPLIER = 1.9f;
    
    private static final float DISCHARGE_DURATION = 0.4f;
    
    private static final float LOW_POWER_SOUND_VOLUME = 0.33f;
    private static final float MEDIUM_POWER_SOUND_VOLUME = 0.66f;
    private static final float HIGH_POWER_SOUND_VOLUME = 1.0f;
    
    private final Sprite mCraterSprite;
    private final Vector2 mCraterOffset;
    
    private final Sprite mDischargeSprite;
    private final Vector2 mDischargeInitialOffset;
    private float mDischargeElapsed;
    
    private final float mCraterWidth;
    private final float mJumpBoostSpeed;
    private final float mSoundVolume;
    
    public JumpBoostPlatformFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        String powerString = featureData.getProperty(PlatformFeatureData.JUMP_BOOST_POWER_PROPERTY);
        JumpPowerData powerData = getJumpPowerData(powerString);
        
        mCraterWidth = powerData.craterWidth;
        
        TextureAtlas platformsAtlas = assetManager.get(ResourceNames.PLATFORMS_ATLAS);
        
        mCraterSprite = platformsAtlas.createSprite(powerData.craterImageName);
        mCraterSprite.setSize(mCraterWidth, CRATER_HEIGHT);
        
        mDischargeSprite = platformsAtlas.createSprite(powerData.dischargeImageName);
        mDischargeSprite.setSize(powerData.dischargeWidth, powerData.dischargeHeight);
        
        mJumpBoostSpeed = powerData.speed;
        mSoundVolume = powerData.soundVolume;
        
        float positionFraction = Float.parseFloat(
                featureData.getProperty(PlatformFeatureData.JUMP_BOOST_POSITION_PROPERTY));
        mCraterOffset = new Vector2(
                (PlatformData.PLATFORM_WIDTH - mCraterWidth) * positionFraction,
                PlatformData.PLATFORM_HEIGHT - CRATER_IN_PLATFORM_DEPTH);
        
        mDischargeInitialOffset = new Vector2(
                mCraterOffset.x + (mCraterWidth - powerData.dischargeWidth) / 2.0f,
                mCraterOffset.y + CRATER_HEIGHT);
        
        mDischargeElapsed = DISCHARGE_DURATION;
        
        mRenderPrecedence = RENDER_PRECEDENCE;
        mContactPrecendence = CONTACT_PRECEDENCE;
    }
    
    @Override
    public void update(float delta) {
        
        if (mDischargeElapsed < DISCHARGE_DURATION) {
            mDischargeElapsed += delta;
        }
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 platformPosition, Color color) {
        
        if (mDischargeElapsed < DISCHARGE_DURATION) {
            float dischargeAlpha = 1.0f - mDischargeElapsed / DISCHARGE_DURATION;
            
            mDischargeSprite.setPosition(
                    platformPosition.x + mDischargeInitialOffset.x,
                    platformPosition.y + mDischargeInitialOffset.y);
            mDischargeSprite.draw(batch, dischargeAlpha);
        }
        
        mCraterSprite.setPosition(
                platformPosition.x + mCraterOffset.x,
                platformPosition.y + mCraterOffset.y);
        mCraterSprite.setColor(color);
        mCraterSprite.draw(batch);
    }
    
    @Override
    public boolean isContact(float relativeCollisionPointX) {
        float charX1 = relativeCollisionPointX + GameCharacter.COLLISION_WIDTH_OFFSET;
        float charX2 = charX1 + GameCharacter.COLLISION_WIDTH;
        
        float featureX1 = mCraterOffset.x;
        float featureX2 = featureX1 + mCraterWidth;
        
        // http://eli.thegreenplace.net/2008/08/15/intersection-of-1d-segments/
        return charX2 >= featureX1 && featureX2 >= charX1;
    }
    
    @Override
    public void applyContact(CollisionEffects collisionEffects) {
        collisionEffects.set(CollisionEffects.JUMP_BOOST, CollisionEffects.JUMP_BOOST_SPEED_INDEX, mJumpBoostSpeed);
        collisionEffects.set(CollisionEffects.JUMP_BOOST, CollisionEffects.JUMP_BOOST_SOUND_VOLUME_INDEX,
                mSoundVolume);
        startDischarge();
    }
    
    private void startDischarge() {
        mDischargeElapsed = 0.0f;
    }
    
    private static JumpPowerData getJumpPowerData(String powerString) {
        
        if (PlatformFeatureData.JUMP_BOOST_POWER_LOW_PROPERTY_VALUE.equals(powerString)) {
            return new JumpPowerData(
                    ResourceNames.PLATFORM_JUMP_BOOST_CRATER_LOW_IMAGE_NAME,
                    ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_LOW_IMAGE_NAME,
                    CRATER_LOW_WIDTH,
                    DISCHARGE_LOW_WIDTH,
                    DISCHARGE_LOW_HEIGHT,
                    GameCharacter.JUMP_SPEED * LOW_POWER_MULTIPLIER,
                    LOW_POWER_SOUND_VOLUME);
        } else if (PlatformFeatureData.JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE.equals(powerString)) {
            return new JumpPowerData(
                    ResourceNames.PLATFORM_JUMP_BOOST_CRATER_MEDIUM_IMAGE_NAME,
                    ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_MEDIUM_IMAGE_NAME,
                    CRATER_MEDIUM_WIDTH,
                    DISCHARGE_MEDIUM_WIDTH,
                    DISCHARGE_MEDIUM_HEIGHT,
                    GameCharacter.JUMP_SPEED * MEDIUM_POWER_MULTIPLIER,
                    MEDIUM_POWER_SOUND_VOLUME);
        } else {
            return new JumpPowerData(
                    ResourceNames.PLATFORM_JUMP_BOOST_CRATER_HIGH_IMAGE_NAME,
                    ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_HIGH_IMAGE_NAME,
                    CRATER_HIGH_WIDTH,
                    DISCHARGE_HIGH_WIDTH,
                    DISCHARGE_HIGH_HEIGHT,
                    GameCharacter.JUMP_SPEED * HIGH_POWER_MULTIPLIER,
                    HIGH_POWER_SOUND_VOLUME);
        }
    }
    
    private static class JumpPowerData {
        final String craterImageName;
        final String dischargeImageName;
        final float craterWidth;
        final float dischargeWidth;
        final float dischargeHeight;
        final float speed;
        final float soundVolume;
        
        public JumpPowerData(
                String craterImageName,
                String dischargeImageName,
                float craterWidth,
                float dischargeWidth,
                float dischargeHeight,
                float speed,
                float soundVolume) {
            
            this.craterImageName = craterImageName;
            this.dischargeImageName = dischargeImageName;
            this.craterWidth = craterWidth;
            this.dischargeWidth = dischargeWidth;
            this.dischargeHeight = dischargeHeight;
            this.speed = speed;
            this.soundVolume = soundVolume;
        }
    }
}
