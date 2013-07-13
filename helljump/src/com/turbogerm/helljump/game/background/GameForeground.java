package com.turbogerm.helljump.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.helljump.CameraData;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.resources.ResourceNames;

public final class GameForeground {
    
    private static final int NUM_IMAGES = ResourceNames.BACKGROUND_FOREGROUND_SIDE_ELEMENT_IMAGE_COUNT;
    
    private final Rectangle mCameraRect;
    
    private final SideSprites mLeftSideSprites;
    private final SideSprites mRightSideSprites;
    
    private final float mSpriteHeight;
    
    private float mRiseHeight;
    
    public GameForeground(CameraData cameraData, AssetManager assetManager) {
        
        mCameraRect = cameraData.getNonOffsetedGameCameraRect();
        
        TextureAtlas atlas = assetManager.get(ResourceNames.BACKGROUND_ATLAS);
        AtlasRegion spriteRegion = atlas.findRegion(ResourceNames.getBackgroundForegroundSideElementImageName(0));
        float spriteWidth = spriteRegion.getRegionWidth() * GameAreaUtils.PIXEL_TO_METER;
        mSpriteHeight = spriteRegion.getRegionHeight() * GameAreaUtils.PIXEL_TO_METER;
        
        int numDisplayedSpritesPerSide = MathUtils.ceil(GameArea.GAME_AREA_HEIGHT / mSpriteHeight) + 1;
        
        mLeftSideSprites = new SideSprites(atlas, numDisplayedSpritesPerSide,
                spriteWidth, mSpriteHeight, true);
        mRightSideSprites = new SideSprites(atlas, numDisplayedSpritesPerSide,
                spriteWidth, mSpriteHeight, false);
    }
    
    public void reset(float riseHeight) {
        mRiseHeight = riseHeight;
        
        int numSprites = MathUtils.round(mRiseHeight / mSpriteHeight);
        mLeftSideSprites.reset(numSprites);
        mRightSideSprites.reset(numSprites);
    }
    
    public void render(SpriteBatch batch, float visibleAreaPosition) {
        
        if (mCameraRect.width <= GameArea.GAME_AREA_WIDTH) {
            return;
        }
        
        int firstDisplayedSpriteIndex = Math.max((int) (visibleAreaPosition / mSpriteHeight), 0);
        mLeftSideSprites.render(batch, firstDisplayedSpriteIndex);
        mRightSideSprites.render(batch, firstDisplayedSpriteIndex);
    }
    
    private static class SideSprites {
        private final Sprite[][] mSprites;
        private final int[] mNextAvailableIndexesForImage;
        
        private int[] mImageIndexes;
        
        private final int mNumDisplayedSprites;
        private final float mSpriteWidth;
        private final float mSpriteHeight;;
        
        public SideSprites(TextureAtlas atlas, int numDisplayedSprites,
                float spriteWidth, float spriteHeight,
                boolean isLeft) {
            
            mNumDisplayedSprites = numDisplayedSprites;
            mSpriteWidth = spriteWidth;
            mSpriteHeight = spriteHeight;
            
            float positionX = isLeft ? -mSpriteWidth : GameArea.GAME_AREA_WIDTH;
            
            mSprites = new Sprite[NUM_IMAGES][mNumDisplayedSprites];
            for (int i = 0; i < mSprites.length; i++) {
                for (int j = 0; j < mNumDisplayedSprites; j++) {
                    Sprite sprite = atlas.createSprite(
                            ResourceNames.getBackgroundForegroundSideElementImageName(i));
                    GameUtils.multiplySpriteSize(sprite, GameAreaUtils.PIXEL_TO_METER);
                    sprite.setX(positionX);
                    if (!isLeft) {
                        sprite.flip(true, false);
                    }
                    mSprites[i][j] = sprite;
                }
            }
            
            mNextAvailableIndexesForImage = new int[NUM_IMAGES];
        }
        
        public void reset(int numSprites) {
            mImageIndexes = GameUtils.getRandomIntegers(mSprites.length, numSprites);
        }
        
        public void render(SpriteBatch batch, int firstDisplayedSpriteIndex) {
            int lastDisplayedSpriteIndex = Math.min(
                    firstDisplayedSpriteIndex + mNumDisplayedSprites - 1, mImageIndexes.length - 1);
            
            float firstDisplayedSpriteY = firstDisplayedSpriteIndex * mSpriteHeight;
            resetNextAvailableIndexesForImage();
            
            for (int i = firstDisplayedSpriteIndex; i <= lastDisplayedSpriteIndex; i++) {
                int imageIndex = mImageIndexes[i];
                Sprite sprite = mSprites[imageIndex][mNextAvailableIndexesForImage[imageIndex]];
                mNextAvailableIndexesForImage[imageIndex]++;
                
                sprite.setY(firstDisplayedSpriteY + (i - firstDisplayedSpriteIndex) * mSpriteHeight);
                sprite.draw(batch);
            }
        }
        
        private void resetNextAvailableIndexesForImage() {
            for (int i = 0; i < mNextAvailableIndexesForImage.length; i++) {
                mNextAvailableIndexesForImage[i] = 0;
            }
        }
    }
}
