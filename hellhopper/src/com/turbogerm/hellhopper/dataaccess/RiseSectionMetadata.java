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

public final class RiseSectionMetadata extends RiseSectionDataBase {
    
    public static final String BASIC_GENERATOR_TYPE = "basic";
    public static final String JUMP_BOOST_GENERATOR_TYPE = "jumpboost";
    public static final String VISIBLE_ON_JUMP_GENERATOR_TYPE = "visibleonjump";
    public static final String CRUMBLE_GENERATOR_TYPE = "crumble";
    
    public static final String NORMAL_PLATFORM_WEIGHT_PROPERTY = "normalplatformweight";
    public static final String MOVING_PLATFORM_WEIGHT_PROPERTY = "movingplatformweight";
    public static final String REPOSITION_PLATFORM_WEIGHT_PROPERTY = "repositionplatformweight";
    public static final String VISIBLE_ON_JUMP_PLATFORM_WEIGHT_PROPERTY = "visibleonjumpplatformweight";
    public static final String MIN_MOVING_SPEED_PROPERTY = "minmovingspeed";
    public static final String MAX_MOVING_SPEED_PROPERTY = "maxmovingspeed";
    public static final String MIN_MOVING_RANGE_PROPERTY = "minmovingrange";
    public static final String MAX_MOVING_RANGE_PROPERTY = "maxmovingrange";
    public static final String MIN_REPOSITION_RANGE_PROPERTY = "minrepositionrange";
    public static final String MAX_REPOSITION_RANGE_PROPERTY = "maxrepositionrange";
    public static final String JUMP_BOOST_FRACTION_PROPERTY = "jumpboostfraction";
    public static final String JUMP_BOOST_LOW_WEIGHT_PROPERTY = "jumpboostlowweight";
    public static final String JUMP_BOOST_MEDIUM_WEIGHT_PROPERTY = "jumpboostmediumweight";
    public static final String JUMP_BOOST_HIGH_WEIGHT_PROPERTY = "jumpboosthighweight";
    public static final String CRUMBLE_PROPERTY = "crumble";
    public static final String CRUMBLE_FRACTION_PROPERTY = "crumblefraction";
    
    private final String mGeneratorType;
    private final int mMinStepRange;
    private final int mMaxStepRange;
    private final int mMinStepDistance;
    private final int mMaxStepDistance;
    private final ObjectMap<String, String> mProperties;
    
    public RiseSectionMetadata(String generatorType, String type, String name,
            int minStepRange, int maxStepRange, int minStepDistance,
            int maxStepDistance, int difficulty, ObjectMap<String, String> properties) {
        super(type, name, difficulty);
        
        mGeneratorType = generatorType;
        mMinStepRange = minStepRange;
        mMaxStepRange = maxStepRange;
        mMinStepDistance = minStepDistance;
        mMaxStepDistance = maxStepDistance;
        mProperties = properties;
    }
    
    @Override
    public boolean isMetadata() {
        return true;
    }
    
    public String getGeneratorType() {
        return mGeneratorType;
    }
    
    public int getMinStepRange() {
        return mMinStepRange;
    }
    
    public int getMaxStepRange() {
        return mMaxStepRange;
    }
    
    public int getMinStepDistance() {
        return mMinStepDistance;
    }
    
    public int getMaxStepDistance() {
        return mMaxStepDistance;
    }
    
    public String getProperty(String name) {
        return mProperties.get(name);
    }
}
