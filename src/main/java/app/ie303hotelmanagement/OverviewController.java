package app.ie303hotelmanagement;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OverviewController {
    @FXML private RadioButton radioButton1;
    @FXML private RadioButton radioButton2;
    @FXML private RadioButton radioButton3;
    @FXML private Spinner hour1;
    @FXML private Spinner hour2;
    @FXML private Spinner minute1;
    @FXML private Spinner minute2;
    @FXML private DatePicker date1;
    @FXML private DatePicker date2;
    @FXML private Text numberOfReservations;
    @FXML private Text numberOfRooms;
    @FXML private Text numberOfCheckin;
    @FXML private Text numberOfCheckout;
    @FXML private Pane graph;
    @FXML private Pane availability;
    @FXML private Pane employeeGraph;
    @FXML private Button employeeButton;
    @FXML private ImageView avatar;
    @FXML private TableView<Log> logTable;
    @FXML private TableColumn<Log, LocalDateTime> logTimeColumn;
    @FXML private TableColumn<Log, String> logIndexColumn;
    @FXML private TableColumn<Log, String> logDetailColumn;
    @FXML private TableColumn<Log, String> logStatusColumn;
    private String connectionURL = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    @FXML
    public void initialize() throws SQLException {
        handleRadioButton2();
        SpinnerValueFactory<Integer> hourFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        hour1.setValueFactory(hourFactory1);

        SpinnerValueFactory<Integer> minuteFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        minute1.setValueFactory(minuteFactory1);

        SpinnerValueFactory<Integer> hourFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        hour2.setValueFactory(hourFactory2);

        SpinnerValueFactory<Integer> minuteFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        minute2.setValueFactory(minuteFactory2);


        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();
        drawLineGraph(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));



        Connection conn = DriverManager.getConnection(connectionURL, username, password);
        // Create a new PieChart object
        PieChart chart = new PieChart();
        ResultSet rs3 = conn.createStatement().executeQuery("SELECT Room_Status, COUNT(*) FROM room GROUP BY Room_Status ");

        // Populate the PieChart with data from the ResultSet
        while (rs3.next()) {
            String status = rs3.getString("Room_Status");
            int count = rs3.getInt(2);
            chart.getData().add(new PieChart.Data(status, count));
        }

        // Add the PieChart to the graph pane
        chart.setPrefSize(355, 300);
        StackPane pane = new StackPane(chart);
        pane.setAlignment(Pos.CENTER);
        availability.getChildren().add(pane);

        PieChart employeeChart = new PieChart();
        ResultSet rs4 = conn.createStatement().executeQuery("SELECT Employee_Status, COUNT(*) FROM employee GROUP BY Employee_Status ");
        while (rs4.next()) {
            String status = rs4.getString("Employee_Status");
            int count = rs4.getInt(2);
            employeeChart.getData().add(new PieChart.Data(status, count));
        }
        employeeChart.setPrefSize(355, 300);
        StackPane pane2 = new StackPane(employeeChart);
        pane2.setAlignment(Pos.CENTER);
        employeeGraph.getChildren().add(pane2);
        rs3.close();
        rs4.close();

        PreparedStatement stmt3 = conn.prepareStatement("SELECT * FROM employee WHERE Employee_ID = ?");
        stmt3.setString(1, employeeID);
        ResultSet rs5 = stmt3.executeQuery();
        if (rs5.next()){
            employeeButton.setText(rs5.getString("Employee_Name"));
        }
        //set avatar
        String imageName = employeeID + ".png";
        File imageFile = new File("src/main/resources/images/" + imageName);
        if (!imageFile.exists()) {
            imageName = "default.png"; // assuming the default image's name is default.png
            imageFile = new File("src/main/resources/images/" + imageName);
        }
        Image image = new Image(imageFile.toURI().toString());
        avatar.setImage(image);
        Rectangle clip = new Rectangle(avatar.getFitWidth(), avatar.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        avatar.setClip(clip);
        rs5.close();

        PreparedStatement stmt6 = conn.prepareStatement("SELECT * FROM log ORDER BY Log_Time DESC");
        ResultSet rs6 = stmt6.executeQuery();
        while (rs6.next()) {
            logTable.getItems().add(new Log(rs6.getTimestamp("Log_Time").toLocalDateTime(), rs6.getString("Log_Index"), rs6.getString("Log_Status"), rs6.getString("Log_Detail")));
        }
        logTimeColumn.setCellValueFactory(new PropertyValueFactory<>("logTime"));
        logTimeColumn.setCellFactory(column -> new TableCell<Log, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String formattedTime = item.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
                    setText(formattedTime);
                }
            }
        });
        logIndexColumn.setCellValueFactory(new PropertyValueFactory<>("logIndex"));
        logDetailColumn.setCellValueFactory(new PropertyValueFactory<>("logDetail"));
        logStatusColumn.setCellValueFactory(new PropertyValueFactory<>("logStatus"));
        rs6.close();

        conn.close();
    }

    //Vẽ biểu đồ
    public void drawLineGraph(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        graph.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setPrefSize(1150, 650);
        lineChart.setTitle("Check-in/Check-out");
        xAxis.setLabel("Ngày");
        yAxis.setLabel("Số lượng check-in");
        lineChart.setAnimated(true);
        XYChart.Series<String, Number> checkinSeries = new XYChart.Series<>();
        checkinSeries.setName("Check-in");

        XYChart.Series<String, Number> checkoutSeries = new XYChart.Series<>();
        checkoutSeries.setName("Check-out");
        Connection conn = DriverManager.getConnection(connectionURL, username, password);

        PreparedStatement stmt1 = conn.prepareStatement("SELECT Checkin_Date, COUNT(*) FROM checkin_history WHERE Checkin_Date BETWEEN ? AND ? GROUP BY Checkin_Date ORDER BY Checkin_Date ASC");
        stmt1.setObject(1, startDate);
        stmt1.setObject(2, endDate);
        ResultSet rs1 = stmt1.executeQuery();

        while (rs1.next()) {
            String date = rs1.getString("Checkin_Date");
            int count = rs1.getInt("COUNT(*)");
            checkinSeries.getData().add(new XYChart.Data<>(date, count));
        }
        stmt1.close();
        PreparedStatement stmt2 = conn.prepareStatement("SELECT Checkout_Date, COUNT(*) FROM checkout WHERE Checkout_Date BETWEEN ? AND ? GROUP BY Checkout_Date ORDER BY Checkout_Date ASC");
        stmt2.setObject(1, startDate);
        stmt2.setObject(2, endDate);
        ResultSet rs2 = stmt2.executeQuery();

        while (rs2.next()) {
            String date = rs2.getString("Checkout_Date");
            int count = rs2.getInt("COUNT(*)");
            checkoutSeries.getData().add(new XYChart.Data<>(date, count));
        }
        stmt2.close();
        lineChart.getData().addAll(checkinSeries, checkoutSeries);
        graph.getChildren().add(lineChart);
        rs1.close();
        rs2.close();
        conn.close();
    }

    //Sửa các thẻ
    public void cardEditor (LocalDateTime startDate, LocalDateTime endDate) {
        int checkinCount = 0;
        try {
            Connection conn = DriverManager.getConnection(connectionURL, username, password);
            PreparedStatement stm1 = conn.prepareStatement("SELECT COUNT(Guest_ID) FROM reservation WHERE Reserve_Date BETWEEN ? AND ? GROUP BY Guest_ID");
            stm1.setObject(1, startDate);
            stm1.setObject(2, endDate);
            ResultSet rs1 = stm1.executeQuery();
            if (rs1.next()) {
                int count = rs1.getInt(1);
                numberOfReservations.setText(Integer.toString(count));
            }
            rs1.close();
            PreparedStatement stmt2 = conn.prepareStatement("SELECT COUNT(*) FROM reservation WHERE Reserve_Date BETWEEN ? AND ?");
            stmt2.setObject(1, startDate);
            stmt2.setObject(2, endDate);
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2.next()) {
                int count = rs2.getInt(1);
                numberOfRooms.setText(Integer.toString(count));
            }
            rs2.close();
            stmt2.close();
            PreparedStatement stmt3 = conn.prepareStatement("SELECT COUNT(*) FROM checkin WHERE Checkin_Date BETWEEN ? AND ?");
            stmt3.setObject(1, startDate);
            stmt3.setObject(2, endDate);
            ResultSet rs3 = stmt3.executeQuery();
            if (rs3.next()) {
                checkinCount = rs3.getInt(1);
            }
            rs3.close();
            stmt3.close();
            PreparedStatement stmt4 = conn.prepareStatement("SELECT COUNT(*) FROM checkout WHERE Checkout_Date BETWEEN ? AND ?");
            stmt4.setObject(1, startDate);
            stmt4.setObject(2, endDate);
            ResultSet rs4 = stmt4.executeQuery();
            if (rs4.next()) {
                checkinCount = checkinCount + rs4.getInt(1);
            }
            numberOfCheckin.setText(Integer.toString(checkinCount));
            rs4.close();
            stmt4.close();
            PreparedStatement stmt5 = conn.prepareStatement("SELECT COUNT(*) FROM checkout WHERE Checkout_Date BETWEEN ? AND ?");
            stmt5.setObject(1, startDate);
            stmt5.setObject(2, endDate);
            ResultSet rs5 = stmt5.executeQuery();
            if (rs5.next()) {
                int count = rs5.getInt(1);
                numberOfCheckout.setText(Integer.toString(count));
            }
            rs5.close();
            stmt5.close();
            conn.close();
        } catch (SQLException e) {
            // handle exception
        }
    }
    public void handleRadioButton1() {
        radioButton1.setSelected(true);
        graph.getChildren().clear();
        LocalDateTime dateTime1 = LocalDateTime.of(date1.getValue(), LocalTime.of((int) hour1.getValue(), (int) minute1.getValue()));
        LocalDateTime dateTime2 = LocalDateTime.of(date2.getValue(), LocalTime.of((int) hour2.getValue(), (int) minute2.getValue()));
        try {
            drawLineGraph(dateTime1, dateTime2);
            cardEditor(dateTime1, dateTime2);
        } catch (SQLException e) {
            // handle exception
        }
    }
    @FXML
    public void handleRadioButton2() {
        radioButton2.setSelected(true);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        try {
            drawLineGraph(startDate.atStartOfDay(), endDate.atStartOfDay());
            cardEditor(startDate.atStartOfDay(), endDate.atStartOfDay());
        } catch (SQLException e) {
            // handle exception
        }
    }
    @FXML
    public void handleRadioButton3() {
        radioButton3.setSelected(true);
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        try {
            drawLineGraph(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
            cardEditor(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        } catch (SQLException e) {
            // handle exception
        }
    }
    @FXML
    public void handleEmployeeButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard2.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) employeeButton.getScene().getWindow();
        //dashboardController.initialize(EmployeeSingleton.getInstance().getEmployeeID());
        window.setScene(scene);
    }
}
