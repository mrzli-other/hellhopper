package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.EnemyData;

public final class SawEnemy extends EnemyBase {
    
    private static final float ROTATION_SPEED = 180.0f;
    private static final float COLLISION_RADIUS_MULTIPLIER = 0.9f;
    
    private final Circle mCollisionCircle;
    
    public SawEnemy(EnemyData enemyData, int startStep, AssetManager assetManager) {
        super(enemyData, ResourceNames.ENEMY_SAW_TEXTURE, startStep, assetManager);
        
        float x = mSprite.getX() + mSprite.getWidth() / 2.0f;
        float y = mSprite.getY() + mSprite.getHeight() / 2.0f;
        float radius = mSprite.getWidth() / 2.0f * COLLISION_RADIUS_MULTIPLIER;
        mCollisionCircle = new Circle(x, y, radius);
    }
    
    @Override
    public void update(float delta) {
        mSprite.rotate(-ROTATION_SPEED * delta);
    }
    
    @Override
    public boolean isCollision(Rectangle rect) {
        return Intersector.overlapCircleRectangle(mCollisionCircle, rect);
    }
}
