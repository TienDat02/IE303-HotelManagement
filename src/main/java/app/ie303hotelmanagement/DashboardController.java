package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    private Button LogoutButton;

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login-Page.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
