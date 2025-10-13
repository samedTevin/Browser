package utils;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;



import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebUtil {

    private static WebHistory history;
    public static Stage stage;
    private static Map<Tab, Double> zoomLevels = new HashMap<Tab, Double>();
    private static double zoom;



    public static void loadWeb(TextField searchField, WebEngine webEngine) {
        if(searchField == null) {
            return;
        }

        if(searchField.getText().trim().isEmpty()){
            return;
        }

        String textField = searchField.getText().trim();
        String url;

        if(textField.startsWith("http://") || textField.startsWith("https://")) {
            url = textField;
        }
        else if(validateUrl(textField)){
            url = "http://" + textField;
        }
        else{
            url = "https://www.google.com/search?q=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
        }

        searchField.setText(url);
        webEngine.load(url);
    }


    public static boolean validateUrl(String url){
        String[] domains = {".com",".net",".org",".info",".biz",".gov",".edu",".mil",".int",
                ".tr",".uk",".de",".fr",".jp",".cn",".ru",".br",".au",".ca",
                ".in",".ir",".es",".it",".nl",".se",".ch",".us",".mx",".kr",
                ".co",".io",".ai"};

        for(String domain : domains){
            if(url.endsWith(domain)){
                return true;
            }
        }

        return false;
    }

    public static void setTab(TabPane tabPane, TextField textField){
        tabPane.getSelectionModel().getSelectedItem().setText(textField.getText());
    }

    public static void forward(WebEngine webEngine) {
        history = webEngine.getHistory();
        int currentIndex = history.getCurrentIndex();
        if(currentIndex < history.getEntries().size()-1){
            history.go(1);
        }
        else{
            WebAlerts.information("Already at the last page, cannot go forward!");
        }

    }

    public static void backward(WebEngine webEngine) {
        history = webEngine.getHistory();
        int currentIndex = history.getCurrentIndex();
        if (currentIndex > 0) {
            history.go(-1);
        }
        else{
            WebAlerts.information("Already at first page, you can't go back!");
        }

    }

    public static void zoomIn(WebView webView, Tab tab) {
        zoom = zoomLevels.getOrDefault(tab, 1.0);
        zoom += 0.1;
        webView.setZoom(zoom);
        zoomLevels.put(tab, zoom);
    }

    public static void zoomOut(WebView webView, Tab tab) {
        zoom = zoomLevels.getOrDefault(tab, 1.0);
        zoom -= 0.1;
        webView.setZoom(zoom);
        zoomLevels.put(tab, zoom);
    }

    public static void refresh(WebEngine webEngine) {
        webEngine.reload();
    }

    public static void home(WebEngine webEngine, TextField searchField) {
        webEngine.load("https://www.google.com");
        searchField.setText("Enter a URL or search on Google");
    }

    public static void history(WebEngine webEngine) {
        history = webEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
    }

    public static void printPage(WebView webView) {

        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if(printerJob == null){
            WebAlerts.information("No printer available!");
            return;
        }

        boolean proceed = printerJob.showPrintDialog(stage);
        if(!proceed) {
            WebAlerts.information("Printing cancelled");
            return;
        }


        printerJob.showPageSetupDialog(stage);

        boolean success = printerJob.printPage(webView);
        if(success){
            printerJob.endJob();
            WebAlerts.information("Printing completed successfully");
        }
        else{
            WebAlerts.information("Printing failed");
        }
    }

    public static void screenshot(WebView webView){
        WritableImage image = webView.snapshot(new SnapshotParameters(), null);
        File file = new File("C:\\Users\\PC\\Desktop\\browser\\Browser\\src\\screenshots\\screenshot_" + System.currentTimeMillis() + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            WebAlerts.information("Screenshot saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            WebAlerts.information("Screenshot failed");
        }
    }

    public static void searchText(WebView webView, String searchText){
        webView.getEngine().executeScript("window.find('" + searchText + "')");
    }

    public static void fullScreen(){
        stage.setFullScreen(true);
    }

}
