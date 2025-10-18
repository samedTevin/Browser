package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class WebTab {

    public static void createTab(Tab tab, TabPane tabPane,String tabName, String fxmlPath){

        try{
            tab = new Tab(tabName);
            FXMLLoader loader = new FXMLLoader(WebTab.class.getResource(fxmlPath));
            Parent root = loader.load();
            tab.setContent(root);
            tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
            tabPane.getSelectionModel().select(tab);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
