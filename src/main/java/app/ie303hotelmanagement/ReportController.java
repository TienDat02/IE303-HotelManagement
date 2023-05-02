package app.ie303hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.fill.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

import javafx.embed.swing.SwingNode;
import javafx.embed.swing.JFXPanel;
import win.zqxu.jrviewer.JRViewerFX;


public class ReportController {
    @FXML
    private Button LogoutButton;
    @FXML
    private Button printReport;
    @FXML
    private ComboBox<String> reportType;
    @FXML
    private Pane reportScene;

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc về việc đăng xuất hay không?");
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

    @FXML
    void initialize() throws JRException, SQLException {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Danh sách nhân viên",
                "Báo cáo checkin"
        );
        reportType.setItems(options);
    }

    @FXML
    void handleReportType() throws SQLException, JRException {
        // Get the selected report type from the ComboBox
        String selectedReport = reportType.getValue();
        // Check if a report type is selected
        if (selectedReport != null) {
            // Load the report file based on the selected report type
            String reportFile;
            if (selectedReport.equals("Danh sách nhân viên")) {
                reportFile = "F:/Dowload/EmployeeReport.jrxml";
            } else if (selectedReport.equals("Báo cáo checkin")) {
                reportFile = "F:/Dowload/CheckIn.jrxml";
            } else {
                // Handle invalid report type
                return;
            }
            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);

            // Fill the report with data
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            if (selectedReport.equals("Danh sách nhân viên")) {
                resultSet = statement.executeQuery("SELECT * FROM employee");
            } else {
                resultSet = statement.executeQuery("SELECT * FROM checkin JOIN employee ON checkin.Employee_ID = employee.Employee_ID JOIN guest ON checkin.Guest_ID = guest.Guest_ID");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");

        // Create a statement
        Statement statement = connection.createStatement();

        // Get the selected report type from the ComboBox
        String selectedReport = reportType.getValue();

        // Execute a query and get the result set based on the selected report type
        ResultSet resultSet;
        if (selectedReport.equals("Danh sách nhân viên")) {
            resultSet = statement.executeQuery("SELECT * FROM employee");

            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("F:/Dowload/EmployeeReport.jrxml");

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
            resultSet = statement.executeQuery("SELECT * FROM checkin JOIN employee ON checkin.Employee_ID = employee.Employee_ID JOIN guest ON checkin.Guest_ID = guest.Guest_ID");

            // Convert the result set to a JRDataSource
            JRDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Compile the report
            JasperReport jasperReport = JasperCompileManager.compileReport("F:/Dowload/CheckIn.jrxml");

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
}