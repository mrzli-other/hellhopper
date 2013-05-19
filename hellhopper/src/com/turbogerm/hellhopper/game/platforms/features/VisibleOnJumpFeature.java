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
package com.turbogerm.hellhopper.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.hellhopper.dataaccess.PlatformFeatureData;
import com.turbogerm.hellhopper.game.CollisionEffects;

final class VisibleOnJumpFeature extends PlatformFeatureBase {
    
    private static final float VISIBILITY_DURATION = 1.2f;
    
    private float mVisibilityCountdown;
    
    public VisibleOnJumpFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        mVisibilityCountdown = 0.0f;
    }
    
    @Override
    public void update(float delta) {
        if (mVisibilityCountdown > 0.0f) {
            mVisibilityCountdown -= delta;
        }
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.isPlatformVisible = mVisibilityCountdown > 0.0f;
        modifier.spriteColor.set(1.0f, 1.0f, 1.0f, mVisibilityCountdown / VISIBILITY_DURATION);
    }
    
    public void applyEffect(int collisionEffect) {
        if (collisionEffect == CollisionEffects.VISIBLE_ON_JUMP) {
            mVisibilityCountdown = VISIBILITY_DURATION;
        }
    }
}
