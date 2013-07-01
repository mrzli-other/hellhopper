package com.turbogerm.hellhopper.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public final class CustomImageButton {
    
    private final ImageButton mButton;
    
    public CustomImageButton(float positionX, float positionY,
            String upTexturePath, String downTexturePath, CustomButtonAction action,
            AssetManager assetManager) {
        
        Texture upTexture = assetManager.get(upTexturePath);
        TextureRegion upTextureRegion = new TextureRegion(upTexture);
        Drawable upDrawable = new TextureRegionDrawable(upTextureRegion);
        
        Texture downTexture = assetManager.get(downTexturePath);
        TextureRegion downTextureRegion = new TextureRegion(downTexture);
        Drawable downDrawable = new TextureRegionDrawable(downTextureRegion);
        
        mButton = new ImageButton(upDrawable, downDrawable);
        float width = upTexture.getWidth();
        float height = upTexture.getHeight();
        mButton.setBounds(positionX, positionY, width, height);
        mButton.addListener(getInputListener(mButton, action));
    }
    
    public void addToStage(Stage stage) {
        stage.addActor(mButton);
    }
    
    private static InputListener getInputListener(final Actor actor, final CustomButtonAction action) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    action.invoke();
                }
            }
        };
    }
}
