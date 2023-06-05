package app.ie303hotelmanagement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Create the x and y axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        // Set the chart title
        lineChart.setTitle("Revenue");

        // Create the data series
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Revenue");

        // Add the data to the series
        series.getData().add(new XYChart.Data<>(1, 1000));
        series.getData().add(new XYChart.Data<>(2, 1500));
        series.getData().add(new XYChart.Data<>(3, 2000));
        series.getData().add(new XYChart.Data<>(4, 2500));
        series.getData().add(new XYChart.Data<>(5, 3000));

        // Add the series to the chart
        lineChart.getData().add(series);

        // Create the scene and show the stage
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}