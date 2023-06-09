package app.ie303hotelmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelManagement extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotelManagement.class.getResource("Login-Page.fxml"));
        Scene scene = new Scene((Parent) fxmlLoader.load());
        stage.setTitle("RoomMaster");
        stage.getIcons().add(new Image(HotelManagement.class.getResourceAsStream("/app/ie303hotelmanagement/Logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}