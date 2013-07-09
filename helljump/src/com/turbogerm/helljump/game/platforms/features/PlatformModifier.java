package com.turbogerm.helljump.game.platforms.features;

import com.badlogic.gdx.graphics.Color;

public final class PlatformModifier {
    
    public boolean isPlatformVisible;
    public Color spriteColor;
    
    public PlatformModifier() {
        isPlatformVisible = true;
        spriteColor = new Color();
    }
    
    public void reset() {
        isPlatformVisible = true;
    }
}
