package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.resources.ResourceNames;
import com.turbogerm.hellhopper.screens.general.CustomButtonAction;
import com.turbogerm.hellhopper.screens.general.CustomImageButton;
import com.turbogerm.hellhopper.screens.general.ScreenBackground;

public final class CreditsScreen extends ScreenBase {
    
    private static final float BUTTON_X = 105.0f;
    private static final float BUTTON_Y = 20.0f;
    
    private final ScreenBackground mScreenBackground;
    
    public CreditsScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener(this));
        
        mScreenBackground = new ScreenBackground(mAssetManager);
        
        float offsetFromTop = addTitleLabel();
        addTextLabel(offsetFromTop);
        
        CustomImageButton button = new CustomImageButton(
                BUTTON_X, BUTTON_Y,
                ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE,
                ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE,
                getBackAction(),
                mAssetManager);
        button.addToStage(mGuiStage);
    }
    
    @Override
    public void show() {
        super.show();
        
        mScreenBackground.reset();
    }
    
    @Override
    public void renderImpl(float delta) {
        mScreenBackground.update(delta);
        
        mBatch.begin();
        mScreenBackground.render(mBatch);
        mBatch.end();
    }
    
    private float addTitleLabel() {
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("xxxl-font");
        
        String text = "Credits";
        TextBounds textBounds = labelStyle.font.getBounds(text);
        
        final float padding = 10.0f;
        final float labelHeight = textBounds.height + 2.0f * padding;
        
        Label label = new Label(text, mGuiSkin);
        label.setBounds(0.0f, HellHopper.VIEWPORT_HEIGHT - labelHeight,
                HellHopper.VIEWPORT_WIDTH, labelHeight);
        label.setStyle(labelStyle);
        label.setAlignment(Align.center);
        mGuiStage.addActor(label);
        
        return labelHeight;
    }
    
    private void addTextLabel(float offsetFromTop) {
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("medium-font");
        
        StringBuilder sb = new StringBuilder();
        sb.append("Created by: Goran Mrzljak\n");
        sb.append("  (goran.mrzljak@gmail.com)\n\n");
        sb.append("Credits for original resources\n");
        sb.append("  (I modified most of them):\n");
        sb.append("Flame on platform animation -\n");
        sb.append("  Zabin and Jetrel (opengameart.com)\n");
        sb.append("Jump sound - jorickhoofd\n");
        sb.append("  (freesound.org; id: 169551)\n");
        sb.append("Jump Boost sound - jesabat\n");
        sb.append("  (freesound.org; id: 119741)\n");
        sb.append("Coin sound - fins\n");
        sb.append("  (freesound.org; id: 146723)\n");
        sb.append("Item sound - cameronmusic\n");
        sb.append("  (freesound.org; id: 138410)\n");
        sb.append("Fart sound - smokenweewALT\n");
        sb.append("  (freesound.org; id: 177050)\n");
        sb.append("Enemy sound - anwul\n");
        sb.append("  (freesound.org; id: 185366)\n");
        sb.append("Fire sound - urupin\n");
        sb.append("  (freesound.org; id: 178813)\n");
        sb.append("Fall sound - silversatyr\n");
        sb.append("  (freesound.org; id: 113366)\n");
        sb.append("Sheep sound - reinsamba\n");
        sb.append("  (freesound.org; id: 57796)\n");
        
        String text = sb.toString();
        
        TextBounds textBounds = labelStyle.font.getMultiLineBounds(text);
        final float padding = 5.0f;
        final float labelHeight = textBounds.height + padding;
        
        Label textLabel = new Label(text, mGuiSkin);
        textLabel.setBounds(padding, HellHopper.VIEWPORT_HEIGHT - offsetFromTop - labelHeight,
                HellHopper.VIEWPORT_WIDTH, labelHeight);
        textLabel.setStyle(labelStyle);
        textLabel.setAlignment(Align.left);
        mGuiStage.addActor(textLabel);
    }
    
    private static InputListener getStageInputListener(final CreditsScreen screen) {
        
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    screen.mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
    
    private CustomButtonAction getBackAction() {
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
            }
        };
    }
}
