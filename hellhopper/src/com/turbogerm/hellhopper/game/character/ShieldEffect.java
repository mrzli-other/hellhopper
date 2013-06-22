package com.turbogerm.hellhopper.game.character;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.GameUtils;

public final class ShieldEffect {
    
    private static final float OFFSET_X = -0.125f;
    private static final float OFFSET_Y = -0.11f;
    private static final float WIDTH = 1.25f;
    private static final float HEIGHT = 1.8f;
    
    private static final float HIGH_ALPHA = 1.0f;
    private static final float LOW_ALPHA = 0.4f;
    private static final float MID_ALPHA = (HIGH_ALPHA + LOW_ALPHA) / 2.0f;
    private static final float ALPHA_HALF_RANGE = (HIGH_ALPHA - LOW_ALPHA) / 2.0f;
    private static final float ALPHA_CHANGE_PERIOD = 0.5f;
    
    private final Sprite mSprite;
    
    private float mTime;
    
    public ShieldEffect(AssetManager assetManager) {
        Texture shieldEffectTexture = assetManager.get(ResourceNames.CHARACTER_SHIELD_EFFECT_TEXTURE);
        mSprite = new Sprite(shieldEffectTexture);
        mSprite.setSize(WIDTH, HEIGHT);
        
        mTime = 0.0f;
    }
    
    public void update(float delta) {
        mTime += delta;
    }
    
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        float alpha = MID_ALPHA + ALPHA_HALF_RANGE * MathUtils.sinDeg(mTime / ALPHA_CHANGE_PERIOD * 360.0f);
        GameUtils.setSpriteAlpha(mSprite, alpha);
        
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
}
