package com.symbolplay.gamelibrary.controls;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public final class HighlightImageButton extends Group {
    
    private final ImageButton button;
    private final Image highlightOverlayImage;
    
    public HighlightImageButton(
            float positionX,
            float positionY,
            TextureAtlas atlas,
            String normalTexturePath,
            String highlightTexturePath,
            AssetManager assetManager) {
        
        TextureRegion normalRegion = atlas.findRegion(normalTexturePath);
        float width = normalRegion.getRegionWidth();
        float height = normalRegion.getRegionHeight();
        
        setBounds(positionX, positionY, width, height);
        
        button = new ImageButton(new TextureRegionDrawable(normalRegion));
        button.setSize(width, height);
        addActor(button);
        
        TextureRegion highlightRegion = atlas.findRegion(highlightTexturePath);
        highlightOverlayImage = new Image(new TextureRegionDrawable(highlightRegion));
        highlightOverlayImage.setSize(width, height);
        highlightOverlayImage.setTouchable(Touchable.disabled);
        highlightOverlayImage.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(1.0f, Interpolation.sineIn), Actions.fadeOut(1.0f, Interpolation.sineOut))));
        addActor(highlightOverlayImage);
        
        setHighlight(false);
    }
    
    public void setHighlight(boolean isHighlight) {
        highlightOverlayImage.setVisible(isHighlight);
    }
    
    public ImageButton getImageButton() {
        return button;
    }
    
    public boolean addListener(EventListener listener) {
        return button.addListener(listener);
    }
}
