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
package com.turbogerm.hellhopper.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public final class Pools {
    
    private static final int VECTORS_CAPACITY = 16;
    private static final int RECTANGLES_CAPACITY = 16;
    
    private static final Pool<Vector2> mVectorsPool;
    private static final Pool<Rectangle> mRectanglesPool;
    
    static {
        mVectorsPool = new Pool<Vector2>(VECTORS_CAPACITY, VECTORS_CAPACITY) {
            @Override
            protected Vector2 newObject () {
                return new Vector2();
            }
        };
        
        mRectanglesPool = new Pool<Rectangle>(RECTANGLES_CAPACITY, RECTANGLES_CAPACITY) {
            @Override
            protected Rectangle newObject () {
                return new Rectangle();
            }
        };
    }
    
    public static Vector2 obtainVector() {
        return mVectorsPool.obtain();
    }
    
    public static void freeVector(Vector2 v) {
        mVectorsPool.free(v);
    }
    
    public static Rectangle obtainRectangle() {
        return mRectanglesPool.obtain();
    }
    
    public static void freeRectangle(Rectangle r) {
        mRectanglesPool.free(r);
    }
}
