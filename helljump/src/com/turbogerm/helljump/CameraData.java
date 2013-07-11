package com.turbogerm.helljump;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.helljump.game.GameAreaUtils;

public final class CameraData {
    
    private final Rectangle mGuiCameraRect;
    private final Rectangle mNonOffsetedGameCameraRect;
    
    private final OrthographicCamera mGuiCamera;
    private final OrthographicCamera mGameAreaCamera;
    
    private float mInitialGameAreaCameraY;
    
    public CameraData(float screenWidth, float screenHeight) {
        mGuiCameraRect = new Rectangle();
        mNonOffsetedGameCameraRect = new Rectangle();
        
        mGuiCamera = new OrthographicCamera();
        mGameAreaCamera = new OrthographicCamera();
        
        resize(screenWidth, screenHeight);
    }
    
    public void resize(float screenWidth, float screenHeight) {
        float screenAspectRatio = screenWidth / screenHeight;
        float viewportAspectRatio = HellJump.VIEWPORT_WIDTH / HellJump.VIEWPORT_HEIGHT;
        if (screenAspectRatio <= viewportAspectRatio) {
            mGuiCameraRect.width = HellJump.VIEWPORT_WIDTH;
            mGuiCameraRect.height = mGuiCameraRect.width / screenAspectRatio;
            mGuiCameraRect.x = 0.0f;
            mGuiCameraRect.y = (HellJump.VIEWPORT_HEIGHT - mGuiCameraRect.height) / 2.0f; 
        } else {
            mGuiCameraRect.height = HellJump.VIEWPORT_HEIGHT;
            mGuiCameraRect.width = mGuiCameraRect.height * screenAspectRatio;
            mGuiCameraRect.x = (HellJump.VIEWPORT_WIDTH - mGuiCameraRect.width) / 2.0f;
            mGuiCameraRect.y = 0.0f;
        }
        
        mGuiCamera.setToOrtho(false, mGuiCameraRect.width, mGuiCameraRect.height);
        mGuiCamera.translate(mGuiCameraRect.x, mGuiCameraRect.y);
        mGuiCamera.update();
        
        mNonOffsetedGameCameraRect.x = mGuiCameraRect.x * GameAreaUtils.PIXEL_TO_METER;
        mNonOffsetedGameCameraRect.y = mGuiCameraRect.y * GameAreaUtils.PIXEL_TO_METER;
        mNonOffsetedGameCameraRect.width = mGuiCameraRect.width * GameAreaUtils.PIXEL_TO_METER;
        mNonOffsetedGameCameraRect.height = mGuiCameraRect.height * GameAreaUtils.PIXEL_TO_METER;
        
        mGameAreaCamera.setToOrtho(false, mNonOffsetedGameCameraRect.width, mNonOffsetedGameCameraRect.height);
        mGameAreaCamera.translate(mNonOffsetedGameCameraRect.x, mNonOffsetedGameCameraRect.y);
        mGameAreaCamera.update();
        
        mInitialGameAreaCameraY = mGameAreaCamera.position.y;
    }
    
    public void setGameAreaPosition(float gameAreaPosition) {
        mGameAreaCamera.position.y = mInitialGameAreaCameraY + gameAreaPosition;
        mGameAreaCamera.update();
    }
    
    public Rectangle getGuiCameraRect() {
        return mGuiCameraRect;
    }
    
    public Rectangle getNonOffsetedGameCameraRect() {
        return mNonOffsetedGameCameraRect;
    }
    
    public OrthographicCamera getGuiCamera() {
        return mGuiCamera;
    }
    
    public Matrix4 getGuiMatrix() {
        return mGuiCamera.combined;
    }
    
    public Matrix4 getGameAreaMatrix() {
        return mGameAreaCamera.combined;
    }
}
