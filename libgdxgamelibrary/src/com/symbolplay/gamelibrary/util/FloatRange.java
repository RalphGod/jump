package com.symbolplay.gamelibrary.util;

public final class FloatRange {
    
    public float min;
    public float max;
    
    public FloatRange() {
        min = 0.0f;
        max = 0.0f;
    }
    
    public FloatRange(float min, float max) {
        this.min = min;
        this.max = max;
    }
}
