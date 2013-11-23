/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
