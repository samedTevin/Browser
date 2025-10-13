package utils;

import javafx.scene.control.Alert;

public class WebAlerts {

    public static void information(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
