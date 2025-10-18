package controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import utils.WebUtil;


import java.io.*;
import java.net.URL;
import java.util.*;

public class UrlFilterTabController implements Initializable {
    @FXML
    private ListView<String> urlList;
    @FXML
    private TextField textField;
    @FXML
    private Label totalBlocked;
    @FXML
    private Label lastAdded;
    @FXML
    private Label lastRemoved;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(WebUtil.blockedUrls == null){
            WebUtil.blockedUrls = new HashSet<>();
        }
        readUrl();
        getUrls();
        updateLabels(null, null);
    }

    private void getUrls(){
        if(WebUtil.blockedUrls != null){
            urlList.setItems(FXCollections.observableArrayList(WebUtil.blockedUrls));
        }
    }

    @FXML
    private void addUrl(){

        if(textField == null || textField.getText().trim().isEmpty()){
            return;
        }

        try{
            String url = textField.getText().trim();
            urlList.getItems().add(url);
            WebUtil.blockedUrls.add(url);
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PC\\Desktop\\browser\\Browser\\src\\BlockedUrls\\blocked.txt",true));
            writer.write(url);
            writer.newLine();
            writer.close();

            updateLabels(url,null);

            textField.clear();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void readUrl(){
        File file = new File("C:\\Users\\PC\\Desktop\\browser\\Browser\\src\\BlockedUrls\\blocked.txt");
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                WebUtil.blockedUrls.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeUrl(){
        int selectedIndex = urlList.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            File file = new File("C:\\Users\\PC\\Desktop\\browser\\Browser\\src\\BlockedUrls\\blocked.txt");
            try{

                List<String> lines = new ArrayList<>();
                Scanner reader = new Scanner(file);
                while(reader.hasNextLine()){
                    lines.add(reader.nextLine());
                }
                reader.close();

                String toRemove = urlList.getItems().get(selectedIndex);

                lines.remove(toRemove);
                urlList.getItems().remove(selectedIndex);
                WebUtil.blockedUrls.remove(toRemove);

                BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
                for(String line : lines){
                    writer.write(line);
                    writer.newLine();
                }
                writer.close();

                updateLabels(null,toRemove);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void removeAllUrls(){
        File file = new File("C:\\Users\\PC\\Desktop\\browser\\Browser\\src\\BlockedUrls\\blocked.txt");
        urlList.getItems().clear();
        WebUtil.blockedUrls.clear();
        try {
            new FileWriter(file,false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        totalBlocked.setText("0");
        lastRemoved.setText("All URLs removed");
    }

    private void updateLabels(String added, String removed){
        totalBlocked.setText(String.valueOf(WebUtil.blockedUrls.size()));

        if(added != null) lastAdded.setText(added);

        if(removed !=null) lastRemoved.setText(removed);
    }

}
