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
public interface Encrypter {
    public String encrypt(String mes);
    public String decrypt(String mes);
    public Boolean checkMessage(String mes);
    public String getKey();
}
