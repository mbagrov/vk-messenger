/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger.parser;

import com.mycompany.vkmessenger.Token;
import static com.mycompany.vkmessenger.parser.FriendParser.friendSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scenes.InternetConnectionChecker;

/**
 *
 * @author odour
 */
public final class UserInfo {

    private String inputLineForParsing;
    private String firstName;
    private String lastName;

    public UserInfo() {
        if (Token.getToken() != null) {
            downloadFriendString();
            parseInputLineToEntities();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void downloadFriendString() {
        String url;
        url = "https://api.vk.com/method/"
                + "account.getProfileInfo"
                + "?access_token=" + Token.getToken();

        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
                inputLineForParsing = reader.readLine();
           //     System.out.println(inputLineForParsing);
            }
        } catch (MalformedURLException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private void parseInputLineToEntities() {
        try {
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
            JSONObject response = jsonObj.getJSONObject("response");

            firstName = response.getString("first_name");
            lastName = response.getString("last_name");

        } catch (JSONException e) {
            System.out.print(e.getMessage());
           // InternetConnectionChecker.check();
        }
    }
}
