package com.symbolplay.gamelibrary.controls;

public final class CustomImageButtonStyleData {
    
    private final int id;
    private final String upImageName;
    private final String downImageName;
    private final String disabledImageName;
    
    public CustomImageButtonStyleData(int id, String upImageName, String downImageName, String disabledImageName) {
        this.id = id;
        this.upImageName = upImageName;
        this.downImageName = downImageName;
        this.disabledImageName = disabledImageName;
    }
    
    public int getId() {
        return id;
    }
    
    public String getUpImageName() {
        return upImageName;
    }
    
    public String getDownImageName() {
        return downImageName;
    }
    
    public String getDisabledImageName() {
        return disabledImageName;
    }
}
