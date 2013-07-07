package com.turbogerm.germlibrary.controls;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
            TextureAtlas textureAtlas,
            String upTexturePath, String downTexturePath, CustomButtonAction action,
            AssetManager assetManager) {
        
        this(positionX, positionY,
                textureAtlas,
                createSingleStyleData(upTexturePath, downTexturePath),
                action, assetManager);
    }
    
    public CustomImageButton(float positionX, float positionY,
            TextureAtlas textureAtlas,
            Array<CustomImageButtonStyleData> stylesData, CustomButtonAction action,
            AssetManager assetManager) {
        
        mStyles = getStyles(textureAtlas, stylesData, assetManager);
        
        AtlasRegion region = textureAtlas.findRegion(stylesData.get(0).getUpImageName());
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        
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
            TextureAtlas textureAtlas,
            Array<CustomImageButtonStyleData> stylesData, AssetManager assetManager) {
        
        ObjectMap<Integer, ImageButtonStyle> styles = new ObjectMap<Integer, ImageButtonStyle>(stylesData.size);
        for (CustomImageButtonStyleData styleData : stylesData) {
            ImageButtonStyle style = getStyle(textureAtlas,
                    styleData.getUpImageName(), styleData.getDownImageName(), assetManager);
            styles.put(styleData.getId(), style);
        }
        
        return styles;
    }
    
    private static ImageButtonStyle getStyle(TextureAtlas textureAtlas,
            String upImageName, String downImageName, AssetManager assetManager) {
        Drawable upDrawable = getDrawable(textureAtlas, upImageName, assetManager);
        Drawable downDrawable = getDrawable(textureAtlas, downImageName, assetManager);
        return new ImageButtonStyle(null, null, null, upDrawable, downDrawable, null);
    }
    
    private static Drawable getDrawable(TextureAtlas textureAtlas, String imageName, AssetManager assetManager) {
        TextureRegion textureRegion = textureAtlas.findRegion(imageName);
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
