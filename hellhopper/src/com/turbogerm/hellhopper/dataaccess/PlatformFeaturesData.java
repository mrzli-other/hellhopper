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
package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.utils.ObjectMap;

public final class PlatformFeaturesData {
    
    public static final String HORIZONTAL_MOVEMENT = "horizontalmovement";
    public static final String VERTICAL_MOVEMENT = "verticalmovement";
    public static final String CIRCULAR_MOVEMENT = "circularmovement";
    public static final String CRUMBLE = "crumble";
    public static final String FLAME = "flame";
    
    public static final String MOVEMENT_RANGE_PROPERTY = "range";
    public static final String CIRCULAR_MOVEMENT_RADIUS_PROPERTY = "radius";
    public static final String MOVEMENT_SPEED_PROPERTY = "speed";
    
    private final String mFeatureType;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformFeaturesData(String featureType, ObjectMap<String, String> properties) {
        mFeatureType = featureType;
        mProperties = properties;
    }
    
    public String getFeatureType() {
        return mFeatureType;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
}
