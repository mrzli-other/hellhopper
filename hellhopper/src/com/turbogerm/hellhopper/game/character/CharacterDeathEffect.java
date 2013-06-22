package com.turbogerm.hellhopper.game.character;

final class CharacterDeathEffect {
    
    public static final int DYING_STATUS_NONE = 0;
    public static final int DYING_STATUS_FALL = 1;
    public static final int DYING_STATUS_ENEMY = 2;
    public static final int DYING_STATUS_FIRE = 3;
    
    private static final float DYING_DURATION = 3.0f;
    
    private int mDyingStatus;
    private float mDyingCountdown;
    
    public CharacterDeathEffect() {
    }
    
    public void reset() {
        mDyingStatus = DYING_STATUS_NONE;
        mDyingCountdown = DYING_DURATION;
    }
    
    public void update(float delta) {
        if (isDying() && mDyingCountdown > 0.0f) {
            mDyingCountdown -= delta;
        }
    }
    
    public void setDyingStatus(int dyingStatus) {
        mDyingStatus = dyingStatus;
    }
    
    public boolean isDead() {
        return mDyingCountdown <= 0.0f;
    }
    
    public boolean isDying() {
        return mDyingStatus != DYING_STATUS_NONE;
    }
}
