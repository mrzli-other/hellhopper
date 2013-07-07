package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class CharacterBodyGraphics extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.05f;
    private static final float OFFSET_Y = 0.0f;
    private static final float WIDTH = 0.9f;
    private static final float HEIGHT = 0.8f;
    
    public static final Color DEFAULT_COLOR;
    
    private final Sprite mSprite;
    private final Color mColor;
    
    static {
        DEFAULT_COLOR = new Color(0.14f, 0.36f, 0.43f, 1.0f);
    }
    
    public CharacterBodyGraphics(AssetManager assetManager) {
        
        TextureAtlas atlas = assetManager.get(ResourceNames.CHARACTER_ATLAS);
        mSprite = atlas.createSprite(ResourceNames.CHARACTER_BODY_IMAGE_NAME);
        mSprite.setSize(WIDTH, HEIGHT);
        
        mColor = new Color();
    }
    
    @Override
    public void reset() {
        mColor.set(DEFAULT_COLOR);
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        mSprite.setColor(mColor);
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
    
    public void setColor(Color color) {
        mColor.set(color);
    }
}
