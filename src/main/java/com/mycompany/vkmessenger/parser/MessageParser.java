/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.parser;

import com.mycompany.vkmessenger.Token;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scenes.InternetConnectionChecker;

/**
 *
 * @author odour
 */
public class MessageParser implements Parser {

    private final LinkedHashSet<Message> messageSet = new LinkedHashSet<>();
    private String inputLineForParsing;
    private String userID;
    
    private String[] parsedMessage;

    //public MessageParser() {
       // if (Token.getToken() != null) {
        //    messageSet.clear();
        //    downloadMessageString();
        //    parseInputLineToEntities();
       // }
   // }

    @Override
    public LinkedHashSet getCollection(String uid) {
        if (Token.getToken() != null) {
            userID = uid;
            messageSet.clear();
            downloadMessageString();
            try {
                parseInputLineToEntities();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
            }
            return messageSet;
        } else {
            return null;
        }
    }

    @Override
    public Message getById(String mesid) {
        Iterator<Message> iterator = messageSet.iterator();
        for (int i = 0; i < messageSet.size(); i++) {
            Message eachMessage = iterator.next();
            if (mesid.equals(eachMessage.getMessageID())) {
                return eachMessage;
            }
        }
        return null;
    }

    private void downloadMessageString() {
        String url = "https://api.vk.com/method/"
                + "messages.getHistory"
                + "?uid=" + userID
                + "&access_token=" + com.mycompany.vkmessenger.Token.getToken();
        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
                inputLineForParsing = reader.readLine();
            //    System.out.println(inputLineForParsing);
            }
        } catch (MalformedURLException e) {
           // InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        } catch (IOException e) {
          //  InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        }
    }

    private void parseInputLineToEntities() throws UnsupportedEncodingException {
       // String messagePart;
        
        try {
            JSONArray response = null;
            try {
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
             response = jsonObj.getJSONArray("response");
            }
            catch(Exception e) {
         //   InternetConnectionChecker.check();
            System.out.print(e.getMessage());
            }

            int numberOfFriends = response.length();
            for (int i = 1; i < numberOfFriends; ++i) {
                JSONObject friend = response.getJSONObject(i);
                parseLineForBRTag(friend.getString("body"));
                
                for (int j = 0; j < parsedMessage.length; j++) {
                //messagePart = parsedMessage[j];
                
                if ("1".equals(friend.getString("out"))) {
                    messageSet.add(new Message(StringEscapeUtils.unescapeHtml4(parsedMessage[j]), "I", friend.getString("mid")));
                } else {
                    messageSet.add(new Message(StringEscapeUtils.unescapeHtml4(parsedMessage[j]), "He", friend.getString("mid")));
                }
            }
            }
        } catch (JSONException e) {
        //    InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        }
    }
    
    private void parseLineForBRTag(String line) {//String[] parseLineForBRTag(String line) {
        parsedMessage = line.split("<br>");
      //  return parsedMessage;
    }
    
    
}
