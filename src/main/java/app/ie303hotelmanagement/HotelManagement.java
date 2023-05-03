package app.ie303hotelmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
<<<<<<< HEAD
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Service.fxml"));
=======
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Login-Page.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Check-out.fxml"));
>>>>>>> eada7baa28d1f4df4f2d6a848a6c67ce4ef83927
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Service");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}