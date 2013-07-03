package com.turbogerm.germlibrary.util;

import com.badlogic.gdx.graphics.Color;

public final class ColorPositionPair {
    
    private final Color mColor;
    private final float mPosition;
    
    public ColorPositionPair(Color color, float position) {
        mColor = color;
        mPosition = position;
    }
    
    public Color getColor() {
        return mColor;
    }
    
    public float getPosition() {
        return mPosition;
    }
}
