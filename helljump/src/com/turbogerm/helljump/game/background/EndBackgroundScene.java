package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public final class EndBackgroundScene {
    
    private static final float GROUND_OFFSET_Y = -220.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_X = 60.0f * GameAreaUtils.PIXEL_TO_METER;
    private static final float SUN_OFFSET_Y = 250.0f * GameAreaUtils.PIXEL_TO_METER;
    
    private final int MIN_SHEEP = 2;
    private final int MAX_SHEEP = 4;
    
    private final float SUN_ROTATION_SPEED = 5.0f;
    
    private final Rectangle mCameraRect;
    
    private float mRiseHeight;
    
    private final Sprite mSkySprite;
    private final Sprite mSunSprite;
    private final Sprite mCloudsSprite;
    private final Sprite mMountainsSprite;
    private final Sprite mGroundSprite;
    
    private final Sheep[] mSheep;
    private int mNumSheep;
    
    public EndBackgroundScene(CameraData cameraData, AssetManager assetManager) {
        
        mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        
        TextureAtlas atlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        
        mSkySprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SKY_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSkySprite, GameAreaUtils.PIXEL_TO_METER);
        
        mSunSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_SUN_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mSunSprite, GameAreaUtils.PIXEL_TO_METER);
        GameUtils.setSpriteOriginCenter(mSunSprite);
        
        mCloudsSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_CLOUDS_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mCloudsSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mMountainsSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_MOUNTAINS_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mMountainsSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mGroundSprite = atlas.createSprite(ResourceNames.BACKGROUND_END_GROUND_IMAGE_NAME);
        GameUtils.multiplySpriteSize(mGroundSprite, GameAreaUtils.PIXEL_TO_METER);
        
        mSheep = new Sheep[MAX_SHEEP];
        for (int i = 0; i < MAX_SHEEP; i++) {
            mSheep[i] = new Sheep(assetManager);
        }
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
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
        
        setWideSpritePosition(mSkySprite);
        mSunSprite.setPosition(SUN_OFFSET_X, mRiseHeight + SUN_OFFSET_Y);
        mCloudsSprite.setPosition(0.0f, mRiseHeight);
        mMountainsSprite.setPosition(0.0f, mRiseHeight);
        mGroundSprite.setPosition(0.0f, mRiseHeight + GROUND_OFFSET_Y);
        
        mSkySprite.draw(batch);
        mSunSprite.draw(batch);
        mCloudsSprite.draw(batch);
        mMountainsSprite.draw(batch);
        
        for (int i = 0; i < mNumSheep; i++) {
            mSheep[i].render(batch);
        }
        
        mGroundSprite.draw(batch);
    }
    
    private void setWideSpritePosition(Sprite sprite) {
        float spriteX = mCameraRect.x + (mCameraRect.width - sprite.getWidth()) / 2.0f;
        float spriteY = mCameraRect.y +
                (mCameraRect.height - sprite.getHeight()) / 2.0f + mRiseHeight;
        
        sprite.setPosition(spriteX, spriteY);
    }
}
