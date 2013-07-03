package com.turbogerm.hellhopper.screens.general;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public final class CustomImageButton {
    
    private final ImageButton mButton;
    private final ObjectMap<Integer, ImageButtonStyle> mStyles;
    
    public CustomImageButton(float positionX, float positionY,
            String upTexturePath, String downTexturePath, CustomButtonAction action,
            AssetManager assetManager) {
        
        this(positionX, positionY,
                createSingleStyleData(upTexturePath, downTexturePath),
                action, assetManager);
    }
    
    public CustomImageButton(float positionX, float positionY,
            Array<CustomImageButtonStyleData> stylesData, CustomButtonAction action,
            AssetManager assetManager) {
        
        mStyles = getStyles(stylesData, assetManager);
        
        Texture texture = assetManager.get(stylesData.get(0).getUpTexturePath());
        float width = texture.getWidth();
        float height = texture.getHeight();
        
        mButton = new ImageButton(mStyles.values().next());
        
        mButton.setBounds(positionX, positionY, width, height);
        mButton.addListener(getInputListener(mButton, action));
    }
    
    public void addToStage(Stage stage) {
        stage.addActor(mButton);
    }
    
    public void setStyle(int styleId) {
        mButton.setStyle(mStyles.get(styleId));
    }
    
    private static Array<CustomImageButtonStyleData> createSingleStyleData(
            String upTexturePath, String downTexturePath) {
        
        Array<CustomImageButtonStyleData> stylesData = new Array<CustomImageButtonStyleData>(true, 1);
        stylesData.add(new CustomImageButtonStyleData(0, upTexturePath, downTexturePath));
        
        return stylesData;
    }
    
    private static ObjectMap<Integer, ImageButtonStyle> getStyles(
            Array<CustomImageButtonStyleData> stylesData, AssetManager assetManager) {
        
        ObjectMap<Integer, ImageButtonStyle> styles = new ObjectMap<Integer, ImageButtonStyle>(stylesData.size);
        for (CustomImageButtonStyleData styleData : stylesData) {
            ImageButtonStyle style = getStyle(
                    styleData.getUpTexturePath(), styleData.getDownTexturePath(), assetManager);
            styles.put(styleData.getId(), style);
        }
        
        return styles;
    }
    
    private static ImageButtonStyle getStyle(String upTexturePath, String downTexturePath, AssetManager assetManager) {
        Drawable upDrawable = getDrawable(upTexturePath, assetManager);
        Drawable downDrawable = getDrawable(downTexturePath, assetManager);
        return new ImageButtonStyle(null, null, null, upDrawable, downDrawable, null);
    }
    
    private static Drawable getDrawable(String texturePath, AssetManager assetManager) {
        Texture texture = assetManager.get(texturePath);
        TextureRegion textureRegion = new TextureRegion(texture);
        return new TextureRegionDrawable(textureRegion);
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
