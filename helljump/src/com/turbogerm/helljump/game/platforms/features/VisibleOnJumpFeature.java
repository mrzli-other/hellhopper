package com.turbogerm.helljump.game.platforms.features;

import com.badlogic.gdx.assets.AssetManager;
import com.turbogerm.helljump.dataaccess.PlatformFeatureData;
import com.turbogerm.helljump.game.CollisionEffects;

final class VisibleOnJumpFeature extends PlatformFeatureBase {
    
    private static final float VISIBILITY_DURATION = 0.5f;
    
    private float mVisibilityCountdown;
    
    public VisibleOnJumpFeature(PlatformFeatureData featureData, AssetManager assetManager) {
        
        mVisibilityCountdown = 0.0f;
    }
    
    @Override
    public void update(float delta) {
        if (mVisibilityCountdown > 0.0f) {
            mVisibilityCountdown -= delta;
        }
    }
    
    @Override
    public void applyModifier(PlatformModifier modifier) {
        modifier.isPlatformVisible = mVisibilityCountdown > 0.0f;
        modifier.spriteColor.set(1.0f, 1.0f, 1.0f, mVisibilityCountdown / VISIBILITY_DURATION);
    }
    
    public void applyEffect(int collisionEffect) {
        if (collisionEffect == CollisionEffects.VISIBLE_ON_JUMP) {
            mVisibilityCountdown = VISIBILITY_DURATION;
        }
    }
}
