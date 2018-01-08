package com.symbolplay.gamelibrary.controls;

import org.apache.commons.lang3.StringUtils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.symbolplay.gamelibrary.util.GameUtils;

public class CustomImageButton extends Group {
    
    private final ObjectMap<Integer, ImageButtonStyle> styles;
    private final ImageButton button;
    private final Label label;
    
    private int stateId;
    
    private boolean isEnabled;
    
    public CustomImageButton(
            float positionX,
            float positionY,
            TextureAtlas atlas,
            String upImageName,
            String downImageName,
            String disabledImageName,
            String text,
            LabelStyle labelStyle) {
        
        this(
                positionX,
                positionY,
                atlas,
                createSingleStyleData(upImageName, downImageName, disabledImageName),
                text,
                labelStyle);
    }
    
    public CustomImageButton(
            float positionX,
            float positionY,
            TextureAtlas atlas,
            Array<CustomImageButtonStyleData> stylesData) {
        
        this(
                positionX,
                positionY,
                atlas,
                stylesData,
                null,
                null);
    }
    
    public CustomImageButton(
            float positionX,
            float positionY,
            TextureAtlas atlas,
            Array<CustomImageButtonStyleData> stylesData,
            String text,
            LabelStyle labelStyle) {
        
        styles = getStyles(atlas, stylesData);
        
        AtlasRegion region = atlas.findRegion(stylesData.get(0).getUpImageName());
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        
        setBounds(positionX, positionY, width, height);
        
        stateId = styles.keys().next();
        button = new ImageButton(styles.get(stateId));
        button.setSize(width, height);
        addActor(button);
        
        if (!StringUtils.isEmpty(text) && labelStyle != null) {
            label = new Label(text, labelStyle);
            label.setAlignment(Align.center);
            label.setSize(width, height);
            label.setTouchable(Touchable.disabled);
            addActor(label);
        } else {
            label = null;
        }
        setEnabled(true);
    }
    
    public int getStateId() {
        return stateId;
    }
    
    public void setState(int stateId) {
        this.stateId = stateId;
        button.setStyle(styles.get(stateId));
    }
    
    public boolean isEnabled() {
        return isEnabled;
    }
    
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        button.setDisabled(!isEnabled);
        if (label != null) {
            label.setColor(isEnabled ? Color.BLACK : Color.GRAY);
        }
    }
    
    public ImageButton getImageButton() {
        return button;
    }
    
    public boolean addListener(EventListener listener) {
        return button.addListener(listener);
    }
    
    private static Array<CustomImageButtonStyleData> createSingleStyleData(String upImageName, String downImageName, String disabledImageName) {
        Array<CustomImageButtonStyleData> stylesData = new Array<CustomImageButtonStyleData>(true, 1);
        stylesData.add(new CustomImageButtonStyleData(0, upImageName, downImageName, disabledImageName));
        return stylesData;
    }
    
    private static ObjectMap<Integer, ImageButtonStyle> getStyles(TextureAtlas atlas, Array<CustomImageButtonStyleData> stylesData) {
        ObjectMap<Integer, ImageButtonStyle> styles = new ObjectMap<Integer, ImageButtonStyle>(stylesData.size);
        for (CustomImageButtonStyleData styleData : stylesData) {
            ImageButtonStyle style = getStyle(atlas, styleData.getUpImageName(), styleData.getDownImageName(), styleData.getDisabledImageName());
            styles.put(styleData.getId(), style);
        }
        
        return styles;
    }
    
    private static ImageButtonStyle getStyle(TextureAtlas textureAtlas, String upImageName, String downImageName, String disabledImageName) {
        ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
        imageButtonStyle.up = GameUtils.getTextureDrawable(textureAtlas, upImageName);
        imageButtonStyle.down = GameUtils.getTextureDrawable(textureAtlas, downImageName);
        imageButtonStyle.disabled = GameUtils.getTextureDrawable(textureAtlas, disabledImageName);
        
        return imageButtonStyle;
    }
}
