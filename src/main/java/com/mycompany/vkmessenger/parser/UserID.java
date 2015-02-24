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
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author odour
 */
public class UserID {

    private String inputLineForParsing;
    private String id;

    public UserID() {
        if (Token.getToken() != null) {
            downloadFriendString();
            parseInputLineToEntities();
        }
    }

    public String getID() {
        return id;
    }

    public void downloadFriendString() {
        String url;
        url = "https://api.vk.com/method/"
                + "users.get"
                + "?access_token=" + Token.getToken();

        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
                inputLineForParsing = reader.readLine();
               // System.out.println(inputLineForParsing);
            }
        } catch (MalformedURLException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private void parseInputLineToEntities() {
        JSONArray response = null;
        try {
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
            //      JSONObject response = jsonObj.getJSONObject("response");
            response = jsonObj.getJSONArray("response");

            JSONObject friend = response.getJSONObject(0);
            id = friend.getString("uid");

        } catch (Exception e) {
            //   InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        }
    }
}
