package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.util.GameUtils;

final class CharacterEndEffect {
    
    private static final float END_RESTITUTION_MULTIPLIER = 1.0f / 1.5f;
    private static final float END_RESTITUTION_SPEED_DECREASE = 0.75f;
    private static final float END_REACHED_DURATION = 3.0f;
    
    private boolean mIsEndReached;
    private float mEndReachedCountdown;
    
    public CharacterEndEffect() {
    }
    
    public void reset() {
        mIsEndReached = false;
        mEndReachedCountdown = END_REACHED_DURATION;
    }
    
    public void update(Vector2 characterPosition, Vector2 characterSpeed, float riseHeight, float delta) {
        if (mIsEndReached && mEndReachedCountdown > 0.0f) {
            if (characterPosition.y <= riseHeight) {
                characterPosition.y = riseHeight + GameUtils.EPSILON;
                characterSpeed.y = Math.abs(characterSpeed.y * END_RESTITUTION_MULTIPLIER);
                characterSpeed.y = Math.max(characterSpeed.y - END_RESTITUTION_SPEED_DECREASE, 0.0f);
            }
            mEndReachedCountdown -= delta;
        }
    }
    
    public void setEndReached() {
        mIsEndReached = true;
    }
    
    public boolean isFinished() {
        return mEndReachedCountdown <= 0.0f;
    }
    
    public boolean isEndReached() {
        return mIsEndReached;
    }
}
