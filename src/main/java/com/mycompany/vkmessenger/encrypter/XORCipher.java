/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.encrypter;

/**
 *
 * @author odour
 */
public class XORCipher implements Encrypter {

    private final String gamma;

    public XORCipher(String gam) {
        this.gamma = gam;
    }

    @Override
    public String getKey() {
        return this.gamma;
    }

    private int getCharCode(char ch) {
        return (int) ch;
    }

    private String getCharByCode(int charCode) {
        return Character.toString((char) charCode);
    }

    @Override
    public String encrypt(String message) {
        int j = 0;
        char messageChar;
        char gammaChar;
        String finalStr = "";
        int finalCode;
        int codeSum;

        finalStr += getCharByCode(2000) + getCharByCode(2020);
      //  finalStr += "XOR";
        for (int i = 0; i < message.length(); i++) {
            messageChar = message.charAt(i);
            gammaChar = this.gamma.charAt(j);

            codeSum = getCharCode(messageChar) + getCharCode(gammaChar);
            finalCode = codeSum % 2040;

            if (finalCode == 0) {
                finalCode = 2040;
            }

            j++;
            finalStr += getCharByCode(finalCode);
            if (j == gamma.length()) {
                j = 0;
            }
            //   System.out.println(Integer.toString(getCharCode(messageChar)) + " " + Integer.toString(getCharCode(gammaChar)));
        }
        //   System.out.println(finalStr);
        return finalStr;
    }

    @Override
    public String decrypt(String message) {
        int j = 0;
        char messageChar;
        char gammaChar;
        String finalStr = "";
        int finalCode;
        int codeSum;
        //       System.out.println(" ");
        for (int i = 2; i < message.length(); i++) {
            messageChar = message.charAt(i);
            gammaChar = this.gamma.charAt(j);

            codeSum = getCharCode(messageChar) - getCharCode(gammaChar) + 2040;
            finalCode = codeSum % 2040;

            if (finalCode == 0) {
                finalCode = 2040;
            }

            j++;
            finalStr += getCharByCode(finalCode);
            if (j == (gamma.length())) {
                j = 0;
            }
            // System.out.println(Integer.toString(getCharCode(messageChar)) + " " + Integer.toString(getCharCode(gammaChar)));
        }
        //    System.out.println(finalStr);
        return finalStr;
    }

    @Override
    public Boolean checkMessage(String message) {
        if (message.length() >= 2) {
            if (Character.toString(message.charAt(0)).equals(getCharByCode(2000))
                    && Character.toString(message.charAt(1)).equals(getCharByCode(2020))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
     /*   if (message.length() >= 3) {
            if ("XOR".equals(message.substring(0, 3))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }*/
}
