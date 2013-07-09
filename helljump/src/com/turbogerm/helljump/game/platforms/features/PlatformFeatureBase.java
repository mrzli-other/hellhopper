package com.turbogerm.helljump.game.platforms.features;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.helljump.game.CollisionEffects;

public abstract class PlatformFeatureBase {
    
    protected float mRenderPrecedence;
    protected float mContactPrecendence;
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch, Vector2 platformPosition, Color color) {
    }
    
    public boolean isContact(float relativeCollisionPointX) {
        return false;
    }
    
    public void applyModifier(PlatformModifier modifier) {
    }
    
    public void applyEffect(int collisionEffect) {
    }
    
    public void applyContact(CollisionEffects collisionEffects) {
    }
    
    public float getRenderPrecedence() {
        return mRenderPrecedence;
    }
    
    public float getContactPrecedence() {
        return mContactPrecendence;
    }
}
