package com.symbolplay.gamelibrary.resources;

import com.badlogic.gdx.math.MathUtils;

public final class IndexedResourceName {
    
    private final String baseName;
    private final String extension;
    private final int numIndexes;
    
    public IndexedResourceName(String baseName, String extension, int numIndexes) {
        this.baseName = baseName;
        this.extension = extension;
        this.numIndexes = numIndexes;
    }
    
    public int getNumIndexes() {
        return numIndexes;
    }
    
    public String getResourceName(int index) {
        return baseName + (index < 10 ? "0" + index : index) + extension;
    }
    
    public String getRandomResourceName() {
        int index = MathUtils.random(numIndexes - 1);
        return getResourceName(index);
    }
}
