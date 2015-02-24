/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

//import com.mycompany.vkmessenger.EncryptAlgoritm;
import com.mycompany.vkmessenger.SettingsSaver;
import com.mycompany.vkmessenger.Window;
import com.mycompany.vkmessenger.encrypter.AESCipher;
import com.mycompany.vkmessenger.encrypter.XORCipher;
import com.mycompany.vkmessenger.parser.Friend;
import com.mycompany.vkmessenger.parser.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author odour
 */
public class SettingsScene {

    Scene currentScene;
    ToggleGroup encryptMethodRadioGroup;
    Node encryptTabNode;
    // String userID;
    // Parser friendParser;
    Friend currentFriend;// = friendParser.getById(userID);

    public SettingsScene(String uid, Parser friendPars) throws IOException {
        // userID = uid;
        currentFriend = friendPars.getById(uid);
        //  friendParser = friendPars;
        downloadSceneFromFile();
        findTabs();
        buildRadioButtons();
        buildEventListenerForRadioButtons();
        buildSaveButton();
    }

    public Scene getScene() {
        return currentScene;
    }

    private void downloadSceneFromFile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/test2.fxml"));
        currentScene = new Scene(root);
    }

    private void buildRadioButtons() {
        encryptMethodRadioGroup = new ToggleGroup();
        RadioButton noEnc = (RadioButton) encryptTabNode.lookup("#encRadioNo");
        RadioButton gammingEnc = (RadioButton) encryptTabNode.lookup("#encRadioGamming");
        RadioButton aesEnc = (RadioButton) encryptTabNode.lookup("#encRadioAES");

        //noEnc.setSelected(true);
        noEnc.setToggleGroup(encryptMethodRadioGroup);
        gammingEnc.setToggleGroup(encryptMethodRadioGroup);
        aesEnc.setToggleGroup(encryptMethodRadioGroup);

        noEnc.setUserData("default");
        gammingEnc.setUserData("gamming");
        aesEnc.setUserData("gamming");

        //  System.out.println(EncryptAlgoritm.getAlgorytmType());
        if ("XORCipher".equals(currentFriend.getAlgorytmType())) {
            gammingEnc.setSelected(true);
            TextField encField = (TextField) encryptTabNode.lookup("#encKey");
            TextField decField = (TextField) encryptTabNode.lookup("#decKey");

            encField.setVisible(true);
            decField.setVisible(true);
            encryptTabNode.lookup("#encKeyLabel").setVisible(true);
            encryptTabNode.lookup("#decKeyLabel").setVisible(true);

            // System.out.println(currentFriend.getEncrypter().toString());
            encField.setText(currentFriend.getEncrypter().getKey());
            decField.setText(currentFriend.getDecrypter().getKey());
        } else if ("AESCipher".equals(currentFriend.getAlgorytmType())) {
            aesEnc.setSelected(true);
            TextField encField = (TextField) encryptTabNode.lookup("#encKey");
            TextField decField = (TextField) encryptTabNode.lookup("#decKey");

            encField.setVisible(true);
            decField.setVisible(true);
            encryptTabNode.lookup("#encKeyLabel").setVisible(true);
            encryptTabNode.lookup("#decKeyLabel").setVisible(true);

            // System.out.println(currentFriend.getEncrypter().toString());
            encField.setText(currentFriend.getEncrypter().getKey());
            decField.setText(currentFriend.getDecrypter().getKey());
        } else {
            noEnc.setSelected(true);
        }
    }

    private void findTabs() {
        TabPane root = (TabPane) currentScene.lookup("#mainTabPane");
        ObservableList<Tab> tabs = root.getTabs();

        Tab encryptTab = tabs.get(0);
        encryptTabNode = encryptTab.getContent();
    }

    private void buildEventListenerForRadioButtons() {
        encryptMethodRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (encryptMethodRadioGroup.getSelectedToggle() != null) {
                    if (!"default".equals(encryptMethodRadioGroup.getSelectedToggle().getUserData().toString())) {
                        encryptTabNode.lookup("#encKey").setVisible(true);
                        encryptTabNode.lookup("#decKey").setVisible(true);
                        encryptTabNode.lookup("#encKeyLabel").setVisible(true);
                        encryptTabNode.lookup("#decKeyLabel").setVisible(true);
                    } else {
                        encryptTabNode.lookup("#encKey").setVisible(false);
                        encryptTabNode.lookup("#decKey").setVisible(false);
                        encryptTabNode.lookup("#encKeyLabel").setVisible(false);
                        encryptTabNode.lookup("#decKeyLabel").setVisible(false);
                    }
                }
            }
        });
    }

    private void buildSaveButton() {
        Button sendBtn = (Button) encryptTabNode.lookup("#saveEncBtn");
        //sendBtn.setId(currentUserId);
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RadioButton noEnc = (RadioButton) encryptTabNode.lookup("#encRadioNo");
                RadioButton gammingEnc = (RadioButton) encryptTabNode.lookup("#encRadioGamming");
                RadioButton aesEnc = (RadioButton) encryptTabNode.lookup("#encRadioAES");

                TextField enc = (TextField) encryptTabNode.lookup("#encKey");
                TextField dec = (TextField) encryptTabNode.lookup("#decKey");

                if (noEnc.isSelected()) {
                    currentFriend.setEncrypter(null);
                    currentFriend.setDecrypter(null);
                    Window.getSettingsWindow().close();
                } else if (gammingEnc.isSelected()) {
                    if (!"".equals(enc.getText()) && !"".equals(dec.getText())) {
                        currentFriend.setEncrypter(new XORCipher(enc.getText()));
                        currentFriend.setDecrypter(new XORCipher(dec.getText()));
                    }
                } else if (aesEnc.isSelected()) {
                    if (!"".equals(enc.getText()) && !"".equals(dec.getText())) {
                        try {
                            currentFriend.setEncrypter(new AESCipher(enc.getText()));
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException ex) {
                            Logger.getLogger(SettingsScene.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            currentFriend.setDecrypter(new AESCipher(dec.getText()));
                        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException ex) {
                            Logger.getLogger(SettingsScene.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
                Window.getSettingsWindow().close();
                SettingsSaver.saveSettingsToFile(currentFriend);
            }

        }
        );
    }
}
