package app.ie303hotelmanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyApp extends Application {

@Override
public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        CalendarControl calendar = new CalendarControl();
        root.getChildren().add(calendar);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        }

public static void main(String[] args) {
        launch(args);
        }
        }