/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.encrypter;

//import com.google.common.base.Charsets;
//import com.google.common.io.ByteStreams;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author odour
 */
public class AESCipher implements Encrypter {

    private SecretKeySpec secretKeySpec;
    private final String keyString;
    private final Cipher cipher;
    private IvParameterSpec ivspec;

    public AESCipher(String inpKey) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        keyString = inpKey;
        setAlgorytmKey(inpKey);
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        generateIvParam();
    }

    private void generateIvParam() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ivspec = new IvParameterSpec(iv);
    }

    private void setAlgorytmKey(String keyString) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] keyBuffer;
        keyBuffer = keyString.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keyBuffer = sha.digest(keyBuffer);
        keyBuffer = Arrays.copyOf(keyBuffer, 16); // use only first 128 bit

        secretKeySpec = new SecretKeySpec(keyBuffer, "AES");
    }

    private String getCharByCode(int charCode) {
        return Character.toString((char) charCode);
    }

    @Override
    public String encrypt(String mes) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AESCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] encryptedMessage = null;
        try {
            encryptedMessage = cipher.doFinal(mes.getBytes("UTF-8"));
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
            Logger.getLogger(AESCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        String str = getCharByCode(2000) + getCharByCode(2021) + Base64.encodeBase64String(encryptedMessage);
        return str;
    }

    @Override
    public String decrypt(String mes) {
        String message = mes.substring(2);

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AESCipher.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] decryptedMessage = null;
        try {
            decryptedMessage = cipher.doFinal(Base64.decodeBase64(message));
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
           // Logger.getLogger(AESCipher.class.getName()).log(Level.SEVERE, null, ex);
            return "Incorrect key";
        }
        String str = null;
        try {
            str = new String(decryptedMessage, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
          //  Logger.getLogger(AESCipher.class.getName()).log(Level.SEVERE, null, ex);
            return "Incorrect key";
        }
        return str;
    }

    @Override
    public Boolean checkMessage(String message) {
        if (message.length() >= 2) {
            if (Character.toString(message.charAt(0)).equals(getCharByCode(2000))
                    && Character.toString(message.charAt(1)).equals(getCharByCode(2021))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getKey() {
        return keyString;
    }
}
