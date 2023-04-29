package app.ie303hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class RoomController {
    @FXML
    private Button LogoutButton;

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
    private ChoiceBox<String> roomStatus;

    private ObservableList<RoomDetail> roomList;

    public void initialize() {
        // set up the columns in the table
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("roomFloor"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));


        // load data from database
        ArrayList<RoomDetail> roomDisplay = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room");
            ResultSet rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                roomDisplay.add(new RoomDetail(rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getFloat("Room_Price"), rs.getString("Room_Status")));
                i++;
            }
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
    }

    public void handleLogoutButton(ActionEvent event) throws IOException {
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

    public void handleAddRoomButton(MouseEvent event) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
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

                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
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
                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
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
}

