/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.HellHopper;

public final class InfoScreen extends ScreenBase {
    
    public InfoScreen(HellHopper game) {
        super(game);
        
        mClearColor = Color.DARK_GRAY;
        
        mGuiStage.addListener(getStageInputListener(this));
        
        LabelStyle titleLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        titleLabelStyle.font = mGuiSkin.get("xxl-font", BitmapFont.class); // mResources.getFont("xxxl");
        
        LabelStyle textLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        textLabelStyle.font = mGuiSkin.get("large-font", BitmapFont.class); // mResources.getFont("xxxl");
        
        final float controlPadding = 10.0f;
        final float titleLabelHeight = 50.0f;
        final float textControlHeight = 100.0f;
        final float smallTextHeight = 20.0f;
        final float controlImageSize = textControlHeight;
        final float textControlVerticalStride = textControlHeight + controlPadding;
        
        final float controlTextLabelWidth = 100.0f;
        final float controlImage1X = controlTextLabelWidth + 2.0f * controlPadding;
        final float controlImage2X = controlTextLabelWidth + controlImageSize + 3.0f * controlPadding;
        final float controlImage3X = controlTextLabelWidth + 2.0f * controlImageSize + 4.0f * controlPadding;
        
        float currentControlY = HellHopper.VIEWPORT_HEIGHT - titleLabelHeight;
        
        Label controlsTitleLabel = new Label("Controls", mGuiSkin);
        controlsTitleLabel.setBounds(0.0f, currentControlY, HellHopper.VIEWPORT_WIDTH, titleLabelHeight);
        controlsTitleLabel.setStyle(titleLabelStyle);
        controlsTitleLabel.setAlignment(Align.center);
        mGuiStage.addActor(controlsTitleLabel);
        
        currentControlY -= textControlVerticalStride;
        
        Label leftTextLabel = new Label("Left:", mGuiSkin);
        leftTextLabel.setBounds(controlPadding, currentControlY, controlTextLabelWidth, textControlHeight);
        leftTextLabel.setStyle(textLabelStyle);
        leftTextLabel.setAlignment(Align.left);
        mGuiStage.addActor(leftTextLabel);
        
        Texture leftButtonTexture = mAssetManager.get(ResourceNames.GUI_LEFT_UP_TEXTURE);
        Image leftButtonImageImage = new Image(leftButtonTexture);
        leftButtonImageImage.setBounds(controlImage1X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(leftButtonImageImage);
        
        Texture keyboardLeftTexture = mAssetManager.get(ResourceNames.GUI_KEYBOARD_LEFT_TEXTURE);
        Image keyboardLeftImage = new Image(keyboardLeftTexture);
        keyboardLeftImage.setBounds(controlImage2X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(keyboardLeftImage);
        
        Texture dragLeftTexture = mAssetManager.get(ResourceNames.GUI_DRAG_LEFT_TEXTURE);
        Image dragLeftImage = new Image(dragLeftTexture);
        dragLeftImage.setBounds(controlImage3X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(dragLeftImage);
        
        currentControlY -= textControlVerticalStride;
        
        Label rightTextLabel = new Label("Right:", mGuiSkin);
        rightTextLabel.setBounds(controlPadding, currentControlY, controlTextLabelWidth, textControlHeight);
        rightTextLabel.setStyle(textLabelStyle);
        rightTextLabel.setAlignment(Align.left);
        mGuiStage.addActor(rightTextLabel);
        
        Texture rightButtonTexture = mAssetManager.get(ResourceNames.GUI_RIGHT_UP_TEXTURE);
        Image rightButtonImageImage = new Image(rightButtonTexture);
        rightButtonImageImage.setBounds(controlImage1X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(rightButtonImageImage);
        
        Texture keyboardRightTexture = mAssetManager.get(ResourceNames.GUI_KEYBOARD_RIGHT_TEXTURE);
        Image keyboardRightImage = new Image(keyboardRightTexture);
        keyboardRightImage.setBounds(controlImage2X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(keyboardRightImage);
        
        Texture dragRightTexture = mAssetManager.get(ResourceNames.GUI_DRAG_RIGHT_TEXTURE);
        Image dragRightImage = new Image(dragRightTexture);
        dragRightImage.setBounds(controlImage3X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(dragRightImage);
        
        currentControlY -= textControlVerticalStride;
        
        Label rotateTextLabel = new Label("Rotate:", mGuiSkin);
        rotateTextLabel.setBounds(controlPadding, currentControlY, controlTextLabelWidth, textControlHeight);
        rotateTextLabel.setStyle(textLabelStyle);
        rotateTextLabel.setAlignment(Align.left);
        mGuiStage.addActor(rotateTextLabel);
        
        Texture rotateButtonTexture = mAssetManager.get(ResourceNames.GUI_ROTATE_UP_TEXTURE);
        Image rotateButtonImageImage = new Image(rotateButtonTexture);
        rotateButtonImageImage.setBounds(controlImage1X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(rotateButtonImageImage);
        
        Texture keyboardRotateTexture = mAssetManager.get(ResourceNames.GUI_KEYBOARD_UP_TEXTURE);
        Image keyboardRotateImage = new Image(keyboardRotateTexture);
        keyboardRotateImage.setBounds(controlImage2X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(keyboardRotateImage);
        
        Texture dragRotateTexture = mAssetManager.get(ResourceNames.GUI_DRAG_UP_TEXTURE);
        Image dragRotateImage = new Image(dragRotateTexture);
        dragRotateImage.setBounds(controlImage3X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(dragRotateImage);
        
        currentControlY -= textControlVerticalStride;
        
        Label dropTextLabel = new Label("Drop:", mGuiSkin);
        dropTextLabel.setBounds(controlPadding, currentControlY, controlTextLabelWidth, textControlHeight);
        dropTextLabel.setStyle(textLabelStyle);
        dropTextLabel.setAlignment(Align.left);
        mGuiStage.addActor(dropTextLabel);
        
        Texture dropButtonTexture = mAssetManager.get(ResourceNames.GUI_DOWN_UP_TEXTURE);
        Image dropButtonImageImage = new Image(dropButtonTexture);
        dropButtonImageImage.setBounds(controlImage1X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(dropButtonImageImage);
        
        Texture keyboardDropTexture = mAssetManager.get(ResourceNames.GUI_KEYBOARD_DOWN_TEXTURE);
        Image keyboardDropImage = new Image(keyboardDropTexture);
        keyboardDropImage.setBounds(controlImage2X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(keyboardDropImage);
        
        Texture dragDropTexture = mAssetManager.get(ResourceNames.GUI_DRAG_DOWN_TEXTURE);
        Image dragDropImage = new Image(dragDropTexture);
        dragDropImage.setBounds(controlImage3X, currentControlY, controlImageSize, controlImageSize);
        mGuiStage.addActor(dragDropImage);
        
        currentControlY -= 3.0f * smallTextHeight + controlPadding;
        
        Label dragDescriptionLabel = new Label(
                "* Touch/mouse dragging must be done on game area \n(where the blocks fall)\n" +
                        "Pause can be toggled by pressing P.", mGuiSkin);
        dragDescriptionLabel.setBounds(controlPadding, currentControlY, HellHopper.VIEWPORT_WIDTH, titleLabelHeight);
        dragDescriptionLabel.setAlignment(Align.left);
        mGuiStage.addActor(dragDescriptionLabel);
        
        currentControlY -= titleLabelHeight + 2.0f * controlPadding;
        
        Label creditsTitleLabel = new Label("Credits", mGuiSkin);
        creditsTitleLabel.setBounds(0.0f, currentControlY, HellHopper.VIEWPORT_WIDTH, titleLabelHeight);
        creditsTitleLabel.setStyle(titleLabelStyle);
        creditsTitleLabel.setAlignment(Align.center);
        mGuiStage.addActor(creditsTitleLabel);
        
        currentControlY -= 2.0f * smallTextHeight + controlPadding;
        
        Label creditsTextLabel = new Label(
                "Dedicated to: Marija Suchy :)\n" +
                        "Created by: Goran Mrzljak (goran.mrzljak@gmail.com)", mGuiSkin);
        creditsTextLabel.setBounds(controlPadding, currentControlY, HellHopper.VIEWPORT_WIDTH, titleLabelHeight);
        creditsTextLabel.setAlignment(Align.left);
        mGuiStage.addActor(creditsTextLabel);
        
        final float buttonWidth = 360.0f;
        final float buttonHeight = 80.0f;
        final float buttonX = (HellHopper.VIEWPORT_WIDTH - buttonWidth) / 2.0f;
        final float buttonY = 10.0f;
        
        TextureRegion backUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE));
        Drawable backUpDrawable = new TextureRegionDrawable(backUpTextureRegion);
        TextureRegion backDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE));
        Drawable backDownDrawable = new TextureRegionDrawable(backDownTextureRegion);
        ImageButton backButton = new ImageButton(backUpDrawable, backDownDrawable);
        backButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        backButton.addListener(getBackInputListener(backButton));
        mGuiStage.addActor(backButton);
    }
    
    private static InputListener getStageInputListener(final InfoScreen screen) {
        
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
    
    private InputListener getBackInputListener(final Actor actor) {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                }
            }
        };
    }
}
