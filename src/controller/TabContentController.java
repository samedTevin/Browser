package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class TabContentController implements Initializable {

    @FXML
    private WebView web;

    private WebEngine webEngine;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = web.getEngine();
        webEngine.load("https://www.google.com");
    }
}
