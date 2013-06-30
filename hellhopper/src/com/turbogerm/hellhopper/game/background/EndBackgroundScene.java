package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.turbogerm.hellhopper.game.GameAreaUtils;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.util.GameUtils;

public final class EndBackgroundScene {
    
    private static final float GROUND_OFFSET_Y = -220.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_X = 60.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_Y = 250.0f * GameAreaUtils.PIXEL_TO_METER;
    
    private final int MIN_SHEEP = 2;
    private final int MAX_SHEEP = 4;
    
    private final Sprite mSkySprite;
    private final Sprite mSunSprite;
    private final Sprite mCloudsSprite;
    private final Sprite mMountainsSprite;
    private final Sprite mGroundSprite;
    
    private final Sheep[] mSheep;
    private int mNumSheep;
    
    private final float SUN_ROTATION_SPEED = 5.0f;
    
    public EndBackgroundScene(AssetManager assetManager) {
        
        Texture skyTexture = assetManager.get(ResourceNames.BACKGROUND_END_SKY_TEXTURE);
        mSkySprite = new Sprite(skyTexture);
        mSkySprite.setSize(
                skyTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                skyTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        Texture sunTexture = assetManager.get(ResourceNames.BACKGROUND_END_SUN_TEXTURE);
        mSunSprite = new Sprite(sunTexture);
        mSunSprite.setSize(
                sunTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                sunTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        GameUtils.setSpriteOriginCenter(mSunSprite);
        
        Texture cloudsTexture = assetManager.get(ResourceNames.BACKGROUND_END_CLOUDS_TEXTURE);
        mCloudsSprite = new Sprite(cloudsTexture);
        mCloudsSprite.setSize(
                cloudsTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                cloudsTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        Texture mountainsTexture = assetManager.get(ResourceNames.BACKGROUND_END_MOUNTAINS_TEXTURE);
        mMountainsSprite = new Sprite(mountainsTexture);
        mMountainsSprite.setSize(
                mountainsTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                mountainsTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        Texture groundTexture = assetManager.get(ResourceNames.BACKGROUND_END_GROUND_TEXTURE);
        mGroundSprite = new Sprite(groundTexture);
        mGroundSprite.setSize(
                groundTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                groundTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        mSheep = new Sheep[MAX_SHEEP];
        for (int i = 0; i < MAX_SHEEP; i++) {
            mSheep[i] = new Sheep(assetManager);
        }
    }
    
    public void reset(float riseHeight) {
        mSkySprite.setPosition(0.0f, riseHeight);
        mSunSprite.setPosition(SUN_OFFSET_X, riseHeight + SUN_OFFSET_Y);
        mCloudsSprite.setPosition(0.0f, riseHeight);
        mMountainsSprite.setPosition(0.0f, riseHeight);
        mGroundSprite.setPosition(0.0f, riseHeight + GROUND_OFFSET_Y);
        
        for (int i = 0; i < MAX_SHEEP; i++) {
            mSheep[i].reset(riseHeight);
        }
        
        mNumSheep = MathUtils.random(MIN_SHEEP, MAX_SHEEP);
    }
    
    public void update(float delta) {
        mSunSprite.rotate(SUN_ROTATION_SPEED * delta);
        
        for (int i = 0; i < mNumSheep; i++) {
            mSheep[i].update(delta);
        }
    }
    
    public void render(SpriteBatch batch) {
        mSkySprite.draw(batch);
        mSunSprite.draw(batch);
        mCloudsSprite.draw(batch);
        mMountainsSprite.draw(batch);
        
        for (int i = 0; i < mNumSheep; i++) {
            mSheep[i].render(batch);
        }
        
        mGroundSprite.draw(batch);
    }
}
