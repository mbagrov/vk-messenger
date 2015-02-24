/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.vkmessenger.parser;

/**
 *
 * @author odour
 */
public class Message {
    private String text;
    private String companion;
    private String messageID;
    
    public Message(String text, String comp, String mesID) {
        this.text = text;
        this.companion = comp;
        this.messageID = mesID;
    }
      
    public String getBody() {
        return this.text;
    }
    
    public String getCompanion() {
        return this.companion;
    }
    
    public String getMessageID() {
        return this.messageID;
    }  

}
