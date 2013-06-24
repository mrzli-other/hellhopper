package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.GameUtils;

public final class FartDischargeGraphics extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = 0.275f;
    private static final float OFFSET_Y = -0.35f;
    private static final float WIDTH = 0.45f;
    private static final float HEIGHT = 0.65f;
    
    private static final float DISCHARGE_DURATION = 0.3f;
    
    private final Sprite mSprite;
    
    private float mDischargeElapsed;
    
    public FartDischargeGraphics(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.CHARACTER_FART_DISCHARGE_TEXTURE);
        mSprite = new Sprite(texture);
        mSprite.setSize(WIDTH, HEIGHT);
    }
    
    @Override
    public void reset() {
        mDischargeElapsed = DISCHARGE_DURATION;
    }
    
    @Override
    public void update(float delta) {
        if (mDischargeElapsed < DISCHARGE_DURATION) {
            mDischargeElapsed += delta;
        }
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        if (mDischargeElapsed < DISCHARGE_DURATION) {
            float alpha = 1.0f - mDischargeElapsed / DISCHARGE_DURATION;
            GameUtils.setSpriteAlpha(mSprite, alpha);
            
            mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
            mSprite.draw(batch);
        }
    }
    
    public void discharge() {
        mDischargeElapsed = 0.0f;
    }
}
