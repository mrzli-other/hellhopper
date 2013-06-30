package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class CharacterMouthGraphicsSmile extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.3f;
    private static final float OFFSET_Y = 0.46f;
    private static final float WIDTH = 0.4f;
    private static final float HEIGHT = 0.1f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite mSprite;
    
    static {
        DEFAULT_COLOR = new Color(1.0f, 0.5f, 0.0f, 1.0f);
    }
    
    public CharacterMouthGraphicsSmile(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.CHARACTER_MOUTH_SMILE_TEXTURE);
        mSprite = new Sprite(texture);
        mSprite.setSize(WIDTH, HEIGHT);
        mSprite.setColor(DEFAULT_COLOR);
    }
    
    @Override
    public void reset() {
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
}
