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
package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.utils.ObjectMap;

public final class PlatformMovementData {
    
    public static final String HORIZONTAL_MOVEMENT = "horizontal";
    public static final String VERTICAL_MOVEMENT = "vertical";
    public static final String CIRCULAR_MOVEMENT = "circular";
    public static final String REPOSITION_MOVEMENT = "reposition";
    
    public static final String SPEED_PROPERTY = "speed";
    
    public static final String RANGE_PROPERTY = "range";
    public static final String INITIAL_OFFSET_PROPERTY = "initialoffset";
    public static final String RADIUS_PROPERTY = "radius";
    public static final String INITIAL_DEGREES_PROPERTY = "initialdegrees";
    
    public static final String DIRECTION_PROPERTY = "direction";
    public static final String DIRECTION_CCW_PROPERTY_VALUE = "ccw";
    public static final String DIRECTION_CW_PROPERTY_VALUE = "cw";
    
    public static final String REPOSITION_TYPE_PROPERTY = "repositiontype";
    public static final String REPOSITION_TYPE_RANDOM_PROPERTY_VALUE = "random";
    public static final String REPOSITION_TYPE_EDGE_PROPERTY_VALUE = "edge";
    
    private final String mMovementType;
    private final ObjectMap<String, String> mProperties;
    
    public PlatformMovementData(String movementType, ObjectMap<String, String> properties) {
        mMovementType = movementType;
        mProperties = properties;
    }
    
    public String getMovementType() {
        return mMovementType;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
}
