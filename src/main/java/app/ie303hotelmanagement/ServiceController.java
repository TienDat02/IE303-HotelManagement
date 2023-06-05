package app.ie303hotelmanagement;

import effects.ButtonAnimation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class ServiceController {
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
    private TableColumn<Service, String> serviceStatusColumn;

    @FXML
    private TableView<RoomService> roomServiceTable = new TableView<RoomService>();
    @FXML
    private TableColumn<RoomService, Integer> idColumn;
    @FXML
    private TableColumn<RoomService, Integer> roomServiceIDColumn;
    @FXML
    private TableColumn<RoomService, Integer> roomServiceRoomIDColumn;
    @FXML
    private TableColumn<RoomService, Integer> roomServiceNameColumn;
    @FXML
    private TableColumn<RoomService, Integer> roomServiceQuantityColumn;
    @FXML
    private TableColumn<RoomService, Date> roomServiceDateColumn;
    @FXML
    private TableColumn<RoomService, Time> roomServiceTimeColumn;
    @FXML private TableColumn<RoomService, String> roomServiceNoteColumn;

    @FXML
    private TextField serviceIDField;
    @FXML
    private Text serviceNameField;
    @FXML
    private ComboBox<String> serviceRoomIDField;
    @FXML
    private TextField serviceTimeField;
    @FXML
    private DatePicker serviceDateField;
    @FXML
    private TextField serviceQuantityField;
    @FXML
    private TextField serviceNoteField;
    @FXML
    private Button serviceAllocateButton;
    @FXML
    private Button serviceCancelButton;

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

    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();
    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String databaseUsername = DataConnector.getUsername();
    private String databasePassword = DataConnector.getPassword();

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public void handleFindingService(ActionEvent event) throws SQLException {
        String serviceID = findingService.getText();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        String sql0 = "SELECT * FROM service WHERE Service_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql0);
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
    @FXML
    public void initializeRoomService() {
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select Room_Service_ID, room_service.Service_ID, Service_Name, Room_ID, Service_Date, Service_Time, room_service.Service_Quantity, room_service.Note FROM room_service, service WHERE service.Service_ID = room_service.Service_ID");
            List<RoomService> roomServices = new ArrayList<>();
            while (rs.next()) {
                int roomServiceID = rs.getInt("Room_Service_ID");
                int serviceID = rs.getInt("Service_ID");
                String serviceName = rs.getString("Service_Name");
                int roomID = rs.getInt("Room_ID");
                Date roomServiceDate = rs.getDate("Service_Date");
                Time roomServiceTime = rs.getTime("Service_Time");
                int quantity = rs.getInt("Service_Quantity");
                String note = rs.getString("Note");
                RoomService roomService = new RoomService(roomServiceID, serviceID, serviceName, roomID, roomServiceDate, roomServiceTime, quantity ,note);
                roomServices.add(roomService);
            }
            roomServiceTable.getItems().addAll(roomServices);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("roomServiceID"));
            roomServiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
            roomServiceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
            roomServiceRoomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
            roomServiceDateColumn.setCellValueFactory(new PropertyValueFactory<>("roomServiceDate"));
            roomServiceTimeColumn.setCellValueFactory(new PropertyValueFactory<>("roomServiceTime"));
            roomServiceQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            roomServiceNoteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        } catch (SQLException e) {
            e.printStackTrace();
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
        ObservableList<String> roomList = FXCollections.observableArrayList();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        String sql = "SELECT Room_ID FROM room WHERE Room_Status = 'Đang thuê'";
        PreparedStatement statement0 = connection.prepareStatement(sql);
        ResultSet resultSet0 = statement0.executeQuery();
        while (resultSet0.next()) {
        String roomID = resultSet0.getString("Room_ID");
        roomList.add(roomID);
        }
        serviceRoomIDField.setItems(roomList);
        roomServiceTable.getItems().clear();
        initializeRoomService();
        serviceTable.getItems().clear();
        Statement statement = connection.createStatement();
        String sql1 = "SELECT * FROM service";
        ResultSet resultSet = statement.executeQuery(sql1);
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
        serviceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("serviceStatus"));
        serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        serviceTable.setRowFactory(tv -> new TableRow<Service>() {
            @Override
            protected void updateItem(Service service, boolean empty) {
                super.updateItem(service, empty);
                if (service == null || empty) {
                    setStyle("");
                } else if (service.getServiceStatus().equals("Bận")) {
                    setStyle("-fx-background-color: red;");
                } else if (service.getServiceStatus().equals("Khả dụng")) {
                    setStyle("-fx-background-color: green;");
                } else {
                    setStyle("");
                }
            }
        });

        resultSet.close();
        statement.close();
        connection.close();
        ButtonAnimation.addHoverEffect(addServiceButton);
        ButtonAnimation.addHoverEffect(alterServiceButton);
        ButtonAnimation.addHoverEffect(deleteServiceButton);
        ButtonAnimation.addHoverEffect(serviceCancelButton);
        ButtonAnimation.addHoverEffect(serviceAllocateButton);
    }

    public void handleServiceIDField(ActionEvent event) throws SQLException {
        String serviceID = serviceIDField.getText();
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        String sql = "SELECT * FROM service WHERE Service_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        String[] serviceIDs = serviceID.split(",");
        StringBuilder serviceNames = new StringBuilder();
        for (String id : serviceIDs) {
            statement.setString(1, id.trim());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String serviceName = resultSet.getString("Service_Name");
                serviceNames.append(serviceName).append(", ");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Dịch vụ không tồn tại!");
                alert.setHeaderText("Lỗi");
                alert.showAndWait();
                return;
            }
        }
        serviceNameField.setText(serviceNames.toString().substring(0, serviceNames.length() - 2));
    }

    public void handleServiceAllocateButton(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM room WHERE Room_ID = ? AND Room_Status = 'Đang thuê'");
        statement1.setString(1, serviceRoomIDField.getValue());
        ResultSet resultSet1 = statement1.executeQuery();
        if (!resultSet1.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Phòng " + serviceRoomIDField.getValue() + " chưa có khách!");
            alert.setHeaderText("Phòng trống");
            alert.showAndWait();
            return;
        }
        PreparedStatement checkServiceAvailable = connection.prepareStatement("SELECT * FROM service WHERE Service_ID = ? AND Service_Status = 'Bận'");
        checkServiceAvailable.setString(1, serviceIDField.getText());
        ResultSet checkServiceAvailableResult = checkServiceAvailable.executeQuery();
        if (checkServiceAvailableResult.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Dịch vụ đang bận!");
            alert.setHeaderText("Lỗi");
            alert.showAndWait();
            return;
        }
        String[] serviceIDs = serviceIDField.getText().split(",");
        String roomID = serviceRoomIDField.getValue();
        LocalDate date = serviceDateField.getValue();
        String time = serviceTimeField.getText();
        int quantity = Integer.parseInt(serviceQuantityField.getText());
        String note = serviceNoteField.getText();

        // Check if time is appropriate
        LocalTime serviceTime = LocalTime.parse(time);
        LocalTime startTime = LocalTime.of(5, 0); // Example start time
        LocalTime endTime = LocalTime.of(23, 0); // Example end time
        if (serviceTime.isBefore(startTime) || serviceTime.isAfter(endTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Thời gian không hợp lệ!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        // Check if date is appropriate
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ngày không hợp lệ!");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }


        String sql = "INSERT INTO room_service (Service_ID, Room_ID, Service_Date, Service_Time, Service_Quantity, Employee_ID, Note) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (String serviceID : serviceIDs) {
            statement.setString(1, serviceID.trim());
            statement.setString(2, roomID);
            statement.setDate(3, Date.valueOf(date));
            statement.setString(4, time);
            statement.setInt(5, quantity);
            statement.setString(6, employeeID);
            statement.setString(7, note);

            statement.executeUpdate();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dịch vụ đã được cấp phát cho phòng " + roomID);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public void handleServiceCancelButton(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        String sql = "DELETE FROM room_service WHERE Service_ID = ? AND Room_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        String[] serviceIDs = serviceIDField.getText().split(",");
        String roomID = serviceRoomIDField.getValue();
        for (String serviceID : serviceIDs) {
            statement.setString(1, serviceID.trim());
            statement.setString(2, roomID);
            statement.executeUpdate();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Dịch vụ đã được hủy cho phòng " + roomID);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
