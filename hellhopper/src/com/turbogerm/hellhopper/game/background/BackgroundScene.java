package com.turbogerm.hellhopper.game.background;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public final class BackgroundScene {
    
    private static final int LAYERS_CAPACITY = 10;
    
    private final SpriteBatch mBatch;
    
    private final Array<BackgroundLayer> mLayers;
    
    public BackgroundScene(AssetManager assetManager) {
        
        mBatch = new SpriteBatch();
        
        mLayers = new Array<BackgroundLayer>(true, LAYERS_CAPACITY);
        //mLayers.add(new BackgroundLayer(assetManager, 16.0f, 5, 5));
        //mLayers.add(new BackgroundLayer(assetManager, 4.0f, 5, 5));
        //mLayers.add(new BackgroundLayer(assetManager, 1.0f, 5, 5));
    }
    
    public void reset(float riseHeight) {
        for (BackgroundLayer layer : mLayers) {
            layer.reset(riseHeight);
        }
    }
    
    public void update(float visibleAreaPosition, float delta) {
        for (BackgroundLayer layer : mLayers) {
            layer.update(visibleAreaPosition, delta);
        }
    }
    
    public void render() {
        for (BackgroundLayer layer : mLayers) {
            layer.render(mBatch);
        }
    }
}
