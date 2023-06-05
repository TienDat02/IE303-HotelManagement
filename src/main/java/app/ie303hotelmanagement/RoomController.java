package app.ie303hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RoomController {
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();

    @FXML
    private Button addRoomButton;
    @FXML
    private Button updateRoomButton;
    @FXML
    private Button deleteRoomButton;
    @FXML
    private TableView<RoomDetail> roomTable;

    @FXML
    private TableColumn<RoomDetail, String> roomIDColumn;

    @FXML
    private TableColumn<RoomDetail, String> roomTypeColumn;

    @FXML
    private TableColumn<RoomDetail, Integer> roomFloorColumn;

    @FXML
    private TableColumn<RoomDetail, Float> roomPriceColumn;

    @FXML
    private TableColumn<RoomDetail, String> roomStatusColumn;
    @FXML
    private ComboBox<String> globalRoomStatus = new ComboBox<>();
    @FXML
    private TextField globalRoomPrice;

    @FXML
    private TextField searchBox;

    @FXML
    private TextField roomId;

    @FXML
    private TextField roomType;

    @FXML
    private TextField roomFloor;

    @FXML
    private TextField roomPrice;
    @FXML
    private CheckBox priceCheckBox;
    @FXML
    private CheckBox statusCheckBox;

    @FXML
    private ChoiceBox<String> roomStatus;
    @FXML
    private ComboBox<String> globalRoomType = new ComboBox<>();
    private ObservableList<RoomDetail> roomList;
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();


    public void initialize() throws SQLException {
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("roomFloor"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));


        // load data from database
        ArrayList<RoomDetail> roomDisplay = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room");
            ResultSet rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                roomDisplay.add(new RoomDetail(rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getFloat("Room_Price"), rs.getString("Room_Status")));
                i++;
            }
            rs.close();
            PreparedStatement ps2 = conn.prepareStatement("SELECT DISTINCT Room_Type FROM room");
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                globalRoomType.getItems().add(rs2.getString("Room_Type"));
            }
            rs2.close();
            PreparedStatement ps3 = conn.prepareStatement("SELECT DISTINCT Room_Status FROM room");
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                globalRoomStatus.getItems().add(rs3.getString("Room_Status"));
            }
            globalRoomStatus.getItems().add("Đang sửa chữa");
            rs3.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // convert the arraylist to observablelist
        roomList = FXCollections.observableArrayList(roomDisplay);


        // load data into the table
        roomTable.setItems(roomList);

        // set up the choice box
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Trống", "Đang sửa chữa");
        roomStatus.setItems(statusOptions);

        roomTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                roomId.setText(newSelection.getRoomID());
                roomType.setText(newSelection.getRoomType());
                roomFloor.setText(Integer.toString(newSelection.getRoomFloor()));
                roomPrice.setText(Float.toString(newSelection.getRoomPrice()));
                roomStatus.setValue(newSelection.getRoomStatus());
            }
        });
        roomTable.setRowFactory(tv -> new TableRow<RoomDetail>() {
            @Override
            protected void updateItem(RoomDetail item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.getRoomStatus().equals("Đang sửa chữa")) {
                    setStyle("-fx-background-color: red;");
                } else {
                    setStyle("");
                }
            }
        });
    }
    public void handleAddRoomButton(MouseEvent event) {
        try {
            Connection conn = DriverManager.getConnection(connectUrl,username , password);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO room (Room_ID, Room_Type, Room_Floor, Room_Price, Room_Status) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, roomId.getText());
            ps.setString(2, roomType.getText());
            ps.setInt(3, Integer.parseInt(roomFloor.getText()));
            ps.setFloat(4, Float.parseFloat(roomPrice.getText()));
            ps.setString(5, roomStatus.getValue());
            ps.executeUpdate();
            conn.close();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleUpdateRoomButton(MouseEvent event) {
        RoomDetail selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            String id = selectedRoom.getRoomID();
            String type = selectedRoom.getRoomType();
            int floor = selectedRoom.getRoomFloor();
            float price = selectedRoom.getRoomPrice();
            String status = selectedRoom.getRoomStatus();

            if (!roomId.getText().isEmpty()) {
                id = roomId.getText();
            }
            if (!roomType.getText().isEmpty()) {
                type = roomType.getText();
            }
            if (!roomFloor.getText().isEmpty()) {
                floor = Integer.parseInt(roomFloor.getText());
            }
            if (!roomPrice.getText().isEmpty()) {
                price = Float.parseFloat(roomPrice.getText());
            }
            if (roomStatus.getValue() != null) {
                status = roomStatus.getValue();
            }



            try {
                if (selectedRoom.getRoomStatus().equals("Chờ trả") || selectedRoom.getRoomStatus().equals("Đang thuê") || selectedRoom.getRoomStatus().equals("Đặt trước")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Phòng đang được sử dụng không thể cập nhật!");

                    alert.setHeaderText(null);
                    alert.showAndWait();
                    return;
                }

                Connection conn = DriverManager.getConnection(connectUrl, username, password);
                PreparedStatement ps = conn.prepareStatement("UPDATE room SET Room_ID = ?, Room_Type = ?, Room_Floor = ?, Room_Price = ?, Room_Status = ? WHERE Room_ID = ?");
                ps.setString(1, id);
                ps.setString(2, type);
                ps.setInt(3, floor);
                ps.setFloat(4, price);
                ps.setString(5, status);
                ps.setString(6, selectedRoom.getRoomID());
                ps.executeUpdate();
                conn.close();
                refreshTable();
            } catch (SQLException e ) {
                e.printStackTrace();
            }
        }
    }

    public void handleDeleteRoomButton(MouseEvent event) {
        RoomDetail selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            try {
                if(selectedRoom.getRoomStatus().equals("Chờ trả") || selectedRoom.getRoomStatus().equals("Đang thuê") || selectedRoom.getRoomStatus().equals("Đặt trước")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Phòng đang được sử dụng không thể xóa!");

                    alert.setHeaderText(null);
                    alert.showAndWait();
                    return;
                }
                Connection conn = DriverManager.getConnection(connectUrl, username, password);
                // delete relationship of checkin table with foreign key Room_ID
                PreparedStatement ps1 = conn.prepareStatement("DELETE FROM checkin WHERE Room_ID = ?");
                ps1.setString(1, selectedRoom.getRoomID());
                ps1.executeUpdate();
                // delete relationship of room_service table with foreign key Room_ID
                PreparedStatement ps2 = conn.prepareStatement("DELETE FROM room_service WHERE Room_ID = ?");
                ps2.setString(1, selectedRoom.getRoomID());
                ps2.executeUpdate();
                // delete relationship of bill table with foreign key Room_ID
                PreparedStatement ps3 = conn.prepareStatement("DELETE FROM bill WHERE Room_ID = ?");
                ps3.setString(1, selectedRoom.getRoomID());
                ps3.executeUpdate();
                // delete the selected room from the room table
                PreparedStatement ps4 = conn.prepareStatement("DELETE FROM room WHERE Room_ID = ?");
                ps4.setString(1, selectedRoom.getRoomID());
                ps4.executeUpdate();
                conn.close();
                refreshTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshTable() {
        roomList.clear();
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                roomList.add(new RoomDetail(rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getFloat("Room_Price"), rs.getString("Room_Status")));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public  int checkIsAdmin(String employeeID) throws SQLException {
        try {
        // check if the user is admin
        Connection check_admin_conn = DriverManager.getConnection(connectUrl,username , password);
        PreparedStatement ps_admin = check_admin_conn.prepareStatement("SELECT * FROM account WHERE Employee_ID = ?");
        ps_admin.setString(1, employeeID);
        ResultSet rs_admin = ps_admin.executeQuery();
        rs_admin.next();
        System.out.println("rs_admin = " + rs_admin.getInt("isAdmin"));

        if (rs_admin.getInt("isAdmin") == 1) {
            return 1;
        } else {
            return 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
    }

    public void handleUpdateGlobally() {
        String acc_pass = null;
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            if (!globalRoomType.getValue().toString().isEmpty()) {
                if (priceCheckBox.isSelected() && !globalRoomPrice.getText().isEmpty()) {
                    PreparedStatement ps = conn.prepareStatement("UPDATE room SET Room_Price = ? WHERE Room_Type = ?");
                    ps.setFloat(1, Float.parseFloat(globalRoomPrice.getText()));
                    ps.setString(2, globalRoomType.getValue().toString());
                    ps.executeUpdate();
                    conn.close();
                    refreshTable();
                }
                if (statusCheckBox.isSelected() && globalRoomStatus.getValue() != null) {
                    PreparedStatement getPassword = conn.prepareStatement("SELECT * FROM account WHERE Employee_ID = ?");
                    getPassword.setString(1, employeeID);
                    ResultSet getPasswordRS = getPassword.executeQuery();
                    if (getPasswordRS.next()){
                        acc_pass = getPasswordRS.getString("account_password");
                    }
                    getPasswordRS.close();
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Xác nhận cập nhật");
                    dialog.setHeaderText("Đây là thao tác nguy hiểm. Vui lòng nhập mật khẩu để xác nhận.");
                    dialog.setContentText("Mật khẩu:");

                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent() && result.get().equals(acc_pass)) {
                        PreparedStatement ps = conn.prepareStatement("UPDATE room SET Room_Status = ? WHERE Room_Type = ?");
                        ps.setString(1, globalRoomStatus.getValue().toString());
                        ps.setString(2, globalRoomType.getValue().toString());
                        ps.executeUpdate();
                        conn.close();
                        refreshTable();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect password. Update cancelled.");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                        return;
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng điền loại phòng!");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

