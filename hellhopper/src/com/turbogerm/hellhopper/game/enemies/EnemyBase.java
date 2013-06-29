package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.EnemyData;
import com.turbogerm.hellhopper.game.GameAreaUtils;
import com.turbogerm.hellhopper.util.GameUtils;

public abstract class EnemyBase {
    
    protected final Sprite mSprite;
    
    public EnemyBase(EnemyData enemyData, String texturePath, int startStep, AssetManager assetManager) {
        
        Texture texture = assetManager.get(texturePath);
        Vector2 initialPosition = enemyData.getPosition(startStep);
        
        mSprite = new Sprite(texture);
        mSprite.setBounds(
                initialPosition.x, initialPosition.y,
                texture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                texture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        GameUtils.setSpriteOriginCenter(mSprite);
    }
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch) {
        mSprite.draw(batch);
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
}
