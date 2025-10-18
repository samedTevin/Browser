package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.controlsfx.control.textfield.TextFields;
import utils.WebTab;
import utils.WebUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static controller.BrowserController.tab;

public class TabContentController implements Initializable {

    @FXML
    private WebView webView;
    @FXML
    private TextField tabSearchField;
    @FXML
    private CheckMenuItem checkTabFullscreen;
    @FXML
    private CheckMenuItem tabBlockPop;
    @FXML
    private CheckMenuItem tabVerifySecurity;
    @FXML
    private MenuButton tabBookmarkMenu;
    @FXML
    private TextField tabTextSearch;

    private WebEngine webEngine;

    public static TabPane tabPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        tabHome();
        WebUtil.urlFilter(webEngine);
        WebUtil.setupHistoryTracking(webEngine);
        tabSelectBookmark();
        TextFields.bindAutoCompletion(tabSearchField,WebUtil.bookmarks);
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

    @FXML
    private void tabRefresh(){
        WebUtil.refresh(webEngine);
    }

    @FXML
    private void tabHome(){
        WebUtil.home(WebUtil.currentBrowser,tabSearchField,webEngine);
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
    private void tabFullscreen(){
        WebUtil.fullScreen(checkTabFullscreen);
    }

    @FXML
    private void tabAddBookmark(){
        WebUtil.addBookmark(webView);
        tabSelectBookmark();
        TextFields.bindAutoCompletion(tabSearchField,WebUtil.bookmarks);
    }

    @FXML
    private void tabHistoryTab(){
        try{
            tab = new Tab("History");
            FXMLLoader loader = new FXMLLoader(WebTab.class.getResource("/view/HistoryTab.fxml"));
            Parent root = loader.load();
            tab.setContent(root);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
            tabPane.getSelectionModel().select(tab);
            HistoryTabController controller = loader.getController();
            controller.setWebEngine(webEngine);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tabPrintPage(){
        WebUtil.printPage(webView);
    }

    @FXML
    private void tabTakeScreenshot(){
        WebUtil.screenshot(webView);
    }

    @FXML
    private void tabSetGoogle(){
        WebUtil.setGoogle(webEngine,tabSearchField);
    }

    @FXML
    private void tabSetYandex(){
        WebUtil.setYandex(webEngine,tabSearchField);
    }

    @FXML
    private void tabSetDuckDuckGo(){
        WebUtil.setDuck(webEngine,tabSearchField);
    }

    @FXML
    private void tabSetBing(){
        WebUtil.setBing(webEngine,tabSearchField);
    }

    @FXML
    private void tabSetYahoo(){
        WebUtil.setYahoo(webEngine,tabSearchField);
    }


    @FXML
    private void tabBlockPopup(){
        WebUtil.blockPopup(webEngine,tabBlockPop);
    }

    @FXML
    private void tabVerifySiteSecurity(){
        WebUtil.verifySite(webEngine,tabVerifySecurity);
    }

    @FXML
    private void tabSearchText(){
        if(tabTextSearch == null){
            return;
        }
        WebUtil.searchText(webView, tabTextSearch.getText().trim());
    }

    @FXML
    private void tabSelectBookmark(){
        WebUtil.selectBookmark(tabBookmarkMenu,webView);
    }

    @FXML
    private void tabUrlFilter(){
        WebTab.createTab(tab,tabPane,"URL Filter","/view/URLFilter.fxml");
    }


}
