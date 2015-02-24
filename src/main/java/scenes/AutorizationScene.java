/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import com.mycompany.vkmessenger.Token;
import com.mycompany.vkmessenger.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
//import javafx.stage.Stage;
//import scenes.FriendListScene;

/**
 *
 * @author odour
 */
public class AutorizationScene extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    //private Scene nexScene = new FriendListScene(null);
    private final String mainURL = "https://oauth.vk.com/authorize"
            + "?client_id=4264882"
            + "&scope=messages"
            + "&redirect_uri=https://oauth.vk.com/blank.html"
            + "&display=page&v=5.2"
            + "1&response_type=token";

    private void loadMainAuthPage() {
        webEngine.load(mainURL);
        checkInternetConnection();
    }

    private void setChangeLocationEventer() {
        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov,
                            Worker.State oldState, Worker.State newState) {
                       
                            // System.out.println(getLocationString());
                            
             
                           // Logger.getLogger(AutorizationScene.class.getName()).log(Level.SEVERE, null, ex);
                            
                        
                        
                        if (newState == Worker.State.SUCCEEDED) {
                            String parsedTokenFromURL = getLocationString().split("=")[1].split("&")[0];
                            if (parsedTokenFromURL.length() == 85) {
                                Token.setToken(parsedTokenFromURL);
                              //  FriendListScene nextScene = new FriendListScene();
                               // Window.getWindow().setScene(nextScene.getScene());
                                
                                ChatSceneNew nextScene = null;
                                try {
                                    nextScene = new ChatSceneNew();
                                } catch (IOException ex) {
                                    Logger.getLogger(AutorizationScene.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Window.getAutorizeWindow().close();
                               // Window.setWindow(new Stage());
                              //  Stage nextStage = ;
                                Window.getWindow().setScene(nextScene.getScene());
                                Window.getWindow().setTitle("Dialog");
                                Window.showWindow();
                                
                            }
                        }
                    }
                });
    }
    
    private void checkInternetConnection() {
       //System.out.println(InetAddress.getByName("vk.com"));
        Socket sock = new Socket();
        InetSocketAddress address = new InetSocketAddress("vk.com", 80);
        try {
        sock.connect(address, 3000);
        } 
        catch(Exception e) {
           // System.out.println("No connection");
            InternetConnectionChecker.check();
        } finally {
            try {
            sock.close();
        }
        
        catch (Exception e) {
                
                }
        }
        
    }
    
    private void setChangeLocationCheckerEventer() {
    webEngine.locationProperty().addListener(new ChangeListener<String>() {
      @Override public void changed(ObservableValue<? extends String> prop, final String before, final String after) {
      //  System.out.println("Loaded: " + after);
        Platform.runLater(new Runnable() {
          @Override public void run() {
            if (after == null || after.startsWith("https://vk.com/") ||
                    after.startsWith("http://vk.com/join?reg=1") ||
                    after.startsWith("https://vk.com/restore/")) {
           //   System.out.println("Access denied: " + after);
              webEngine.load(mainURL);          
            }  
          }  
        });
      }
    });
    }
    
    /*private Boolean checkLocationString(){
        if ("vk.com".equals(getLocationString().split("/")[2])) {
            return false; 
        } else {
            return true;
        }
    }*/

    private String getLocationString() {
        return webEngine.getLocation();
    }

    public AutorizationScene() {
        setChangeLocationEventer();
        setChangeLocationCheckerEventer();
        loadMainAuthPage();
        getChildren().add(browser);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 500;
    }
}
