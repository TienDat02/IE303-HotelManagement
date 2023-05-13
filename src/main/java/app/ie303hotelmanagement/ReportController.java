package app.ie303hotelmanagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import win.zqxu.jrviewer.JRViewerFX;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;


public class ReportController {
    @FXML
    String employeeID;
    @FXML
    private Button navServiceButton;
    @FXML
    private Button navRoomButton;
    @FXML
    private Button navDashboardButton;
    @FXML
    private Button navCheckinButton;
    @FXML
    private Button navEmployeeButton;
    @FXML
    private Button navCheckoutButton;
    @FXML
    private Button navCustomerButton;
    @FXML
    private Button navReportButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button printReport;
    @FXML
    private ComboBox<String> reportType = new ComboBox<>();
    @FXML
    private Pane reportScene;
    @FXML
    private DatePicker reportTime1;
    @FXML
    private DatePicker reportTime2;

    private final String connectUrl = DataConnector.getDatabaseUrl();
    private final String username = DataConnector.getUsername();
    private final String password = DataConnector.getPassword();

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @FXML
    void initialize() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Danh sách nhân viên",
                "Báo cáo checkin",
                "Báo cáo checkout",
                "Báo cáo doanh thu"
        );
        reportType.setItems(options);
    }

    @FXML
    void handleReportType() throws SQLException, JRException, IOException {
        // Get the selected report type from the ComboBox
        String selectedReport = reportType.getValue();
        // Check if a report type is selected
        if (selectedReport != null) {
            // Load the report file based on the selected report type
            String reportFile;
            if (selectedReport.equals("Danh sách nhân viên")) {
                reportFile = "src/main/resources/Report/EmployeeReport.jrxml";
            } else if (selectedReport.equals("Báo cáo checkin")) {
                reportFile = "src/main/resources/Report/Checkin.jrxml";
            } else if (selectedReport.equals("Báo cáo checkout")) {
                reportFile = "src/main/resources/Report/Check-out.jrxml";
            } else if (selectedReport.equals("Báo cáo doanh thu")) {
                reportFile = "src/main/resources/Report/Doanh-Thu.jrxml";
            } else {
                // Handle invalid report type
                return;
            }

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            // Fill the report with data
            Connection connection = DriverManager.getConnection(connectUrl, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;

            LocalDate date1 = reportTime1.getValue();
            LocalDate date2 = reportTime2.getValue();

            if (selectedReport.equals("Danh sách nhân viên")) {
                resultSet = statement.executeQuery("SELECT * FROM employee");
            } else if (selectedReport.equals("Báo cáo checkin")) {
                String query = "SELECT * FROM checkin_history JOIN employee ON checkin_history.Employee_ID = employee.Employee_ID JOIN guest ON checkin_history.Guest_ID = guest.Guest_ID";
                if (date1 != null && date2 != null) {
                    query += " WHERE checkin_history.Checkin_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
                }
                resultSet = statement.executeQuery(query);
            } else if (selectedReport.equals("Báo cáo checkout")) {
                String query = "SELECT * FROM hotelmanagement.checkout, guest, employee WHERE checkout.Guest_ID = guest.Guest_ID AND checkout.Employee_ID = employee.Employee_ID";
                if (date1 != null && date2 != null) {
                    query += " AND checkout.Checkout_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
                }
                resultSet = statement.executeQuery(query);
            } else if (selectedReport.equals("Báo cáo doanh thu")) {
                String query = "SELECT * FROM doanhthu";
                if (date1 != null && date2 != null) {
                    query += " WHERE doanhthu.Bill_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
                }
                resultSet = statement.executeQuery(query);
            }


            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRResultSetDataSource(resultSet));

            // Create a SwingNode to hold the JRViewerFX
            SwingNode swingNode = new SwingNode();

            // Create a JFXPanel to hold the JRViewerFX
            JFXPanel jfxPanel = new JFXPanel();

            // Create a JRViewerFX to display the report
            JRViewerFX jrViewerFX = new JRViewerFX(jasperPrint);
            jrViewerFX.setPrefHeight(reportScene.getHeight());
            jrViewerFX.setPrefWidth(reportScene.getWidth());

            // Add the JRViewerFX to the JFXPanel
            jfxPanel.setScene(new Scene(jrViewerFX));

            // Add the JFXPanel to the SwingNode
            swingNode.setContent(jfxPanel);

            // Clear the reportScene Pane before adding the new report
            reportScene.getChildren().clear();

            // Add the SwingNode to the reportScene Pane
            reportScene.getChildren().add(swingNode);

            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    @FXML
    void handleBtnPrintReport(ActionEvent event) throws IOException, JRException, SQLException {
        // Establish a connection to the database
        Connection connection = DriverManager.getConnection(connectUrl, username, password);

        // Create a statement
        Statement statement = connection.createStatement();

        // Get the selected report type from the ComboBox
        String selectedReport = reportType.getValue();

        // Execute a query and get the result set based on the selected report type
        ResultSet resultSet;

        LocalDate date1 = reportTime1.getValue();
        LocalDate date2 = reportTime2.getValue();


        if (selectedReport.equals("Danh sách nhân viên")) {
            resultSet = statement.executeQuery("SELECT * FROM employee");

            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/Report/EmployeeReport.jrxml");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Create a FileChooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn nơi lưu trữ");

            // Set the initial directory for the dialog
            File initialDirectory = new File(System.getProperty("user.home"));
            fileChooser.setInitialDirectory(initialDirectory);

            // Show the dialog and get the chosen file path
            File chosenFile = fileChooser.showSaveDialog(null);
            String exportPath = chosenFile.getAbsolutePath();

            // Export the report to the chosen file path
            JasperExportManager.exportReportToHtmlFile(jasperPrint, exportPath);

        } else if (selectedReport.equals("Báo cáo checkin")) {
            String query = "SELECT * FROM checkin_history JOIN employee ON checkin_history.Employee_ID = employee.Employee_ID JOIN guest ON checkin_history.Guest_ID = guest.Guest_ID";
            if (date1 != null && date2 != null) {
                query += " WHERE checkin_history.Checkin_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
            }
            resultSet = statement.executeQuery(query);
            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/Report/Checkin.jrxml");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Create a FileChooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn nơi lưu trữ");

            // Set the initial directory for the dialog
            File initialDirectory = new File(System.getProperty("user.home"));
            fileChooser.setInitialDirectory(initialDirectory);

            // Show the dialog and get the chosen file path
            File chosenFile = fileChooser.showSaveDialog(null);
            String exportPath = chosenFile.getAbsolutePath();

            // Export the report to the chosen file path
            JasperExportManager.exportReportToHtmlFile(jasperPrint, exportPath);

        } else if (selectedReport.equals("Báo cáo checkout")) {
            String query = "SELECT * FROM hotelmanagement.checkout, guest, employee WHERE checkout.Guest_ID = guest.Guest_ID AND checkout.Employee_ID = employee.Employee_ID";
            if (date1 != null && date2 != null) {
                query += " AND checkout.Checkout_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
            }
            resultSet = statement.executeQuery(query);

            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/Report/Check-out.jrxml");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Create a FileChooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn nơi lưu trữ");

            // Set the initial directory for the dialog
            File initialDirectory = new File(System.getProperty("user.home"));
            fileChooser.setInitialDirectory(initialDirectory);

            // Show the dialog and get the chosen file path
            File chosenFile = fileChooser.showSaveDialog(null);
            String exportPath = chosenFile.getAbsolutePath();

            // Export the report to the chosen file path
            JasperExportManager.exportReportToHtmlFile(jasperPrint, exportPath);

        } else if (selectedReport.equals("Báo cáo doanh thu")) {
            String query = "SELECT * FROM doanhthu";
            if (date1 != null && date2 != null) {
                query += " WHERE doanhthu.Bill_Date BETWEEN '" + date1 + "' AND '" + date2 + "'";
            }
            resultSet = statement.executeQuery(query);
            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/Report/Checkout.jrxml");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Create a FileChooser dialog
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Chọn nơi lưu trữ");

            // Set the initial directory for the dialog
            File initialDirectory = new File(System.getProperty("user.home"));
            fileChooser.setInitialDirectory(initialDirectory);

            // Show the dialog and get the chosen file path
            File chosenFile = fileChooser.showSaveDialog(null);
            String exportPath = chosenFile.getAbsolutePath();

            // Export the report to the chosen file path
            JasperExportManager.exportReportToHtmlFile(jasperPrint, exportPath);

        } else {
            // Handle invalid report type
            return;
        }

        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    }

    @FXML
    public void handleNavDashboardButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent dashboardParent = loader.load();
        DashboardController dashboardController = loader.getController();
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navDashboardButton.getScene().getWindow();
        dashboardController.initialize(employeeID);
        window.setScene(dashboardScene);
    }

    //Navitation
    @FXML
    public void handleNavCustomerButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent dashboardParent = loader.load();
        CustomerController customerController = loader.getController();
        customerController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCustomerButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavServiceButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Service.fxml"));
        Parent dashboardParent = loader.load();
        ServiceController serviceController = loader.getController();
        serviceController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navServiceButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavRoomButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        Parent dashboardParent = loader.load();
        RoomController roomController = loader.getController();
        roomController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navRoomButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML// đây là hàm để chuyển qua trang Checkin
    public void handleNavCheckinButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent dashboardParent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID);
        System.out.println("employeeID in DashboardController: " + employeeID); // Add this line
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckinButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavCheckoutButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check-out.fxml"));
        Parent dashboardParent = loader.load();
        CheckOutController checkOut = loader.getController();
        checkOut.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckoutButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavEmployeeButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePage.fxml"));
        Parent dashboardParent = loader.load();
        QLNVController qlnvController = loader.getController();
        qlnvController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navEmployeeButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavReportButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent dashboardParent = loader.load();
        ReportController reportController = loader.getController();
        reportController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navReportButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML// đây là hàm để đăng xuất
    public void handleLogoutButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có muốn đăng xuất?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("Login-Page.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}

