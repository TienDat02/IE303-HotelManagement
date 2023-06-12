package app.ie303hotelmanagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.*;
import win.zqxu.jrviewer.JRViewerFX;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


public class ReportController {
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();
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
    void initialize() throws SQLException {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Danh sách nhân viên",
                "Báo cáo checkin",
                "Báo cáo checkout"
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

    //Pane 2

}

