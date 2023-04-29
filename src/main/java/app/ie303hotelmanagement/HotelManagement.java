package app.ie303hotelmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Login-Page.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Check-out.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Room Master");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}