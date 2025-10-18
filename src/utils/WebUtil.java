package utils;


import controller.HistoryTabController;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class WebUtil {

    public static WebHistory history;
    public static Stage stage;
    private static Map<Tab, Double> zoomLevels = new HashMap<Tab, Double>();
    private static double zoom;
    public static Set<String> bookmarks = new HashSet<>();
    public static String currentBrowser = "Google";
    public static Set<String> blockedUrls = new HashSet<>();
    private static HistoryTabController historyTabController;



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
            url = switch (currentBrowser) {
                case "Google" ->
                        "https://www.google.com/search?q=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
                case "Yandex" ->
                        "https://yandex.com/search/?text=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
                case "DuckDuckGo" ->
                        "https://duckduckgo.com/?q=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
                case "Bing" -> "https://www.bing.com/search?q" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
                default -> "https://search.yahoo.com/search?p=" + URLEncoder.encode(textField, StandardCharsets.UTF_8);
            };
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

    public static void home( String home, TextField searchField, WebEngine webEngine) {

        String homeUrl = switch (currentBrowser) {
            case "Google" -> "https://www.google.com";
            case "Yandex" -> "https://www.yandex.com";
            case "DuckDuckGo" -> "https://duckduckgo.com/";
            case "Bing" -> "https://www.bing.com";
            default -> "https://www.yahoo.com";
        };

        searchField.setPromptText("Search on " + currentBrowser + " or enter a URL");
        webEngine.load(homeUrl);
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
        File file = new File("src/screenshots" + System.currentTimeMillis() + ".png");
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

    public static void fullScreen(CheckMenuItem menuItem){
        stage.setFullScreen(menuItem.isSelected());
    }

    public static void addBookmark(WebView webView) {
        String url = webView.getEngine().getLocation();
        addBookmarkToFile(url);
    }

    public static void addBookmarkToFile(String url) {
            bookmarks.add(url);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/bookmarks/bookmark.txt", true));
                writer.write(url +"\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WebAlerts.information("Bookmark added successfully");
    }

    public static void readBookmark(){
        File file = new File("src/bookmarks/bookmark.txt");
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                bookmarks.add(line);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void selectBookmark(MenuButton menuButton, WebView webView){
        menuButton.getItems().clear();
        for(String bookmark : bookmarks){
            MenuItem menuItem = new MenuItem(bookmark);
            menuItem.setOnAction(event -> {webView.getEngine().load(bookmark);});
            menuButton.getItems().add(menuItem);
        }
    }

    public static void setGoogle(WebEngine webEngine, TextField search){
        currentBrowser = "Google";
        home(currentBrowser, search, webEngine);
    }

    public static void setYandex(WebEngine webEngine, TextField search){
        currentBrowser = "Yandex";
        home(currentBrowser, search, webEngine);
    }

    public static void setDuck(WebEngine webEngine, TextField search){
        currentBrowser = "DuckDuckGo";
        home(currentBrowser, search, webEngine);
    }

    public static void setBing(WebEngine webEngine, TextField search){
        currentBrowser = "Bing";
        home(currentBrowser, search, webEngine);
    }

    public static void setYahoo(WebEngine webEngine, TextField search){
        currentBrowser = "Yahoo";
        home(currentBrowser, search, webEngine);
    }


    public static void blockPopup(WebEngine webEngine, CheckMenuItem menuItem){
        if(menuItem.isSelected()){
            webEngine.setCreatePopupHandler(config ->{
                Platform.runLater(() -> WebAlerts.warning("Popup blocked!"));
            return null;
        });
        }
        else{
            webEngine.setCreatePopupHandler(null);
        }
    }

    public static void verifySite(WebEngine webEngine, CheckMenuItem menuItem){
            webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
                if(menuItem.isSelected()){
                    if(!newValue.startsWith("https://")){
                        Platform.runLater(() -> WebAlerts.warning("Unsecure URL: " + newValue));
                    }
                }
            });
    }

    private static String normalize(String url){
        if(url == null) return "";
        url = url.replaceFirst("https?://","").replaceFirst("www\\.","");
        return url.split("/")[0];
    }

    public static void urlFilter(WebEngine webEngine){
        webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
            String normNew = normalize(newValue);
            for(String site: blockedUrls){
                String url = normalize(site);
                if(normNew.equals(url)){
                    Platform.runLater(() -> {
                        webEngine.load("auto:blank");
                        WebAlerts.warning("Blocked URL: " + newValue);
                    });
                    break;
                }
            }
        });
    }

    public static void handleFavorite(javafx.event.ActionEvent event , WebView webView){
        MenuItem menuItem = (MenuItem) event.getSource();
        String itemName = menuItem.getText();

        String url = switch (itemName) {
            case "Instagram" -> "https://www.instagram.com";
            case "YouTube" -> "https://www.youtube.com";
            case "Facebook" -> "https://www.facebook.com";
            case "X" -> "https://x.com";
            case "WhatsApp" -> "https://web.whatsapp.com";
            default -> null;
        };

        if(url != null){
            webView.getEngine().load(url);
        }
    }

    public static void setupHistoryTracking(WebEngine webEngine) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, old, state) -> {
            if(state == Worker.State.SUCCEEDED){
                String url = webEngine.getLocation();
                if(url != null && !url.isEmpty() && !url.equals("about:blank")){
                    try (BufferedWriter w = new BufferedWriter(new FileWriter("src/WebHistory/history.txt", true))) {
                        w.write((webEngine.getTitle() != null ? webEngine.getTitle() : "Untitled") + "-" + url);
                        w.newLine();
                    } catch (Exception e) {}
                }
            }
        });
    }




}

