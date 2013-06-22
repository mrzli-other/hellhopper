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
