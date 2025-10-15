package controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.textfield.TextFields;
import utils.WebUtil;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BrowserController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextField searchField;
    @FXML
    private TextField textSearch;
    @FXML
    private MenuButton bookmarkMenu;
    @FXML
    private MenuItem google;
    @FXML
    private MenuItem yandex;
    @FXML
    private MenuItem duck;
    private MenuItem[] menuItems = {google,yandex,duck};
    @FXML
    private CheckMenuItem checkFullscreen;
    @FXML
    private CheckMenuItem blockPop;
    @FXML
    private CheckMenuItem verifySecurity;
    private WebEngine webEngine;
    public static Tab tab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        home();
        TabContentController.tabPane = tabPane;
        selectBookmark();
        TextFields.bindAutoCompletion(searchField,WebUtil.bookmarks);
    }

    @FXML
    private void loadWeb() {
        WebUtil.setTab(tabPane,searchField);
        WebUtil.loadWeb(searchField,webEngine);
    }

    @FXML
    private void forward(){
       WebUtil.forward(webEngine);
    }

    @FXML
    private void backward(){
       WebUtil.backward(webEngine);
    }

    @FXML
    private void History(){

    }

    @FXML
    private void zoomIn(){
        WebUtil.zoomIn(webView, BrowserController.tab);
    }

    @FXML
    private void zoomOut(){
        WebUtil.zoomOut(webView, BrowserController.tab);
    }

    @FXML
    private void refresh(){
       WebUtil.refresh(webEngine);
    }

    @FXML
    private void home(){
        WebUtil.home(WebUtil.currentBrowser, searchField, webEngine);
    }

    @FXML
    private void addTab(){
        try{
            tab = new Tab("New Page");
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

    @FXML
    private void historyTab(){
        try{
            tab = new Tab("History");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HistoryTab.fxml"));
            Parent controller = loader.load();

            tab.setContent(controller);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
            tabPane.getSelectionModel().select(tab);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void printPage(){
        WebUtil.printPage(webView);
    }

    @FXML
    private void takeScreenshot(){
        WebUtil.screenshot(webView);
    }

    @FXML
    private void searchText(){
        if(textSearch == null){
            return;
        }
        WebUtil.searchText(webView, textSearch.getText().trim());
    }

    @FXML
    private void fullScreen(){
        WebUtil.fullScreen(checkFullscreen);
    }

    @FXML
    private void addBookmark() throws IOException {
        WebUtil.addBookmark(webView);
        selectBookmark();
        TextFields.bindAutoCompletion(searchField,WebUtil.bookmarks);
    }

    @FXML
    private void selectBookmark(){
        WebUtil.selectBookmark(bookmarkMenu,webView);
    }

    @FXML
    private void setGoogle(){
        WebUtil.setGoogle(webEngine,searchField);
    }

    @FXML
    private void setYandex(){
        WebUtil.setYandex(webEngine,searchField);
    }

    @FXML
    private void setDuck(){
        WebUtil.setDuck(webEngine,searchField);
    }

    @FXML
    private void setBing(){
        WebUtil.setBing(webEngine,searchField);
    }

    @FXML
    private void setYahoo(){
        WebUtil.setYahoo(webEngine,searchField);
    }

    @FXML
    private void blockPopup(){
        WebUtil.blockPopup(webEngine,blockPop);
    }

    @FXML
    private void verifySiteSecurity(){
        WebUtil.verifySite(webEngine,verifySecurity);
    }








}
