package com.mycompany.vkmessenger.parser;

import com.mycompany.vkmessenger.encrypter.Encrypter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author odour
 */
//import java.util.*;
public class Friend {

    private String uid;
    private String firstName;
    private String lastName;
    private String profilePhoto50;
    private String profilePhoto100;
    private Encrypter encrypter;
    private Encrypter decrypter;
    private String algorytmType;

    public Friend(String id, String first, String last, String photo50, String photo100) {
        this.uid = id;
        this.firstName = first;
        this.lastName = last;
        this.profilePhoto50 = photo50;
        this.profilePhoto100 = photo100;
    }

    public String getUID() {
        return this.uid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoto50() {
        return this.profilePhoto50;
    }

    public String getPhoto100() {
        return this.profilePhoto100;
    }

    public void setEncrypter(Encrypter enc) {
        if (enc != null) {
        algorytmType = enc.getClass().getSimpleName();
        encrypter = enc;
          } else {
            encrypter = enc;
            algorytmType = null;
        }
    }

    public void setDecrypter(Encrypter dec) {
         if (dec != null) {
        algorytmType = dec.getClass().getSimpleName();
        decrypter = dec;
          } else {
            decrypter = dec;
            algorytmType = null;
        }
    }

    public Encrypter getEncrypter() {
        return encrypter;
    }

    public Encrypter getDecrypter() {
        return decrypter;
    }
    
    public String getAlgorytmType() {
        return algorytmType;
    }
}
