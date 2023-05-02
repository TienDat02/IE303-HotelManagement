package app.ie303hotelmanagement;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class CheckinController {
    @FXML
    private Button navDashboardButton;
    @FXML
    private Button navServiceButton;
    @FXML
    private Button navRoomButton;
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
    private TextField inputCCCD;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputYearOfBirth;
    @FXML
    private TextField inputPhone;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> sTT;
    @FXML
    private TableColumn<Room, String> roomID;
    @FXML
    private TableColumn<Room, String> roomType;
    @FXML
    private TableColumn<Room, Integer> floor;
    @FXML
    private TableColumn<Room, String> status;
    @FXML
    private TextField inputRoom;
    @FXML
    private DatePicker inputCheckinDate;
    @FXML
    private TextField inputNumberOfGuest;
    @FXML
    private TextField inputNote;
    @FXML
    private DatePicker inputCheckoutDate;

    private String employeeID;//đây là biến để lưu lại employeeID khi chuyển qua lại giữa các trang
    private int count = 1;
    private String connectionString = "jdbc:mysql://localhost:3306/hotelmanagement";
    private String username = "root";
    private String password = "tiendat1102";
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    @FXML
    public void handleCCCDInput() {
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, inputCCCD.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                inputName.setText(rs.getString("Guest_Name"));
                inputYearOfBirth.setText(rs.getString("Guest_BirthYear"));
                inputPhone.setText(rs.getString("Guest_Phone"));
            }
            rs.close(); // đóng kết nối cơ sở dữ liệu
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Room_ID FROM reservation WHERE Guest_ID = ?");
            stmt2.setString(1, inputCCCD.getText());
            ResultSet rs2 = stmt2.executeQuery();
            StringBuilder reservedRooms = new StringBuilder();
            while (rs2.next()) {
                reservedRooms.append(rs2.getString("Room_ID")).append(",");
            }
            if (reservedRooms.length() > 0) {
                reservedRooms.deleteCharAt(reservedRooms.length() - 1); // remove last comma
            }
            rs2.close();
            PreparedStatement stmt3 = conn.prepareStatement("SELECT Expected_Checkin_Date, Expected_Checkout_Date, Number_ofGuest FROM reservation WHERE Guest_ID = ?");
            stmt3.setString(1, inputCCCD.getText());
            ResultSet rs3 = stmt3.executeQuery();
            if (rs3.next()) {
                inputCheckinDate.setValue(rs3.getDate("Expected_Checkin_Date").toLocalDate());
                inputCheckoutDate.setValue(rs3.getDate("Expected_Checkout_Date").toLocalDate());
                inputRoom.setText(reservedRooms.toString());
                inputNumberOfGuest.setText(rs3.getString("Number_ofGuest"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room where Room_Status = 'Trống' or Room_Status = 'Đặt trước'");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(count, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status"));
                roomTable.getItems().add(room);
                count++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sTT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Room, Integer> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getsTT());
            }
        });
        roomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        roomTable.getItems().addListener((javafx.collections.ListChangeListener.Change<? extends Room> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (int i = 0; i < c.getAddedSize(); i++) {
                        c.getList().get(i).setsTT(count);
                        count++;
                    }
                }
            }
        });
    }

    @FXML
    public void reloadCheckinPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent parent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleReservateButton(ActionEvent event) throws IOException {
        try {
        Connection conn = DriverManager.getConnection(connectionString, username, password);
        String[] roomIDs = inputRoom.getText().split(","); // split input by comma
        boolean allRoomsAvailable = true;
        for (String roomID : roomIDs) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room WHERE Room_ID = ?");
            stmt.setString(1, roomID);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next() || !rs.getString("Room_Status").equals("Trống")) {
                allRoomsAvailable = false;
                break;
            }
        }
        if (!allRoomsAvailable) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Một hoặc nhiều phòng không khả dụng, vui lòng thử lại.");
            alert.showAndWait();
        } else if (inputCCCD.getText().isEmpty() || inputName.getText().isEmpty() || inputYearOfBirth.getText().isEmpty() || inputPhone.getText().isEmpty() || inputRoom.getText().isEmpty() || inputCheckinDate.getValue() == null || inputNumberOfGuest.getText().isEmpty() || inputCheckoutDate.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng điền đầy đủ thông tin.");
            alert.showAndWait();
        } else if (inputCheckinDate.getValue().isAfter(inputCheckoutDate.getValue()) || inputCheckinDate.getValue().isBefore(LocalDate.now()) || inputCheckoutDate.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Ngày đến và ngày đi không hợp lệ.");
            alert.showAndWait();
        } else {
            LocalDate checkinDate = inputCheckinDate.getValue();
            LocalDate checkoutDate = inputCheckoutDate.getValue();
            for (String roomID : roomIDs) {
                PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO reservation (Guest_ID, Employee_ID, Room_ID, Reserve_Date, Expected_Checkin_Date, Expected_Checkout_Date, Number_OfGuest) VALUES (?, ?, ?, ?, ?, ?, ?)");
                stmt2.setString(1, inputCCCD.getText());
                stmt2.setString(2, employeeID);
                stmt2.setString(3, roomID);
                stmt2.setString(4, LocalDate.now().toString());
                stmt2.setString(5, checkinDate.toString());
                stmt2.setString(6, checkoutDate.toString());
                stmt2.setString(7, inputNumberOfGuest.getText());
                stmt2.executeUpdate();

                PreparedStatement stmt3 = conn.prepareStatement("UPDATE room SET Room_Status = 'Đặt trước' WHERE Room_ID = ?");
                stmt3.setString(1, roomID);
                stmt3.executeUpdate();
            }

            PreparedStatement stmt4 = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
            stmt4.setString(1, inputCCCD.getText());
            ResultSet rs2 = stmt4.executeQuery();
            if (!rs2.next()) {
                PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO guest (Guest_ID, Guest_Name, Guest_Phone, Guest_BirthYear, Guest_Notes) VALUES (?, ?, ?, ?, ?)");
                stmt5.setString(1, inputCCCD.getText());
                stmt5.setString(2, inputName.getText());
                stmt5.setString(3, inputPhone.getText());
                stmt5.setString(4, inputYearOfBirth.getText());
                stmt5.setString(5, inputNote.getText());
                stmt5.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Đặt phòng thành công.");
            alert.showAndWait();
            //reload checkin page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
            Parent parent = loader.load();
            CheckinController checkinController = loader.getController();
            checkinController.setEmployeeID(employeeID);
            Scene dashboardScene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dashboardScene);
        }
        conn.close();
    } catch (SQLException | IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Không thể kết nối đến cơ sở dữ liệu.");
        alert.showAndWait();
        e.printStackTrace();
        }
    }
    @FXML
    public void handleCheckinButton(ActionEvent event) throws IOException {
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            String[] roomIDs = inputRoom.getText().split(","); // split input by comma
            boolean allRoomsAvailable = true;
            for (String roomID : roomIDs) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room WHERE Room_ID = ?");
                stmt.setString(1, roomID);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next() || !rs.getString("Room_Status").equals("Trống")) {
                    if (!rs.getString("Room_Status").equals("Đặt trước")) {
                        allRoomsAvailable = false;
                        break;
                    }
                    break;
                }
            }
            if (!allRoomsAvailable) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Một hoặc nhiều phòng không khả dụng, vui lòng thử lại.");
                alert.showAndWait();
            } else if (inputCCCD.getText().isEmpty() || inputName.getText().isEmpty() || inputYearOfBirth.getText().isEmpty() || inputPhone.getText().isEmpty() || inputRoom.getText().isEmpty() || inputNumberOfGuest.getText().isEmpty() || inputCheckoutDate.getValue() == null ) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Vui lòng điền đầy đủ thông tin.");
                alert.showAndWait();
            } else if (inputCheckinDate.getValue().isAfter(inputCheckoutDate.getValue())|| inputCheckinDate.getValue().isBefore(LocalDate.now()) || inputCheckoutDate.getValue().isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Ngày đến và ngày đi không hợp lệ.");
                alert.showAndWait();
            } else {
                LocalDate checkinDate = LocalDate.now();
                LocalDate checkoutDate = inputCheckoutDate.getValue();
                for (String roomID : roomIDs) {
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Checkin (Guest_ID, Room_ID, Checkin_Date, Expected_Checkout_Date, Number_OfGuest, Employee_ID) VALUES (?, ?, ?, ?, ?, ?)");
                    stmt2.setString(1, inputCCCD.getText());
                    stmt2.setString(2, roomID);
                    stmt2.setString(3, checkinDate.toString());
                    stmt2.setString(4, checkoutDate.toString());
                    stmt2.setInt(5, Integer.parseInt(inputNumberOfGuest.getText()));
                    stmt2.setString(6, employeeID);
                    stmt2.executeUpdate();

                    PreparedStatement stmt3 = conn.prepareStatement("UPDATE room SET Room_Status = 'Đang thuê' WHERE Room_ID = ?");
                    stmt3.setString(1, roomID);
                    stmt3.executeUpdate();
                }

                PreparedStatement stmt4 = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
                stmt4.setString(1, inputCCCD.getText());
                ResultSet rs2 = stmt4.executeQuery();
                if (!rs2.next()) {
                    PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO guest (Guest_ID, Guest_Name, Guest_Phone, Guest_BirthYear, Guest_Notes) VALUES (?, ?, ?, ?, ?)");
                    stmt5.setString(1, inputCCCD.getText());
                    stmt5.setString(2, inputName.getText());
                    stmt5.setString(3, inputPhone.getText());
                    stmt5.setString(4, inputYearOfBirth.getText());
                    stmt5.setString(5, inputNote.getText());
                    stmt5.executeUpdate();
                }
                // delete reservation
                PreparedStatement stmt6 = conn.prepareStatement("DELETE FROM reservation WHERE Guest_ID = ?");
                stmt6.setString(1, inputCCCD.getText());
                stmt6.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setHeaderText("Nhận phòng thành công.");
                alert.showAndWait();
                //reload checkin page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
                Parent parent = loader.load();
                CheckinController checkinController = loader.getController();
                checkinController.setEmployeeID(employeeID);
                Scene dashboardScene = new Scene(parent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dashboardScene);
            }
            conn.close();
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể kết nối đến cơ sở dữ liệu.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    @FXML
    public void deleteReservation(ActionEvent event) throws IOException, SQLException {
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);

            // create confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Bạn có chắc chắn muốn hủy đặt phòng?");
            Optional<ButtonType> result = alert.showAndWait();

            // if user clicks "OK", execute action
            if (result.get() == ButtonType.OK) {
                PreparedStatement stmt2 = conn.prepareStatement("UPDATE room SET Room_Status = 'Trống' WHERE Room_ID IN (SELECT Room_ID FROM reservation WHERE Guest_ID = ?)");
                stmt2.setString(1, inputCCCD.getText());
                stmt2.executeUpdate();

                PreparedStatement stmt = conn.prepareStatement("DELETE FROM reservation WHERE Guest_ID = ?");
                stmt.setString(1, inputCCCD.getText());
                stmt.executeUpdate();

                conn.close();

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Thành công");
                successAlert.setHeaderText("Hủy đặt phòng thành công.");
                successAlert.showAndWait();
                //reload checkin page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
                Parent parent = loader.load();
                CheckinController checkinController = loader.getController();
                checkinController.setEmployeeID(employeeID);
                Scene dashboardScene = new Scene(parent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(dashboardScene);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Không thể kết nối đến cơ sở dữ liệu.");
            alert.showAndWait();
            e.printStackTrace();
        }
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
    @FXML
    public void handleNavCheckinButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent dashboardParent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckinButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

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

