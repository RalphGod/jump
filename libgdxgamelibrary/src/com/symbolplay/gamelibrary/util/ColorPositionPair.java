package com.symbolplay.gamelibrary.util;

import com.badlogic.gdx.graphics.Color;

public final class ColorPositionPair {
    
    private final Color color;
    private final float position;
    
    public ColorPositionPair(Color color, float position) {
        this.color = color;
        this.position = position;
    }
    
    public Color getColor() {
        return color;
    }
    
    public float getPosition() {
        return position;
    }
}
