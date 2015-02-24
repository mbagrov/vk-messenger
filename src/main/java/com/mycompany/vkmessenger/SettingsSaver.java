/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger;

import com.mycompany.vkmessenger.encrypter.Encrypter;
import com.mycompany.vkmessenger.encrypter.XORCipher;
import com.mycompany.vkmessenger.parser.Friend;
import com.mycompany.vkmessenger.parser.FriendParser;
import com.mycompany.vkmessenger.parser.Message;
import com.mycompany.vkmessenger.parser.Parser;
import com.mycompany.vkmessenger.parser.UserID;
import java.io.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javafx.application.Application;

/**
 *
 * @author odour
 */
public class SettingsSaver {

    private static Parser friendParser = new FriendParser();
    private static UserID usid = new UserID();

    public static void saveSettingsToFile(Friend friend) {
        String rowForWriting = null;
        if (friend.getAlgorytmType() != null) {
            rowForWriting = friend.getUID() + " " + friend.getAlgorytmType() + " "
                    + friend.getEncrypter().getKey() + " " + friend.getDecrypter().getKey() + " ";// + "\r\n";
            writeNewStringToFile(rowForWriting);
        } else {
            deleteExistingStringFromFile(friend.getUID());
            return;
        }
    }

    private static void deleteExistingStringFromFile(String friendUid) {

        LinkedHashSet<String> settingsSet = new LinkedHashSet<>();
        StringBuilder newFileContent = new StringBuilder();

        File file = new File(System.getProperty("user.dir") + "/" + usid.getID() + ".conf");

        if (!file.exists()) {
            try {
            file.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String row = null;

            while ((row = reader.readLine()) != null) {
                settingsSet.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        Iterator<String> iterator = settingsSet.iterator();
        for (int i = 0; i < settingsSet.size(); i++) {
            String eachSett = iterator.next();

            if (friendUid.equals(eachSett.split(" ")[0])) {
                continue;
            }
            newFileContent.append(eachSett).append('\n');
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(newFileContent.toString());
            bw.close();
        } catch (Exception e) {

        }
    }

    private static void writeNewStringToFile(String newString) {
        LinkedHashSet<String> settingsSet = new LinkedHashSet<>();
        StringBuilder newFileContent = new StringBuilder();

        File file = new File(System.getProperty("user.dir") + "/" + usid.getID() + ".conf");
        
         if (!file.exists()) {
            try {
            file.createNewFile();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String row = null;

            while ((row = reader.readLine()) != null) {
                settingsSet.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        Boolean newRow = true;

        Iterator<String> iterator = settingsSet.iterator();
        for (int i = 0; i < settingsSet.size(); i++) {
            String eachSett = iterator.next();

            if ((newString.split(" ")[0]).equals(eachSett.split(" ")[0])) {
                eachSett = newString;
                newFileContent.append(eachSett).append('\n');
                newRow = false;
                continue;
            }
            newFileContent.append(eachSett).append('\n');
        }

        if (newRow) {
            newFileContent.append(newString).append('\n');
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(newFileContent.toString());
            bw.close();
        } catch (Exception e) {

        }
    }

    public static void readSettinsFromFile() {
     //   String workingDir = System.getProperty("user.dir");
	//   System.out.println("Current working directory : " + workingDir);
        if (friendParser.getCollection(null) != null) {

            File file = new File(System.getProperty("user.dir") + "/" + usid.getID() + ".conf");
          //  System.out.println(file.toString());
            if (file.exists()) {
                BufferedReader reader = null;

                try {
                    reader = new BufferedReader(new FileReader(file));
                    String row = null;

                    while ((row = reader.readLine()) != null) {
                        Friend friend;
                        friend = friendParser.getById(row.split(" ")[0]);

                        Encrypter enc = null;
                        Encrypter dec = null;

                        try {
                            enc = (Encrypter) createObject(row.split(" ")[1], row.split(" ")[2]);
                            dec = (Encrypter) createObject(row.split(" ")[1], row.split(" ")[3]);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        friend.setEncrypter(enc);
                        friend.setDecrypter(dec);
                        //System.out.println(enc.getKey());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    private static Object createObject(String className,//Constructor constructor,
            String argument) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> classType = Class.forName("com.mycompany.vkmessenger.encrypter." + className);
        Constructor<?> constructor = classType.getConstructor(String.class);
        Object object = constructor.newInstance(new Object[]{argument});
        return object;
    }
}
