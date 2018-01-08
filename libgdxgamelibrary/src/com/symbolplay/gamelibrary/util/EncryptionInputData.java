package com.symbolplay.gamelibrary.util;

public final class EncryptionInputData {
    
    private final String skeyString;
    private final String ivString;
    
    public EncryptionInputData(String skeyString, String ivString) {
        this.skeyString = skeyString;
        this.ivString = ivString;
    }
    
    public String getSkeyString() {
        return skeyString;
    }
    
    public String getIvString() {
        return ivString;
    }
}
