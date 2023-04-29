package app.ie303hotelmanagement;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class CheckOutController {

    @FXML
    private Button LogoutButton;

    @FXML
    private TableView<RoomDisplay> waitCheckOut;

    @FXML
    private TableColumn<RoomDisplay, Integer> ordinalNumberColumn;

    @FXML
    private TableColumn<RoomDisplay, String> roomIDColumn;

    @FXML
    private TableColumn<RoomDisplay, String> roomTypeColumn;

    @FXML
    private TableColumn<RoomDisplay, Integer> roomFloorColumn;

    @FXML
    private TableColumn<RoomDisplay, String> roomStatusColumn;

    @FXML
    private TextField searchBox;

    private ObservableList<RoomDisplay> roomList;

    public void initialize() {
        // set up the columns in the table
        ordinalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("ordinalNumber"));
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("roomFloor"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

        // load data from database
        ArrayList<RoomDisplay> roomDisplay = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE Room_Status = 'Trống'");
            ResultSet rs = ps.executeQuery();
            int i = 1;
            while (rs.next()) {
                roomDisplay.add(new RoomDisplay(i, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status")));
                i++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // convert the arraylist to observablelist
        roomList = FXCollections.observableArrayList(roomDisplay);


        // load data into the table
        waitCheckOut.setItems(roomList);
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

    public void handleSearchBox(KeyEvent event) {
        String search = searchBox.getText();
        ObservableList<RoomDisplay> filteredList = FXCollections.observableArrayList();
        for (RoomDisplay room : roomList) {
            if (room.getRoomID().contains(search)) {
                filteredList.add(room);
            }
        }

        waitCheckOut.setItems(filteredList);
    }

    @FXML
    void handleCheckOut(MouseEvent event) throws IOException, SQLException {
        RoomDisplay selectedRoom = waitCheckOut.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            Connection conn = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");
            // Get the customer id
            PreparedStatement psCustomer = conn.prepareStatement("SELECT *\n" +
                    "                    FROM guest g\n" +
                    "                    INNER JOIN  checkin ci ON g.GUEST_ID = ci.GUEST_ID\n" +
                    "                    INNER JOIN room r ON r.ROOM_ID = ci.ROOM_ID\n" +
                    "                    WHERE r.ROOM_ID = ?");
            psCustomer.setString(1, selectedRoom.getRoomID());
            ResultSet rsCustomer = psCustomer.executeQuery();

            // Add the customer information to list to use in the next scene

            ArrayList<Customer> customerDisplay = new ArrayList<>();
            int customerOrdinary = 1;
            while (rsCustomer.next()) {
                customerDisplay.add(new Customer(customerOrdinary,rsCustomer.getString("Guest_ID"),rsCustomer.getString("Guest_Name"),rsCustomer.getInt("Guest_BirthYear"),rsCustomer.getString("Guest_Phone"),rsCustomer.getString("Guest_Notes")));
                customerOrdinary++;
            }


            // Get the all the rooms that the customer hired
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE Customer_ID = ?");
            ps.setString(1, rsCustomer.getString("Customer_ID"));
            ResultSet rs = ps.executeQuery();

            // Add all the rooms to the list to use in the next scene
            ArrayList<RoomDisplay> roomDisplay = new ArrayList<>();
            int i = 1;
            while (rs.next()) {
                roomDisplay.add(new RoomDisplay(i, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status")));
                i++;
            }

            // Load the next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Chi-tiet-check-out.fxml"));
            Parent root = loader.load();
            ChiTietCheckOut controller = loader.getController();

            // Set the customer list to the next scene
            controller.setCustomerList(customerDisplay);
            // Set the room list to the next scene
            controller.setCustomerRoom(roomDisplay);
            // Set the room ID to the next scene so we can easily find customer base on room ID
            controller.setRoomID(selectedRoom.getRoomID());

            // Set the scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}


