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
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scenes.InternetConnectionChecker;

/**
 *
 * @author odour
 */
public class PhotoParser implements Parser {

    public static LinkedHashSet<Photo> photoSet = new LinkedHashSet<>();
    private String inputLineForParsing;
    private String userID;
   // private String imgURL;

    public PhotoParser() {
        if (Token.getToken() != null) {
            photoSet.clear();
            downloadPhotoString();
            parseInputLineToEntities();
        }
    }

    @Override
    public LinkedHashSet getCollection(String uid) {
        if (Token.getToken() != null) {
            userID = uid;
            photoSet.clear();
            downloadPhotoString();
            parseInputLineToEntities();
            return photoSet;
        } else {
            return null;
        }
    }

    @Override
    public Photo getById(String phid) {
        Iterator<Photo> iterator = photoSet.iterator();
        for (int i = 0; i < photoSet.size(); i++) {
            Photo eachPhoto = iterator.next();
            if (phid.equals(eachPhoto.getPhotoID())) {
                return eachPhoto;
            }
        }
        return null;
    }

    private void downloadPhotoString() {
        String url = "https://api.vk.com/method/"
                + "photos.getProfile"
                + "?owner_id=" + userID
                + "&access_token=" + Token.getToken();
        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
                inputLineForParsing = reader.readLine();
            }
        } catch (MalformedURLException e) {
         //   InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        } catch (IOException e) {
          //  InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        }
    }

    private void parseInputLineToEntities() {
        try {
            if ("error".equals(inputLineForParsing.split("\"")[1])) {
                photoSet.add(new Photo("http://vk.com/images/deactivated_c.gif",
                        "http://vk.com/images/deactivated_a.gif",
                        null));
            } else {
                JSONArray response = null;
            try {
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
             response = jsonObj.getJSONArray("response");
            }
            catch(Exception e) {
           // InternetConnectionChecker.check();
            System.out.print(e.getMessage());
            }
                JSONObject jsonObj = new JSONObject(inputLineForParsing);
                response = jsonObj.getJSONArray("response");

                if (response.length() != 0) {
                    int currentProfilePhotoNumber = response.length() - 1;
                    JSONObject profilePhoto = response.getJSONObject(currentProfilePhotoNumber);
                    photoSet.add(new Photo(profilePhoto.getString("src_small"),
                            profilePhoto.getString("src_big"),
                            profilePhoto.getString("pid")));
                } else {
                    photoSet.add(new Photo("http://vk.com/images/camera_c.gif",
                            "http://vk.com/images/camera_a.gif",
                            null));
                }
            }
        } catch (JSONException e) {
           // InternetConnectionChecker.check();
            System.out.print(e.getMessage());
        }
    }
}
