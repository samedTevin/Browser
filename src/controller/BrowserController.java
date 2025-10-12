package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class BrowserController implements Initializable {

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    @FXML
    private TextField searchField;

    private double webZoom;

    private WebHistory history;

    @FXML
    private TabPane tabPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");
        webZoom = 1;
    }

    public void loadWeb() {

        if(searchField.getText().trim().isEmpty() || searchField == null){
            return;
        }
        String textField = searchField.getText().trim();
        String url;

        if(textField.startsWith("http://") || textField.startsWith("https://")) {
            url = textField;
        }
        else if(textField.contains(".")){
            url = "https://" + textField;
        }
        else{
            url = "https://www.google.com/search?q=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
        }
        searchField.clear();
        searchField.setText(url);
        webEngine.load(url);
    }

    public void validateUrl(){

    }

    public void forward(){
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
       history.go(1);
    }

    public void backward(){
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(-1);
    }

    public void History(){
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
    }

    public void zoomIn(){
        webZoom+=0.25;
        webView.setZoom(webZoom);
    }

    public void zoomOut(){
        webZoom-=0.25;
        webView.setZoom(webZoom);
    }

    public void refresh(){
        webEngine.reload();
    }

    public void home(){
        webEngine.load("https://www.google.com");
    }

    public void addTab(){
        try{
            Tab tab = new Tab("New Page");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TabContent.fxml"));
            Parent controller = loader.load();

            tab.setContent(controller);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
            tabPane.getSelectionModel().select(tab);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
