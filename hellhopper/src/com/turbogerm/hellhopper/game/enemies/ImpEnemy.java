package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.EnemyData;
import com.turbogerm.hellhopper.resources.ResourceNames;

final class ImpEnemy extends EnemyBase {
    
    private static final float SIN_AMPLITUDE = 0.2f;
    private static final float SIN_PERIOD = 0.5f;
    
    private static final float COLLISION_PADDING_FRONT = 0.55f;
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float mSpeed;
    private final float mRange;
    
    private final float mLeftLimit;
    private final float mRightLimit;
    private boolean mIsRightMovement;
    
    private final Vector2 mInitialPosition;
    private final Vector2 mPosition;
    
    private float mSinTime;
    
    private final Rectangle mCollisionRect;
    
    public ImpEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_IMP_TEXTURE, startStep, assetManager);
        
        mInitialPosition = enemyData.getPosition(startStep);
        
        mSpeed = Float.parseFloat(enemyData.getProperty(EnemyData.SPEED_PROPERTY));
        mRange = Float.parseFloat(enemyData.getProperty(EnemyData.RANGE_PROPERTY));
        
        mLeftLimit = mInitialPosition.x;
        mRightLimit = mInitialPosition.x + mRange;
        mIsRightMovement = true;
        
        mPosition = new Vector2(mInitialPosition);
        
        mSprite.flip(true, false);
        
        mSinTime = 0.0f;
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - COLLISION_PADDING - COLLISION_PADDING_FRONT;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void update(float delta) {
        float travelled = mSpeed * delta;
        if (!mIsRightMovement) {
            travelled = -travelled;
        }
        
        changePosition(travelled);
        
        mSinTime = (mSinTime + delta) % SIN_PERIOD;
        mPosition.y = mInitialPosition.y + MathUtils.sinDeg(mSinTime / SIN_PERIOD * 360.0f) * SIN_AMPLITUDE;
        
        mSprite.setPosition(mPosition.x, mPosition.y);
        if (mIsRightMovement) {
            mCollisionRect.setX(mPosition.x + COLLISION_PADDING);
        } else {
            mCollisionRect.setX(mPosition.x + COLLISION_PADDING_FRONT);
        }
        mCollisionRect.setY(mPosition.y + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private void changePosition(float change) {
        mPosition.x += change;
        if (mPosition.x <= mLeftLimit) {
            mIsRightMovement = true;
            mSprite.flip(true, false);
        } else if (mPosition.x >= mRightLimit) {
            mIsRightMovement = false;
            mSprite.flip(true, false);
        }
        
        mPosition.x = MathUtils.clamp(mPosition.x, mLeftLimit, mRightLimit);
    }
}
