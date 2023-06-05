package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GraphsPaneController {
    @FXML private AnchorPane incomeGraphPane;
    @FXML private AnchorPane mostUsedRoomType;
    @FXML private AnchorPane mostUsedService;
    @FXML private RadioButton barChartRadioButton;
    @FXML private RadioButton stackedBarChartRadioButton;
    @FXML private ComboBox<String> filterCombobox = new ComboBox<>();
    @FXML private Spinner<Integer> hour1;
    @FXML private Spinner<Integer> minute1;
    @FXML private Spinner<Integer> hour2;
    @FXML private Spinner<Integer> minute2;
    @FXML private DatePicker date1;
    @FXML private DatePicker date2;
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
    ArrayList<String> colorPatte = new ArrayList<>();
    @FXML
    public void initialize() throws SQLException {
        colorPatte.add("#3474d4");
        colorPatte.add("#a2dcd7");
        colorPatte.add("#17396d");
        colorPatte.add("#ae544f");
        colorPatte.add("#c79c99");
        colorPatte.add("#4c4c6c");
        SpinnerValueFactory<Integer> hourFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        hour1.setValueFactory(hourFactory1);

        SpinnerValueFactory<Integer> minuteFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        minute1.setValueFactory(minuteFactory1);

        SpinnerValueFactory<Integer> hourFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        hour2.setValueFactory(hourFactory2);

        SpinnerValueFactory<Integer> minuteFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        minute2.setValueFactory(minuteFactory2);
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        drawBarChart(startDate, endDate, "MONTH");
        drawMostUsedServiceGraph(startDate, endDate);
        drawMostUsedRoom(startDate, endDate);
        filterCombobox.getItems().addAll("Theo ngày", "Theo tháng", "Theo năm");
    }
    @FXML
    public void drawBarChart(LocalDateTime startDate, LocalDateTime endDate, String gap) throws SQLException {
        Connection connection = DriverManager.getConnection(connectUrl, username, password);
        Statement statement = connection.createStatement();

        String query = "SELECT "+gap+"(Bill_Date), SUM(Total_Cost) as TOTAL FROM bill WHERE Bill_Date < '" + endDate + "' AND Bill_Date > '" + startDate + "' GROUP BY "+ gap +"(Bill_Date)";
        ResultSet resultSet = statement.executeQuery(query);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(gap);
        yAxis.setLabel("Doanh thu (VNĐ)");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Biểu đồ doanh thu theo hóa đơn");
        barChart.setPrefHeight(720);
        barChart.setPrefWidth(1550);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        while (resultSet.next()) {
            String month = resultSet.getString(1);
            double total = resultSet.getDouble(2);
            series.getData().add(new XYChart.Data<>(month, total));
        }
        barChart.getData().add(series);

        incomeGraphPane.getChildren().clear();
        incomeGraphPane.getChildren().add(barChart);

        resultSet.close();
        statement.close();
        connection.close();
    }
    @FXML
    public void drawStackedBarGraph(LocalDateTime startDate, LocalDateTime endDate, String gap) throws SQLException {
        Connection connection = DriverManager.getConnection(connectUrl, username, password);
        Statement statement = connection.createStatement();

        String query = "SELECT "+gap+"(Checkout_Date), SUM(Service_Total) as ServiceTotal, SUM(Room_Total) as RoomTotal FROM checkout WHERE Checkout_Date < '" + endDate + "' AND Checkout_Date > '" + startDate + "' GROUP BY "+gap+"(Checkout_Date)";
        ResultSet resultSet = statement.executeQuery(query);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Biểu đồ doanh thu theo dịch vụ và phòng");
        xAxis.setLabel(gap);
        yAxis.setLabel("Doanh thu (VNĐ)");
        stackedBarChart.setPrefHeight(720);
        stackedBarChart.setPrefWidth(1550);

        XYChart.Series<String, Number> serviceSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> roomSeries = new XYChart.Series<>();
        while (resultSet.next()) {
            String month = resultSet.getString(1);
            double serviceTotal = resultSet.getDouble(2);
            double roomTotal = resultSet.getDouble(3);
            serviceSeries.getData().add(new XYChart.Data<>(month, serviceTotal));
            roomSeries.getData().add(new XYChart.Data<>(month, roomTotal));
        }
        serviceSeries.setName("Service Total");
        roomSeries.setName("Room Total");
        stackedBarChart.getData().addAll(serviceSeries, roomSeries);

        incomeGraphPane.getChildren().clear();
        incomeGraphPane.getChildren().add(stackedBarChart);

        resultSet.close();
        statement.close();
        connection.close();
    }
    @FXML public void drawMostUsedServiceGraph(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        Connection connection = DriverManager.getConnection(connectUrl, username, password);
        Statement statement = connection.createStatement();

        String query = "SELECT Service_Name, SUM(Service_Quantity) AS NumberOfProducts FROM used_service, service WHERE service.Service_ID=used_service.Service_ID AND Service_Date > '" +startDate + "' AND Service_Date <'" +endDate + "'group by used_service.Service_ID;";
        ResultSet resultSet = statement.executeQuery(query);

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Dịch vụ");
        yAxis.setLabel("Số lượt dùng");
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Biểu đồ số lượt dùng dịch vụ");
        barChart.setPrefHeight(720);
        barChart.setPrefWidth(1550);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        while (resultSet.next()) {
            String month = resultSet.getString(1);
            double total = resultSet.getDouble(2);
            series.getData().add(new XYChart.Data<>(month, total));
        }
        barChart.getData().add(series);

        mostUsedService.getChildren().clear();
        mostUsedService.getChildren().add(barChart);

        resultSet.close();
        statement.close();
        connection.close();
    }
    @FXML
    public void drawMostUsedRoom(LocalDateTime startDate, LocalDateTime endDate){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectUrl, username, password);
            Statement statement = connection.createStatement();

            String query = "SELECT Room_Type, COUNT(checkin_history.Room_ID) AS NumberOfProducts FROM checkin_history, room WHERE room.Room_ID=checkin_history.Room_ID AND Checkin_Date > '" +startDate + "' AND Checkin_Date <'" +endDate + "'group by Room_Type;";
            ResultSet resultSet = statement.executeQuery(query);

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Phòng");
            yAxis.setLabel("Số lượt dùng");
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            barChart.setTitle("Biểu đồ số lượt dùng phòng");
            barChart.setPrefHeight(720);
            barChart.setPrefWidth(1550);
            int count = 0;
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            while (resultSet.next()) {
                String month = resultSet.getString(1);
                double total = resultSet.getDouble(2);
                series.getData().add(new XYChart.Data<>(month, total));

            }
            barChart.getData().add(series);

            mostUsedRoomType.getChildren().clear();
            mostUsedRoomType.getChildren().add(barChart);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML public void handleDrawGraph(ActionEvent event) throws SQLException {
        incomeGraphPane.getChildren().clear();
        mostUsedService.getChildren().clear();
        mostUsedRoomType.getChildren().clear();
        LocalDateTime dateTime1 = LocalDateTime.of(date1.getValue(), LocalTime.of((int) hour1.getValue(), (int) minute1.getValue()));
        LocalDateTime dateTime2 = LocalDateTime.of(date2.getValue(), LocalTime.of((int) hour2.getValue(), (int) minute2.getValue()));
        drawMostUsedServiceGraph(dateTime1, dateTime2);
        drawMostUsedRoom(dateTime1, dateTime2);
        String selected = filterCombobox.getSelectionModel().getSelectedItem();
        if (selected.equals("Theo ngày")&& barChartRadioButton.isSelected()) {
            try {
                drawBarChart(dateTime1, dateTime2, "DATE");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (selected.equals("Theo tháng")&& barChartRadioButton.isSelected()) {
            try {
                drawBarChart(dateTime1, dateTime2, "MONTH");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (selected.equals("Theo năm")&& barChartRadioButton.isSelected()) {
            try {
                drawBarChart(dateTime1, dateTime2, "YEAR");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (selected.equals("Theo ngày")&& stackedBarChartRadioButton.isSelected()) {
            try {
                drawStackedBarGraph(dateTime1, dateTime2, "DATE");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (selected.equals("Theo tháng")&& stackedBarChartRadioButton.isSelected()) {
            try {
                drawStackedBarGraph(dateTime1, dateTime2, "MONTH");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if (selected.equals("Theo năm")&& stackedBarChartRadioButton.isSelected()) {
            try {
                drawStackedBarGraph(dateTime1, dateTime2, "YEAR");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
