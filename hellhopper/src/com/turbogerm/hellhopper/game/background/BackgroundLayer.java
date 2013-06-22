package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.game.GameArea;

final class BackgroundLayer {
    
    static final float SPRITE_SCALE_MULTIPLIER = 2.0f;
    
    private final float mScale;
    
    private final float mLayerDisplayX;
    private final float mLayerDisplayWidth;
    private final float mLayerDisplayHeight;
    
    private float mLayerDisplayY;
    
    //private float mRiseHeight;
    
    private final Array<BackgroundObjectBase> mBackgroundObjects;
    
    public BackgroundLayer(AssetManager assetManager, float distance, int numClouds, int numRocks) {
        mScale = 1.0f / distance / SPRITE_SCALE_MULTIPLIER;
        
        mLayerDisplayWidth = GameArea.GAME_AREA_WIDTH / mScale;
        mLayerDisplayHeight = GameArea.GAME_AREA_HEIGHT / mScale;
        mLayerDisplayX = (GameArea.GAME_AREA_WIDTH - mLayerDisplayWidth) / 2.0f;
        mLayerDisplayY = 0.0f;
        
        mBackgroundObjects = new Array<BackgroundObjectBase>(true, numClouds + numRocks);
        for (int i = 0; i < numClouds; i++) {
            mBackgroundObjects.add(new CloudBackgroundObject(assetManager));
        }
        for (int i = 0; i < numRocks; i++) {
            mBackgroundObjects.add(new RockBackgroundObject(assetManager));
        }
    }
    
    public void reset(float riseHeight) {
        //mRiseHeight = riseHeight;
    }
    
    public void update(float visibleAreaPosition, float delta) {
        mLayerDisplayY = visibleAreaPosition;// * mScale;
    }
    
    public void render(SpriteBatch batch) {
        batch.getProjectionMatrix().setToOrtho2D(
                mLayerDisplayX, mLayerDisplayY, mLayerDisplayWidth, mLayerDisplayHeight);
        batch.begin();
        
        for (BackgroundObjectBase backgroundObject : mBackgroundObjects) {
            backgroundObject.render(batch);
        }
        
        batch.end();
    }
}
