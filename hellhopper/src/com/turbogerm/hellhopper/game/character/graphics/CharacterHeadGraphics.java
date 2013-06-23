package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;

public final class CharacterHeadGraphics extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.0f;
    private static final float OFFSET_Y = 0.35f;
    private static final float WIDTH = 1.0f;
    private static final float HEIGHT = 1.15f;
    
    private static final Color DEFAULT_COLOR;
    
    private final Sprite mSprite;
    private final Color mColor;
    
    static {
        DEFAULT_COLOR = new Color(0.57f, 0.74f, 0.79f, 1.0f);
    }
    
    public CharacterHeadGraphics(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.CHARACTER_HEAD_TEXTURE);
        mSprite = new Sprite(texture);
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
}
