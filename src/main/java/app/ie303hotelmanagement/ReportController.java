package app.ie303hotelmanagement;

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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

public class ReportController {
    @FXML
    private Button LogoutButton;
    @FXML
    private Button printReport;
    @FXML
    private ComboBox reportType;
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
    void handleBtnPrintReport(ActionEvent event) throws IOException, JRException, SQLException {
        // Establish a connection to the database
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a query and get the result set
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

        // Convert the result set to a JRDataSource
        JRDataSource dataSource = new JRResultSetDataSource(resultSet);

        // Compile the report
        JasperReport jasperReport = JasperCompileManager.compileReport("F:/EmployeeReport.jrxml");

        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        // Export the report to a PDF file
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "F:/Report.pdf");

        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    }
}