/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author odour
 */
package com.mycompany.vkmessenger.parser;

import com.mycompany.vkmessenger.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scenes.InternetConnectionChecker;

public final class FriendParser implements Parser {

    public static LinkedHashSet<Friend> friendSet = new LinkedHashSet<>();
    private String inputLineForParsing;
    private String userID;
  //  private Image wim;

    public FriendParser() {
        if (Token.getToken() != null) {
            friendSet.clear();
            downloadFriendString();
            parseInputLineToEntities();
        }
    }

    @Override
    public LinkedHashSet getCollection(String uid) {
        if (Token.getToken() != null) {
            userID = uid;
           // friendSet.clear();
           // downloadFriendString();
         //   parseInputLineToEntities();
            return friendSet;
        } else {
            return null;
        }
    }

    @Override
    public Friend getById(String uid) {
        Iterator<Friend> iterator = friendSet.iterator();
        for (int i = 0; i < friendSet.size(); i++) {
            Friend eachFriend = iterator.next();
            if (uid.equals(eachFriend.getUID())) {
                return eachFriend;
            }
        }
        return null;
    }

    public void downloadFriendString() {
        String url;
        if (userID == null) {
            url = "https://api.vk.com/method/"
                    + "friends.get"
                    + "?order=name"
                    + "&fields=photo_50,photo_100,sex"
                    + "&access_token=" + Token.getToken();
        } else {
            url = "https://api.vk.com/method/"
                    + "friends.get"
                    + "?uid=" + userID
                    + "&order=name"
                    + "&fields=photo_50,photo_100,sex"
                    + "&access_token=" + Token.getToken();
        }
        try {
            URL response = new URL(url);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.openStream(), "UTF-8"))) {
                inputLineForParsing = reader.readLine();
                //    System.out.println(inputLineForParsing);
            }
        } catch (MalformedURLException e) {
            System.out.print(e.getMessage());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private void parseInputLineToEntities() {
        try {
            JSONArray response = null;
            try {
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
             response = jsonObj.getJSONArray("response");
            }
            catch(Exception e) {
            InternetConnectionChecker.check();
            System.out.print(e.getMessage());
            }
            JSONObject jsonObj = new JSONObject(inputLineForParsing);
            response = jsonObj.getJSONArray("response");
            int numberOfFriends = response.length();
            for (int i = 0; i < numberOfFriends; ++i) {
                JSONObject friend = response.getJSONObject(i);
                friendSet.add(new Friend(friend.getString("uid"), friend.getString("first_name"),
                        friend.getString("last_name"), friend.getString("photo_50"),
                friend.getString("photo_100")));
            }
        } catch (JSONException e) {
            System.out.print(e.getMessage());
        }
    }

    private String savePhoto(String imgURL, String uid) {
        Image wim = new Image(imgURL);

        File file = new File("pictures/" + uid + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "pictures/" + uid + ".png";
    }
}
