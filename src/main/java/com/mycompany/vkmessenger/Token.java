/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.vkmessenger;

/**
 *
 * @author odour
 */
public class Token {
    private static String token = "";
    
    public static void setToken(String tok){
        token = tok;
    }    
    
    public static String getToken(){
        if (!token.equals("")){
            return token;
        } else {
            return null;
        }
    }
}
