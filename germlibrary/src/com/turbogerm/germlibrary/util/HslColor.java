package com.turbogerm.germlibrary.util;

public final class HslColor {
    
    public float h, s, l;
    
    public HslColor() {
        h = s = l = 0.0f;
    }
    
    public HslColor(float h, float s, float l) {
        this.h = h;
        this.s = s;
        this.l = l;
    }
}