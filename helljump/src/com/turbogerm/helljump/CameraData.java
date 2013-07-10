package com.turbogerm.helljump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.game.GameAreaUtils;

public final class CameraData {
    
    private final Rectangle mViewport;
    
    private final OrthographicCamera mGuiCamera;
    private final OrthographicCamera mGameAreaCamera;
    
    private final float mInitialGameAreaCameraY;
    
    public CameraData() {
        mViewport = new Rectangle();
        
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float screenAspectRatio = screenWidth / screenHeight;
        float viewportAspectRatio = HellJump.VIEWPORT_WIDTH / HellJump.VIEWPORT_HEIGHT;
        if (screenAspectRatio <= viewportAspectRatio) {
            mViewport.width = HellJump.VIEWPORT_WIDTH;
            mViewport.height = mViewport.width / screenAspectRatio;
            mViewport.x = 0.0f;
            mViewport.y = (HellJump.VIEWPORT_HEIGHT - mViewport.height) / 2.0f; 
        } else {
            mViewport.height = HellJump.VIEWPORT_HEIGHT;
            mViewport.width = mViewport.height * screenAspectRatio;
            mViewport.x = (HellJump.VIEWPORT_WIDTH - mViewport.width) / 2.0f;
            mViewport.y = 0.0f;
        }
        
        mGuiCamera = new OrthographicCamera();
        mGuiCamera.setToOrtho(false, mViewport.width, mViewport.height);
        mGuiCamera.translate(mViewport.x, mViewport.y);
        mGuiCamera.update();
        
        mGameAreaCamera = new OrthographicCamera();
        mGameAreaCamera.setToOrtho(
                false,
                mViewport.width * GameAreaUtils.PIXEL_TO_METER,
                mViewport.height * GameAreaUtils.PIXEL_TO_METER);
        mGameAreaCamera.translate(
                mViewport.x * GameAreaUtils.PIXEL_TO_METER,
                mViewport.y * GameAreaUtils.PIXEL_TO_METER);
        mGameAreaCamera.update();
        
        mInitialGameAreaCameraY = mGameAreaCamera.position.y;
    }
    
    public void setGameAreaPosition(float gameAreaPosition) {
        mGameAreaCamera.position.y = mInitialGameAreaCameraY + gameAreaPosition;
        mGameAreaCamera.update();
    }
    
    public Rectangle getViewport() {
        return mViewport;
    }
    
    public Matrix4 getGuiMatrix() {
        return mGuiCamera.combined;
    }
    
    public Matrix4 getGameAreaMatrix() {
        return mGameAreaCamera.combined;
    }
}
