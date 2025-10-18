package controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import utils.WebUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HistoryTabController {



    @FXML
    private ListView<String> historyList;
    private WebHistory history;
    private WebEngine webEngine;
    private static final String HISTORY_FILE = "src/WebHistory/history.txt";


    public void setWebEngine(WebEngine webEngine) {
        this.webEngine = webEngine;
        loadHistoryFromFile();

    }


    private void loadHistoryFromFile()  {
        File file = new File("src/WebHistory/history.txt");
        try {
            ObservableList<String> entries = FXCollections.observableArrayList();
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                entries.add(line);
            }
            scanner.close();
            historyList.setItems(entries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void removeHistory(){
        int selectedIndex = historyList.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            String toRemove = historyList.getItems().get(selectedIndex);
            File file = new File("src/WebHistory/history.txt");
            try{
                List<String> entries = new ArrayList<>();
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(!line.equals(toRemove)){
                        entries.add(line);
                    }
                }

                scanner.close();

                historyList.getItems().remove(selectedIndex);


                BufferedWriter writer = new BufferedWriter(new FileWriter(file,false));
                for(String entry : entries){
                    writer.write(entry);
                    writer.newLine();
                }
                writer.flush();
                writer.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    private void removeAllHistory(){
        File file = new File("src/WebHistory/history.txt");
        historyList.getItems().clear();
        try{
            new FileWriter(file,false).close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
