package com.example.quanlytaikhoan.controllers.Alter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Encryption class to handle password encryption using SHA-256 and AES.
 */
public class Encryption {
    private static final String SECRET_KEY = "MySecretKey12345"; // 16 bytes key
    
    // SHA-256 (cho authentication)
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashbytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for(byte b : hashbytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while encrypting password", e);
        }
    }
    
    // AES Encryption (cho lưu trữ có thể decryptt)
    public static String encryptAES(String plainText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting with AES", e);
        }
    }
    
    // AES Decryption
    public static String decryptAES(String encryptedText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            return "*******"; // Trả về mask nếu không decrypt được
        }
    }
}
