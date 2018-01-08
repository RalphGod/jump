package com.symbolplay.gamelibrary.persistence;

import java.io.UnsupportedEncodingException;

import com.badlogic.gdx.utils.Base64Coder;
import com.symbolplay.gamelibrary.util.EncryptionInputData;
import com.symbolplay.gamelibrary.util.EncryptionUtil;
import com.symbolplay.gamelibrary.util.ExceptionThrower;

// part of the code is from: http://stackoverflow.com/questions/6788018/android-encryption-decryption-with-aes
public final class DataConverter {
    
    private static final String CHARSET = "UTF-8";
    
    private EncryptionInputData encryptionInputData;
    
    public DataConverter(EncryptionInputData encryptionInputData) {
        this.encryptionInputData = encryptionInputData;
    }
    
    public String readConvert(byte[] fileData) {
        if (encryptionInputData != null) {
            return decrypt(fileData, encryptionInputData);
        } else {
            return toString(fileData);
        }
    }
    
    public byte[] writeConvert(String gameDataString) {
        if (encryptionInputData != null) {
            return encrypt(gameDataString, encryptionInputData);
        } else {
            return toByteArray(gameDataString);
        }
    }
    
    private static byte[] encrypt(String gameDataString, EncryptionInputData encryptionInputData) {
        try {
            String base64String = Base64Coder.encodeString(gameDataString);
            byte[] base64Data = base64String.getBytes(CHARSET);
            byte[] encryptedData = EncryptionUtil.encryptData(base64Data, encryptionInputData);
            return encryptedData;
            //return gameDataString.getBytes(CHARSET);
        } catch (Exception e) {
            ExceptionThrower.throwException(e.getMessage());
            return null;
        }
    }
    
    private static String decrypt(byte[] encryptedData, EncryptionInputData encryptionInputData) {
        try {
            byte[] base64Data = EncryptionUtil.decryptData(encryptedData, encryptionInputData);
            String base64String = new String(base64Data, CHARSET);
            String gameDataString = Base64Coder.decodeString(base64String);
            return gameDataString;
            //return new String(encryptedData, CHARSET);
        } catch (Exception e) {
            ExceptionThrower.throwException(e.getMessage());
            return null;
        }
    }
    
    private static byte[] toByteArray(String str) {
        try {
            return str.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            ExceptionThrower.throwException(e.getMessage());
            return null;
        }
    }
    
    private static String toString(byte[] data) {
        try {
            return new String(data, CHARSET);
        } catch (UnsupportedEncodingException e) {
            ExceptionThrower.throwException(e.getMessage());
            return null;
        }
    }
}
