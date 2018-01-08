package com.symbolplay.gamelibrary.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public final class GameUtils {
    
    // MATH
    public static final float EPSILON = 1e-5f;
    
    public static float getPositiveModulus(float value, float mod) {
        return ((value % mod) + mod) % mod;
    }
    
    public static int getPositiveModulus(int value, int mod) {
        return ((value % mod) + mod) % mod;
    }
    
    public static boolean moveToDestination(Vector2 position, Vector2 destination, float moveDistance) {
        if (position.dst2(destination) <= moveDistance * moveDistance) {
            return true;
        } else {
            float distX = destination.x - position.x;
            float distY = destination.y - position.y;
            
            float moveRads = MathUtils.atan2(distY, distX);
            position.x += moveDistance * MathUtils.cos(moveRads);
            position.y += moveDistance * MathUtils.sin(moveRads);
            
            return false;
        }
        
    }
    
    public static float getRandomPosition(float current, float minChange, float maxChange, float minValue, float maxValue) {
        float range1Min = Math.max(current - maxChange, minValue);
        float range1Max = current - minChange;
        float range1 = range1Max - range1Min;
        boolean isRange1 = range1 >= 0.0f;
        
        float range2Min = current + minChange;
        float range2Max = Math.min(current + maxChange, maxValue);
        float range2 = range2Max - range2Min;
        boolean isRange2 = range2 >= 0.0f;
        
        if (isRange1 && isRange2) {
            if (range1 == 0 && range2 == 0) {
                return MathUtils.randomBoolean() ? range1Min : range2Min;
            } else {
                float randomPosition = MathUtils.random(range1 + range2);
                if (randomPosition <= range1) {
                    return range1Min + randomPosition;
                } else {
                    return range2Min + (randomPosition - range1);
                }
            }
            
        } else if (isRange1) {
            return MathUtils.random(range1Min, range1Max);
        } else if (isRange2) {
            return MathUtils.random(range2Min, range2Max);
        } else {
            return 0.0f;
        }
    }
    
    public static float getTriangularFraction(float value, float interval) {
        return getTriangularFraction(value, interval, 0.5f);
    }
    
    public static float getTriangularFraction(float value, float interval, float center) {
        value %= interval;
        float peakPosition = interval * center;
        return value <= peakPosition ? value / peakPosition : (interval - value) / (interval - peakPosition);
    }
    
    public static int[] getRandomIntegers(int range, int count) {
        int[] values = new int[count];
        for (int i = 0; i < values.length; i++) {
            values[i] = MathUtils.random(range - 1);
        }
        
        return values;
    }
    
    public static Array<Integer> getRandomIndexes(int range, int numberOfIndexes, int offset) {
        return getRandomIndexes(range, numberOfIndexes, offset, null);
    }
    
    public static Array<Integer> getRandomIndexes(int range, int numberOfIndexes, int offset,
            Array<Integer> excludedIndexes) {
        
        Array<Integer> selectedList = new Array<Integer>(numberOfIndexes);
        
        if (numberOfIndexes > 0) {
            Array<Integer> availableList = getRange(range);
            
            if (excludedIndexes != null) {
                for (Integer excludedIndex : excludedIndexes) {
                    availableList.removeValue(excludedIndex, false);
                }
            }
            
            for (int i = 0; i < numberOfIndexes; i++) {
                int selectedIndex = MathUtils.random(availableList.size - 1);
                int selected = availableList.get(selectedIndex);
                selectedList.add(selected + offset);
                availableList.removeIndex(selectedIndex);
            }
            
            selectedList.sort();
        }
        
        return selectedList;
    }
    
    public static Array<Integer> getRange(int range) {
        return getRange(0, range);
    }
    
    public static Array<Integer> getRange(int start, int range) {
        Array<Integer> rangeList = new Array<Integer>(true, range);
        int end = start + range;
        for (int i = start; i < end; i++) {
            rangeList.add(i);
        }
        
        return rangeList;
    }
    
    public static int getRandomIndexByCumulativeFractions(float[] cumulativeFractions) {
        float randomNumber = MathUtils.random();
        for (int i = 0; i < cumulativeFractions.length; i++) {
            if (randomNumber <= cumulativeFractions[i]) {
                return i;
            }
        }
        
        return cumulativeFractions.length - 1;
    }
    
    public static float getSum(float[] values) {
        float sum = 0.0f;
        for (float value : values) {
            sum += value;
        }
        
        return sum;
    }
    
    // COLLECTIONS
    public static boolean contains(int[] array, int value) {
        for (int currentValue : array) {
            if (currentValue == value) {
                return true;
            }
        }
        
        return false;
    }
    
    // PARSING
    public static int[] parseIntArray(String valuesString) {
        String[] valueStrings = valuesString.split(",");
        
        int[] values = new int[valueStrings.length];
        for (int i = 0; i < valueStrings.length; i++) {
            values[i] = Integer.parseInt(valueStrings[i]);
        }
        
        return values;
    }
    
    public static float[] parseFloatArray(String valuesString) {
        String[] valueStrings = valuesString.split(",");
        
        float[] values = new float[valueStrings.length];
        for (int i = 0; i < valueStrings.length; i++) {
            values[i] = Float.parseFloat(valueStrings[i]);
        }
        
        return values;
    }
    
    // GRAPHICS
    public static void multiplySpriteSize(Sprite sprite, float multiplier) {
        sprite.setSize(sprite.getWidth() * multiplier, sprite.getHeight() * multiplier);
    }
    
    public static void setRgb(Color dest, Color src) {
        dest.r = src.r;
        dest.g = src.g;
        dest.b = src.b;
    }
    
    public static Drawable getTextureDrawable(TextureAtlas textureAtlas, String imageName) {
        TextureRegion textureRegion = textureAtlas.findRegion(imageName);
        return new TextureRegionDrawable(textureRegion);
    }
    
    // NUMBERS
    public static int compareInteger(int x1, int x2) {
        return x1 - x2;
    }
}
