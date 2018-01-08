package com.symbolplay.gamelibrary.controls;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// http://stackoverflow.com/a/23324286/520229
public class AnimatedImage extends Image {
    
    protected Animation animation;
    private float stateTime;
    
    public AnimatedImage(Animation animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
        stateTime = 0;
    }
    
    @Override
    public void act(float delta) {
        stateTime += delta;
        ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(stateTime, true));
        super.act(delta);
    }
}