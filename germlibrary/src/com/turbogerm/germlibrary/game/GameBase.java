package com.turbogerm.germlibrary.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

// based on Game class from libgdx
public abstract class GameBase implements ApplicationListener {
    
    private static final float MAX_DELTA = 0.1f;
    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_STEP = 1.0f / UPDATE_RATE;
    
    private static final float UPDATE_STEP_MAX = UPDATE_STEP * 1.1f;
    
    private Screen mScreen;
    
    private float mDeltaAccumulator;
    
    public GameBase() {
        mDeltaAccumulator = 0.0f;
    }
    
    @Override
    public void dispose() {
        if (mScreen != null) {
            mScreen.hide();
        }
    }
    
    @Override
    public void pause() {
        if (mScreen != null) {
            mScreen.pause();
        }
    }
    
    @Override
    public void resume() {
        if (mScreen != null) {
            mScreen.resume();
        }
    }
    
    @Override
    public void render() {
        if (mScreen != null) {
            
            // long javaHeap = Gdx.app.getJavaHeap();
            // long nativeHeap = Gdx.app.getNativeHeap();
            // Logger.debug("Java Heap: %d; Native Heap: %d", javaHeap, nativeHeap);
            
            Screen screen = mScreen;
            
            float delta = Math.min(Gdx.graphics.getDeltaTime(), MAX_DELTA);
            
            mDeltaAccumulator += delta;
            while (mDeltaAccumulator > 0.0f) {
                float deltaStep = mDeltaAccumulator <= UPDATE_STEP_MAX ? mDeltaAccumulator : UPDATE_STEP;
                screen.update(deltaStep);
                mDeltaAccumulator -= deltaStep;
            }
            
            screen.render();
        }
    }
    
    @Override
    public void resize(int width, int height) {
        if (mScreen != null) {
            mScreen.resize(width, height);
        }
    }
    
    public void setScreen(Screen screen) {
        if (this.mScreen != null)
            this.mScreen.hide();
        this.mScreen = screen;
        if (this.mScreen != null) {
            this.mScreen.show();
            this.mScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
    
    /** @return the currently active {@link Screen}. */
    public Screen getScreen() {
        return mScreen;
    }
}