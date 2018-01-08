package com.symbolplay.gamelibrary.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionUtil {
    
    private static final String ALGORITHM_KEY = "AES";
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    
    public static byte[] encryptData(byte[] clearData, EncryptionInputData encryptionInputData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        
        Cipher cipher = getCypher(Cipher.ENCRYPT_MODE, encryptionInputData);
        byte[] encrypted = cipher.doFinal(clearData);
        return encrypted;
    }
    
    public static byte[] decryptData(byte[] encryptedData, EncryptionInputData encryptionInputData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        
        Cipher cipher = getCypher(Cipher.DECRYPT_MODE, encryptionInputData);
        byte[] clearData = cipher.doFinal(encryptedData);
        return clearData;
    }
    
    private static Cipher getCypher(int mode, EncryptionInputData encryptionInputData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        
        byte[] skey = HexUtils.hexToBytes(encryptionInputData.getSkeyString());
        byte[] iv = HexUtils.hexToBytes(encryptionInputData.getIvString());
        
        SecretKeySpec skeySpec = new SecretKeySpec(skey, ALGORITHM_KEY);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, skeySpec, new IvParameterSpec(iv));
        
        return cipher;
    }
}
