package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
public class ServiceController {
    @FXML
    private Button LogoutButton;
    @FXML
    private Button checkInButton;
    @FXML
    private Button addServiceButton;
    @FXML
    private Button deleteServiceButton;
    @FXML
    private Button alterServiceButton;
    @FXML
    private Button CheckInButton;
    @FXML
    private Button CheckOutButton;
    @FXML
    private Button ServiceButton;
    @FXML
    private Button CustomerButton;
    @FXML
    private Button RoomButton;
    @FXML
    private Button EmployeeButton;
    @FXML
    private Button ReportButton;
    @FXML
    private TextField findingService;
    @FXML
    private TableView<Service> serviceTable;
    @FXML
    private TableColumn<Service, String> serviceNameColumn;
    @FXML
    private TableColumn<Service, Integer> servicePriceColumn;
    @FXML
    private TableColumn<Service, String> serviceIDColumn;
    @FXML
    private TableColumn<Service, String> serviceDescriptionColumn;
    private String databaseUrl = "jdbc:mysql://localhost:3306/HotelManagement";
    private String databaseUsername = "ThanhTra0802";
    private String databasePassword = "thanhtra0802";
    public void handleFindingService(ActionEvent event) throws SQLException {
        String serviceID = findingService.getText();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        String sql = "SELECT * FROM service WHERE Service_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, serviceID);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String serviceName = resultSet.getString("Service_Name");
            int servicePrice = resultSet.getInt("Service_Price");
            String serviceDescription = resultSet.getString("Service_Description");
            String serviceStatus = resultSet.getString("Service_Status");
            Service service = new Service(serviceID, serviceName, servicePrice, serviceDescription,serviceStatus);
            serviceTable.getItems().add(service);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Không tìm thấy dịch vụ này!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    public void handlePopUpAddServiceButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddService.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Thêm dịch vụ");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        //update the table view after closing the pop up window
        stage.setOnCloseRequest((e) -> {
            try {
                initialize();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
    public void handleDeleteServiceButton(ActionEvent event) throws SQLException {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        if (service == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ cần xóa!");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc về việc xóa dịch vụ này hay không?");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String serviceID = service.getServiceID();
                Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
                Statement statement = connection.createStatement();
                String sql = "DELETE FROM service WHERE Service_ID = '" + serviceID + "'";
                statement.executeUpdate(sql);
                serviceTable.getItems().remove(service);
            }
        }
    }

    public void handlePopUpAlterServiceButton(ActionEvent event) throws IOException {
        Service service = serviceTable.getSelectionModel().getSelectedItem();
        if (service == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn dịch vụ cần sửa!");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            String serviceID = service.getServiceID();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AlterService.fxml"));
            Parent root = loader.load();
            AlterServiceController alterServiceController = loader.getController();
            alterServiceController.setOperateServiceID(serviceID);
            Stage stage = new Stage();
            stage.setTitle("Sửa dịch vụ");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
            //update the table view after closing the pop up window
            stage.setOnCloseRequest(event1 -> {
                serviceTable.getItems().clear();
                try {
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    public void initialize() throws SQLException {
        serviceTable.getItems().clear();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM service";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String serviceID = resultSet.getString("Service_ID");
            String serviceName = resultSet.getString("Service_Name");
            int servicePrice = resultSet.getInt("Service_Price");
            String serviceDescription = resultSet.getString("Service_Description");
            String serviceStatus = resultSet.getString("Service_Status");
            Service service = new Service(serviceID, serviceName, servicePrice, serviceDescription, serviceStatus);
            serviceTable.getItems().add(service);
        }
        serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
    }
    @FXML
    public void handleCheckInButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) checkInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCheckOutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckOut.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) CheckOutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleRoomButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) RoomButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleServiceButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Service.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ServiceButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCustomerButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) CustomerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleEmployeeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) EmployeeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleReportButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Report.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ReportButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleLogoutButton(ActionEvent event) throws IOException {
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage) LogoutButton.getScene().getWindow();
        window.setScene(loginScene);
    }
}
