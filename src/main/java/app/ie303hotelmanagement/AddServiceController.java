package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class AddServiceController {
    // Khai báo các thành phần giao diện
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
    // kết nối database
    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String databaseUsername = DataConnector.getUsername();
    private String databasePassword = DataConnector.getPassword();
    // Hàm xử lý sự kiện khi nhấn nút Add Service
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
