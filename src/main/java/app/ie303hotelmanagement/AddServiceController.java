package app.ie303hotelmanagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
public class AddServiceController {
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonAddService;
    @FXML
    private TextField inputServiceStatus;
    @FXML
    private TextField inputServiceName;
    @FXML
    private TextField inputServicePrice;
    @FXML
    private TextField inputServiceDescription;
    private String databaseUrl = "jdbc:mysql://localhost:3306/HotelManagement";
    private String databaseUsername = "root";
    private String databasePassword = "tiendat1102";
    public void handleButtonAddService(ActionEvent event) throws SQLException {
        String serviceName = inputServiceName.getText();
        float servicePrice = Float.parseFloat(inputServicePrice.getText());
        String serviceDescription = inputServiceDescription.getText();
        String serviceStatus = inputServiceStatus.getText();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO service VALUES (NULL, '" + serviceName + "', '" + servicePrice + "', '" + serviceDescription + "', '" + serviceStatus + "')";
        int resultSet = statement.executeUpdate(sql);
        if (resultSet == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thêm dịch vụ thành công!");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Thêm dịch vụ thất bại!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}
