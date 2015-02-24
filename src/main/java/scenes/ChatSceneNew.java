/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

//import com.mycompany.vkmessenger.EncryptAlgoritm;
import com.mycompany.vkmessenger.Messenger;
import com.mycompany.vkmessenger.SettingsSaver;
import com.mycompany.vkmessenger.Window;
import com.mycompany.vkmessenger.encrypter.XORCipher;
import com.mycompany.vkmessenger.parser.Friend;
import com.mycompany.vkmessenger.parser.FriendParser;
import com.mycompany.vkmessenger.parser.Message;
import com.mycompany.vkmessenger.parser.MessageParser;
import com.mycompany.vkmessenger.parser.Parser;
import com.mycompany.vkmessenger.parser.Photo;
import com.mycompany.vkmessenger.parser.PhotoParser;
import com.mycompany.vkmessenger.parser.UserInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 *
 * @author odour
 */
public class ChatSceneNew {

    private Parser messagesParser = new MessageParser();
    private Messenger messenger = new Messenger();
    private Parser friendParser = new FriendParser();
    private ObservableList messagesArray = FXCollections.observableArrayList();
    private ObservableList friendsArray = FXCollections.observableArrayList();
    private Scene currentScene;
    private Node sideBarNode;
    private Node companionInfoNode;
    private Node chatNode;
    private Node chatInterfaceNode;

    private String currentUserId;
    private int updateMessagesTime = 4000;
    private Timer updatingTimer = new Timer();
    //  private int currentChatLength;
    // private Parser friendParser = new MessageParser();

    /*ChatSceneNew(String uid) throws IOException {
     userID = uid;
     messenger = new Messenger(userID);

     updateMessagesArray();

     Parent root = FXMLLoader.load(getClass().getResource("/fxml/test.fxml"));
     currentScene = new Scene(root);
        
     EncryptAlgoritm.setEncrypter(new XORCipher("gamma"));
     }*/
    public ChatSceneNew() throws IOException {
        try {
            downloadSceneFromFile();
        } catch (IOException e) {
            System.out.println("Didn't download!");
        }
        findSideBarNode();
        findCompanionInfoNode();
        findChatNode();
        findChatInterfaceNode();
        buildProfilePhoto();
        buildProfileName();
        buildFriendListView();
        updateFriendList();
        buildFriendListViewEventListener();
        buildMessagesListView();
        buildSendMessageButton();
        buildTimerForUpdatingMessages();
        buildCloseMainWindowEvent();
        buildSettigsButton();
        SettingsSaver.readSettinsFromFile();
    }

    public Scene getScene() {
        return currentScene;
    }

    private void downloadSceneFromFile() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/chat.fxml"));
        currentScene = new Scene(root);
    }

    private void findSideBarNode() {
        SplitPane spl = (SplitPane) currentScene.lookup("#mainSplit");
        ObservableList<Node> nodes = spl.getItems();

        AnchorPane anch = (AnchorPane) nodes.get(0);

        sideBarNode = anch;
    }

    private void findCompanionInfoNode() {
        SplitPane spl = (SplitPane) currentScene.lookup("#mainSplit");
        ObservableList<Node> nodes = spl.getItems();

        AnchorPane anch = (AnchorPane) nodes.get(1);

        companionInfoNode = anch;
    }

    private void findChatNode() {
        SplitPane spl = (SplitPane) companionInfoNode.lookup("#contentSplit");
        ObservableList<Node> nodes = spl.getItems();

        AnchorPane anch = (AnchorPane) nodes.get(0);

        chatNode = anch;
    }

    private void findChatInterfaceNode() {
        SplitPane spl = (SplitPane) companionInfoNode.lookup("#contentSplit");
        ObservableList<Node> nodes = spl.getItems();

        AnchorPane anch = (AnchorPane) nodes.get(1);

        this.chatInterfaceNode = anch;
        //  GridPane pane = (GridPane) chatInterfaceNode.lookup("#gridForInterface");
        //  pane.setAlignment(Pos.CENTER);

    }

    private void buildProfilePhoto() throws IOException {
        Parser photos = new PhotoParser();
        LinkedHashSet<Photo> photoSet = photos.getCollection("");
        Iterator<Photo> iterator = photoSet.iterator();
        Photo profilePhoto = iterator.next();
        Image image = new Image(profilePhoto.getSmall());

        ImageView profile = (ImageView) sideBarNode.lookup("#profileImage");
        profile.setImage(image);
    }

    private void buildProfileName() {
        Label profileName = (Label) sideBarNode.lookup("#profileName");

        UserInfo info = new UserInfo();
        profileName.setText(info.getFirstName() + " " + info.getLastName());
    }

    private void updateFriendList() {
    //    Parser friends = new FriendParser();
        LinkedHashSet friendSet = friendParser.getCollection(null);
        /*  Iterator<Friend> iterator = friendSet.iterator();
         for (int i = 0; i < friendSet.size(); i++) {
         Friend eachFriend = iterator.next();
         friendsArray.add(eachFriend.getPhoto() + "/" + eachFriend.getFirstName() + " " + eachFriend.getLastName());
         }*/
        friendsArray.addAll(friendSet);
    }

    private void buildFriendListView() {
        ListView friendList = (ListView) sideBarNode.lookup("#friendListView");
        friendList.setItems(friendsArray);
        friendList.setCellFactory(new Callback<ListView<Friend>, ListCell<Friend>>() {
            @Override
            public ListCell<Friend> call(ListView<Friend> param) {
                return new XCell();
            }
        });
    }

    static class XCell extends ListCell<Friend> {

        HBox hbox = new HBox();
        ImageView photo = new ImageView();
        Label label = new Label();
        Image friendImage;

        public XCell() {
            super();
            hbox.getChildren().addAll(photo, label);

        }

        @Override
        protected void updateItem(Friend item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(null);

                label.setText(" " + item.getFirstName() + " " + item.getLastName());
                //  System.out.println(item.getPhoto());
                friendImage = new Image(item.getPhoto50(), true);
                photo.setFitHeight(30);
                photo.setFitWidth(30);

                photo.setImage(friendImage);
            //    photo.setCache(true);
                //   photo.setCacheHint(CacheHint.DEFAULT);

                //  photo.setPreserveRatio(true);
                if (item != null) {
                    setGraphic(hbox);
                }
            }
        }
    }

    private void buildFriendListViewEventListener() {
        final ListView listView = (ListView) sideBarNode.lookup("#friendListView");
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Friend>() {
            @Override
            public void changed(ObservableValue<? extends Friend> observable, Friend oldValue, Friend newValue) {
                // Your action here
                // System.out.println("Selected item: " + newValue);
                currentUserId = newValue.getUID();
                buildSelectedFriendPhoto(newValue.getPhoto100());
                buildSelectedFriendName(newValue.getFirstName(), newValue.getLastName());
                updateChatHistory();
                scrollChatListView();
            }
        });
    }

    private void buildSelectedFriendPhoto(String photoURL) {
        ImageView imgView = (ImageView) companionInfoNode.lookup("#selectedFriendImg");
        Image img = new Image(photoURL);
        imgView.setImage(img);
    }

    private void buildSelectedFriendName(String firstName, String lastName) {
        Label label = (Label) companionInfoNode.lookup("#selectedFriendName");
        label.setText(firstName + " " + lastName);
    }

    private void buildMessagesListView() {
        ListView mesList = (ListView) chatNode.lookup("#chatListView");
        // updateMessagesArray();
        mesList.setItems(messagesArray);
    }

    private void updateChatHistory() {
        LinkedHashSet messageSet = messagesParser.getCollection(currentUserId);
        messagesArray.remove(0, messagesArray.size());

        List reversedChat = reverseSet(messageSet);

        Iterator<Message> iterator = reversedChat.iterator();
        Friend curFriend  = friendParser.getById(currentUserId);

        for (int i = 0; i < reversedChat.size(); i++) {
            Message eachMes = iterator.next();

            if ("He".equals(eachMes.getCompanion()) || "She".equals(eachMes.getCompanion())) {
                if (curFriend.getDecrypter() != null) {
                    if (curFriend.getDecrypter().checkMessage(eachMes.getBody())) {
                        messagesArray.add(eachMes.getCompanion() + ": " + curFriend.getDecrypter().decrypt(eachMes.getBody()));
                    } else {
                        messagesArray.add(eachMes.getCompanion() + ": " + eachMes.getBody());
                    }
                } else {
                    messagesArray.add(eachMes.getCompanion() + ": " + eachMes.getBody());
                }
            } else if ("I".equals(eachMes.getCompanion())) {
                if (curFriend.getEncrypter() != null) {
                    if (curFriend.getEncrypter().checkMessage(eachMes.getBody())) {
                        messagesArray.add(eachMes.getCompanion() + ": " + curFriend.getEncrypter().decrypt(eachMes.getBody()));
                    } else {
                        messagesArray.add(eachMes.getCompanion() + ": " + eachMes.getBody());
                    }

                } else {
                    messagesArray.add(eachMes.getCompanion() + ": " + eachMes.getBody());
                }
            }
        }
        messagesArray.add("");
    }

    private void scrollChatListView() {
        final ListView listView = (ListView) this.chatNode.lookup("#chatListView");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                listView.scrollTo(messagesArray.size());
                listView.getSelectionModel().select(messagesArray.size());
            }
        });
    }

    private List reverseSet(Set set) {
        List list = new ArrayList(set);
        Collections.reverse(list);
        return list;
    }

    private void buildSendMessageButton() {
        Button sendBtn = (Button) chatInterfaceNode.lookup("#sendButton");
        //sendBtn.setId(currentUserId);
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                 Friend curFriend  = friendParser.getById(currentUserId);
                try {
                    TextArea mesArea = (TextArea) chatInterfaceNode.lookup("#enterMessageField");
                    if (curFriend.getEncrypter() != null) {
                        messenger.sendMessage(curFriend.getEncrypter().encrypt(mesArea.getText()), currentUserId);
                    } else {
                        messenger.sendMessage(mesArea.getText(), currentUserId);
                    }
                    mesArea.clear();
                    updateChatHistory();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }

    private class MessagesRefresher extends TimerTask {

        @Override
        public void run() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (currentUserId != null) {
                        updateChatHistory();
                    }
                }
            });
        }
    }

    private void buildTimerForUpdatingMessages() {
        // Timer timer = new Timer();
        TimerTask task = new MessagesRefresher();
        updatingTimer.schedule(task, updateMessagesTime, updateMessagesTime);
    }

    private void buildSettigsButton() {
      //  System.out.println(currentUserId);
        
        Button sendBtn = (Button) chatNode.lookup("#settingsButton");
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (currentUserId != null) {
                SettingsScene nextScene = null;
                try {
                    nextScene = new SettingsScene(currentUserId, friendParser);

                } catch (IOException ex) {
                    Logger.getLogger(ChatSceneNew.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                Stage nextStage = new Stage();
                nextStage.setScene(nextScene.getScene());
                Window.setSettingsWindow(nextStage);
                Window.getSettingsWindow().setTitle("Settings");
                nextStage.show();
            }
            }
        });
        
    }

    private void buildCloseMainWindowEvent() {
        Window.getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
              //   System.out.println("Stage is closing");
                updatingTimer.cancel();
            }
        });
    }
}
