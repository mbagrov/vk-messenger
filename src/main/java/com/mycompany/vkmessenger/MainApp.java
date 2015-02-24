package com.mycompany.vkmessenger;

import com.mycompany.vkmessenger.encrypter.AESCipher;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import scenes.AutorizationScene;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeySpecException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, Exception {
        Window.setWindow(stage);
        Window.setAutorizeWindow(stage);
        Window.getAutorizeWindow().setTitle("Autorization");

        Scene scene = new Scene(new AutorizationScene(), 750, 500, Color.web("#666970"));

        Window.getAutorizeWindow().setScene(scene);
        Window.showAutorizeWindow();

       // AESCipher.using();
      //  AESCipher aes = new AESCipher("code");
      //  String ecnrypted = aes.encrypt("Привет, я петя");
      //  String decrypted = aes.decrypt(ecnrypted);
        
     //   System.out.println(ecnrypted + decrypted);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
