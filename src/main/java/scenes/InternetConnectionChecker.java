/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import com.mycompany.vkmessenger.Window;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author odour
 */
public class InternetConnectionChecker {

    public static void check() {
        if (Window.getAutorizeWindow() != null) {
            Window.getAutorizeWindow().close();
        }
        if (Window.getWindow() != null) {
            Window.getWindow().close();
        }
        if (Window.getSettingsWindow() != null) {
            Window.getSettingsWindow().close();
        }
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().add(new Text(25, 25, "Problems with internet connection!"));
        vbox.getChildren().add(new Text(25, 25, "Please check your internet"));
        vbox.getChildren().add(new Text(25, 25, "connection and restart app."));
        
        Scene scene = new Scene(vbox, 275, 90);
        dialog.setScene(scene);
        dialog.setTitle("No internet connection");
        dialog.show();
    }
}
