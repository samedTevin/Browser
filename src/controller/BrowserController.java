package controller;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utils.WebUtil;



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
    private WebEngine webEngine;
    public static Tab tab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");
        TabContentController.tabPane = tabPane;
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
        WebUtil.home(webEngine,searchField);
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
        WebUtil.fullScreen();
    }


}
