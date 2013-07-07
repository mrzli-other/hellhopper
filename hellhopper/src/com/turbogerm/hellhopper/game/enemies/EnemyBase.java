package com.turbogerm.hellhopper.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.germlibrary.util.GameUtils;
import com.turbogerm.hellhopper.dataaccess.EnemyData;
import com.turbogerm.hellhopper.game.GameAreaUtils;
import com.turbogerm.hellhopper.resources.ResourceNames;

public abstract class EnemyBase {
    
    protected final Sprite mSprite;
    
    public EnemyBase(EnemyData enemyData, String imageName, int startStep, AssetManager assetManager) {
        
        Vector2 initialPosition = enemyData.getPosition(startStep);
        
        TextureAtlas atlas = assetManager.get(ResourceNames.ENEMIES_ATLAS);
        mSprite = atlas.createSprite(imageName);
        mSprite.setPosition(initialPosition.x, initialPosition.y);
        GameUtils.multiplySpriteSize(mSprite, GameAreaUtils.PIXEL_TO_METER);
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
