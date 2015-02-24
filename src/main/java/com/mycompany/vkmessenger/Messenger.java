/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger;

import com.mycompany.vkmessenger.parser.MessageParser;
import com.mycompany.vkmessenger.parser.Parser;
import java.io.*;
import java.net.*;
import java.util.LinkedHashSet;
import scenes.InternetConnectionChecker;

/**
 *
 * @author odour
 */
public class Messenger {

    Parser messages;
  //  private String userID;
  //  private String inputLineForParsing;
    private String urlCodedMessage = "";

  //  public Messenger(String uid) {
     //   userID = uid;
      //  messages = new MessageParser();
  //  }

  //  public LinkedHashSet getMessages() {
   //     return messages.getCollection(userID);
   // }

    public void sendMessage(String message, String userID) {
        try {
            urlCodedMessage = URLEncoder.encode(message, "UTF8");
        } catch (UnsupportedEncodingException e) {
         //   System.out.print(e.getMessage());
            InternetConnectionChecker.check();
        }

        String url = "https://api.vk.com/method/"
                + "messages.send"
                + "?user_id=" + userID
                + "&message=" + urlCodedMessage
                + "&access_token=" + Token.getToken();
        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
               // inputLineForParsing = reader.readLine();
            }
        } catch (MalformedURLException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
