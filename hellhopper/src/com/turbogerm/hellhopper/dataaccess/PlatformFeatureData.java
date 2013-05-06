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

public final class PlatformFeatureData {
    
    public static final String JUMP_BOOST = "jumpboost";
    
    public static final String JUMP_BOOST_POSITION_PROPERTY = "position";
    public static final String JUMP_BOOST_POWER_PROPERTY = "power";
    
    public static final String JUMP_BOOST_POWER_LOW_PROPERTY_VALUE = "low";
    public static final String JUMP_BOOST_POWER_MEDIUM_PROPERTY_VALUE = "medium";
    public static final String JUMP_BOOST_POWER_HIGH_PROPERTY_VALUE = "high";
    
    private final String mFeatureType;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformFeatureData(String featureType, ObjectMap<String, String> properties) {
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
