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
package com.turbogerm.hellhopper.game;

public final class CollisionEffects {
    
    public static final int JUMP_BOOST = 0;
    public static final int BURN = 1;
    public static final int REPOSITION_PLATFORMS = 2;
    private static final int NUM_EFFECTS = 3;
    
    private final boolean mEffects[];
    private final float mValues[];
    
    public CollisionEffects() {
        mEffects = new boolean[NUM_EFFECTS];
        mValues = new float[NUM_EFFECTS];
    }
    
    public void clear() {
        for (int i = 0; i < NUM_EFFECTS; i++) {
            mEffects[i] = false;
            mValues[i] = 0.0f;
        }
    }
    
    public void set(int effect) {
        mEffects[effect] = true;
    }
    
    public void set(int effect, float value) {
        mEffects[effect] = true;
        mValues[effect] = value;
    }
    
    public boolean isEffectActive(int effect) {
        return mEffects[effect];
    }
    
    public float getValue(int effect) {
        return mValues[effect];
    }
}
