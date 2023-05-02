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

    private String employeeID;
    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String databaseUsername = DataConnector.getUsername();
    private String databasePassword = DataConnector.getPassword();

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
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
    public void handleCheckInButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) checkInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
    public void handleNavServiceButton(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Service.fxml"));
        Parent dashboardParent = loader.load();
        ServiceController serviceController = loader.getController();
        serviceController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navServiceButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavRoomButton(ActionEvent event) throws IOException{
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
    public void handleNavReportButton(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent dashboardParent = loader.load();
        ReportController reportController = loader.getController();
        reportController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navReportButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML// đây là hàm để đăng xuất
    void handleLogoutButton(ActionEvent event) throws IOException {
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
