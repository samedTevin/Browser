package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.WebUtil;


import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    public static void main(String[] args) {
        Logger.getLogger("com.sun.javafx.webkit").setLevel(Level.SEVERE);
        Logger.getLogger("javafx.scene.web").setLevel(Level.OFF);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Browser.fxml")));
        WebUtil.stage = stage;
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/view/photos/BrowserLogo.png"))));
        stage.setTitle("Web Browser");
        stage.setScene(new Scene(root));
        stage.show();
    }
}