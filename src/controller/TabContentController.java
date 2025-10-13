package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utils.WebUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.BrowserController.tab;

public class TabContentController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private TextField tabSearchField;
    private double tabWebZoom;

    private WebEngine webEngine;

    public static TabPane tabPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");
        tabWebZoom = 1.0;
    }



    @FXML
    private void loadTabWeb(){
        WebUtil.setTab(tabPane,tabSearchField);
        WebUtil.loadWeb(tabSearchField,webEngine);
    }

    @FXML
    private void tabForward(){
        WebUtil.forward(webEngine);
    }

    @FXML
    private void tabBackward(){
        WebUtil.backward(webEngine);
    }

    private void tabHistory(){

    }

    @FXML
    private void tabZoomIn(){
        WebUtil.zoomIn(webView, tab);
    }

    @FXML
    private void tabZoomOut(){
        WebUtil.zoomOut(webView, tab);
    }

    @FXML
    private void tabRefresh(){
        WebUtil.refresh(webEngine);
    }

    @FXML
    private void tabHome(){
        WebUtil.home(webEngine, tabSearchField);
    }


}
