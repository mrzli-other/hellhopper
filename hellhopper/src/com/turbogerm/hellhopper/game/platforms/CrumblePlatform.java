/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

public final class CrumblePlatform extends PlatformBase {
    
    private static final float CRUMBLING_COUNTDOWN_DURATION = 1.0f;
    
    private boolean mIsCrumbling;
    private float mCrumblingCountdown;
    
    public CrumblePlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData.getPlatformPositions(startStep), ResourceNames.PLATFORM_CRUMBLE_TEXTURE,
                assetManager, false);
        
        mIsCrumbling = false;
        mCrumblingCountdown = CRUMBLING_COUNTDOWN_DURATION;
    }
    
    @Override
    public void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        if (mCrumblingCountdown <= 0.0f) {
            return;
        }
        
        if (mIsCrumbling) {
            mCrumblingCountdown -= delta;
        }
    }
    
    @Override
    public void render(SpriteBatch batch, float delta) {
        if (mCrumblingCountdown <= 0.0f) {
            return;
        }
        
        if (mIsCrumbling) {
            float alpha = mCrumblingCountdown / CRUMBLING_COUNTDOWN_DURATION;
            mSprite.draw(batch, alpha);
        } else {
            mSprite.draw(batch);
        }
    }
    
    @Override
    public boolean isCollision(Vector2 c1, Vector2 c2, Vector2 intersection) {
        
        if (!mIsCrumbling) {
            boolean isCollision = super.isCollision(c1, c2, intersection);
            if (isCollision) {
                mIsCrumbling = true;
            }
            
            return isCollision;
        } else {
            return false;
        }
        
    }
}