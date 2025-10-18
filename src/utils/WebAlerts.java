package utils;

import javafx.scene.control.Alert;

import java.util.Objects;

public class WebAlerts {

    public static void information(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(WebAlerts.class.getResource("/view/css/alert.css")).toExternalForm()
        );
        alert.showAndWait();

    }

    public static void warning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(WebAlerts.class.getResource("/view/css/alert.css")).toExternalForm()
        );
        alert.showAndWait();

    }
}
