// color conversion methods taken from:
//   http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c
package com.symbolplay.gamelibrary.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

public final class SpectrumColorInterpolator {
    
    private final ColorInterpolator colorInterpolator;
    private final Color result;
    
    private final Array<ColorPositionPair> colorPositionPairs;
    
    public SpectrumColorInterpolator(Array<ColorPositionPair> colorPositionPairs) {
        
        colorInterpolator = new ColorInterpolator();
        result = new Color();
        
        this.colorPositionPairs = colorPositionPairs;
        
        for (int i = 0; i < colorPositionPairs.size; i++) {
            Color color = colorPositionPairs.get(i).getColor();
            color.a = 1.0f;
        }
    }
    
    public Color getBackgroundColor(float spectrumFraction) {
        int numColors = colorPositionPairs.size;
        
        float colorPosition = spectrumFraction * getPosition(numColors - 1);
        
        if (colorPosition < getPosition(0)) {
            result.set(getColor(0));
        } else if (colorPosition >= getPosition(numColors - 1)) {
            result.set(getColor(numColors - 1));
        } else {
            int stopIndex = 0;
            while (colorPosition >= getPosition(stopIndex)) {
                stopIndex++;
            }
            
            float t = (colorPosition - getPosition(stopIndex - 1)) /
                    (getPosition(stopIndex) - getPosition(stopIndex - 1));
            
            result.set(colorInterpolator.interpolateColor(getColor(stopIndex - 1), getColor(stopIndex), t));
        }
        
        return result;
    }
    
    private Color getColor(int index) {
        return colorPositionPairs.get(index).getColor();
    }
    
    private float getPosition(int index) {
        return colorPositionPairs.get(index).getPosition();
    }
}
