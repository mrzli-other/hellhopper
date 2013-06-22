package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.EnemyData;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.util.GameUtils;

final class KnightEnemy extends EnemyBase {
    
    private static final float SPEED = 5.0f;
    private static final float TREMOR_ROTATION_LIMITS = 5.0f;
    
    private static final float COLLISION_PADDING = 0.05f;
    
    private final float mRadius;
    private final float mAngleSpeed;
    private float mAngleParameter;
    private final Vector2[] mRotationCenters;
    
    private final Vector2 mPosition;
    private final Vector2 mCenterOffset;
    
    private final Rectangle mCollisionRect;
    
    public KnightEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_KNIGHT_TEXTURE, startStep, assetManager);
        
        float range = GameArea.GAME_AREA_WIDTH - mSprite.getWidth();
        mRadius = range / 4.0f;
        
        mAngleSpeed = SPEED / mRadius * MathUtils.radDeg;
        mAngleParameter = 0.0f;
        
        mPosition = new Vector2();
        mCenterOffset = new Vector2(
                mSprite.getWidth() / 2.0f, mSprite.getHeight() / 2.0f);
        
        mRotationCenters = new Vector2[2];
        mRotationCenters[0] = new Vector2(
                mCenterOffset.x + mRadius,
                mSprite.getY() + mCenterOffset.y);
        mRotationCenters[1] = new Vector2(
                mRotationCenters[0].x + mRadius * 2.0f,
                mRotationCenters[0].y);
        
        float x = mSprite.getX() + COLLISION_PADDING;
        float y = mSprite.getY() + COLLISION_PADDING;
        float width = mSprite.getWidth() - 2.0f * COLLISION_PADDING;
        float height = mSprite.getHeight() - 2.0f * COLLISION_PADDING;
        mCollisionRect = new Rectangle(x, y, width, height);
    }
    
    @Override
    public void update(float delta) {
        float travelledAngle = mAngleSpeed * delta;
        changePosition(travelledAngle);
        
        mSprite.setRotation(MathUtils.random(-TREMOR_ROTATION_LIMITS, TREMOR_ROTATION_LIMITS));
        mSprite.setPosition(mPosition.x, mPosition.y);
        mCollisionRect.setX(mPosition.x + COLLISION_PADDING);
        mCollisionRect.setY(mPosition.y + COLLISION_PADDING);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapRectangles(rect, mCollisionRect);
    }
    
    private void changePosition(float change) {
        mAngleParameter += change;
        mAngleParameter = GameUtils.getPositiveModulus(mAngleParameter, 720.0f);
        
        float angle = mAngleParameter <= 360.0f ? mAngleParameter : 540.0f - mAngleParameter;
        int rotationCenterIndex = mAngleParameter <= 360.0f ? 0 : 1;
        
        mPosition.x = mRotationCenters[rotationCenterIndex].x +
                MathUtils.cosDeg(angle) * mRadius - mCenterOffset.x;
        mPosition.y = mRotationCenters[rotationCenterIndex].y +
                MathUtils.sinDeg(angle) * mRadius - mCenterOffset.y;
    }
}
