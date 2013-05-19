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
import com.turbogerm.hellhopper.util.ExceptionThrower;

public final class PlatformFeatureFactory {
    
public static PlatformFeatureBase create(PlatformFeatureData featureData, AssetManager assetManager) {
        
        String featureType = featureData.getFeatureType();
        if (PlatformFeatureData.JUMP_BOOST_FEATURE.equals(featureType)) {
            return new JumpBoostPlatformFeature(featureData, assetManager);
        } else if (PlatformFeatureData.FLAME_FEATURE.equals(featureType)) {
            return new FlamePlatformFeature(featureData, assetManager);
        } else if (PlatformFeatureData.VISIBLE_ON_JUMP_FEATURE.equals(featureType)) {
            return new VisibleOnJumpFeature(featureData, assetManager);
        } else {
            ExceptionThrower.throwException("Invalid platform feature type: %s", featureType);
            return null;
        }
    }
}
