package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

public final class CrumblePlatform extends PlatformBase {
    
    private static final float CRUMBLING_COUNTDOWN_DURATION = 1.0f;
    
    private boolean mIsCrumbling;
    private float mCrumblingCountdown;
    
    public CrumblePlatform(int riseSectionId, PlatformData platformData, int startStep, AssetManager assetManager) {
        super(riseSectionId, platformData, startStep, assetManager);
        
        mIsCrumbling = false;
        mCrumblingCountdown = CRUMBLING_COUNTDOWN_DURATION;
    }
    
    @Override
    protected void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        if (mIsCrumbling) {
            mCrumblingCountdown -= delta;
        }
        
        super.updateImpl(delta, c1, c2, collisionData);
    }
    
    @Override
    protected void renderImpl(SpriteBatch batch, float delta) {
        if (mIsCrumbling) {
            mPlatformModifier.spriteColor.a = mCrumblingCountdown / CRUMBLING_COUNTDOWN_DURATION;
        }
        
        super.renderImpl(batch, delta);
    }
    
    @Override
    public boolean isCollision(Vector2 c1, Vector2 c2, Vector2 intersection) {
        
        if (!mIsCrumbling) {
            boolean isCollision = super.isCollision(c1, c2, intersection);
            if (isCollision) {
                mIsCrumbling = true;
            }
            
            return isCollision;
        } else {
            return false;
        }
        
    }
    
    @Override
    protected boolean isActiveInternal() {
        return mCrumblingCountdown > 0.0f;
    }
    
    @Override
    protected boolean isMovingInternal() {
        return !mIsCrumbling;
    }
}
