package app.ie303hotelmanagement;

import effects.ButtonAnimation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CheckOutController {
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();

    @FXML private Button checkoutButton;
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
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();

    private ObservableList<RoomDisplay> roomList;
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

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
            LocalDateTime now = LocalDateTime.now();
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            // Update status of room from "Đang thuê" to "Chờ trả" if the check-out date is today
            PreparedStatement psUpdateRoomStatus = conn.prepareStatement("UPDATE room SET Room_Status = 'Chờ trả' WHERE Room_Status = 'Đang thuê' and Room_ID IN (SELECT Room_ID FROM checkin WHERE Expected_Checkout_Date <= ?)");
            psUpdateRoomStatus.setString(1, now.toString());
            psUpdateRoomStatus.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE Room_Status = 'Chở trả' OR Room_Status = 'Đang thuê'");
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
        ButtonAnimation.addHoverEffect(checkoutButton);
        // convert the arraylist to observablelist
        roomList = FXCollections.observableArrayList(roomDisplay);


        // load data into the table
        // create a map to store the expected checkout dates for each room
        Map<String, LocalDateTime> expectedCheckoutDates = new HashMap<>();

// query the expected checkout dates for all rooms
        try (Connection conn = DriverManager.getConnection(DataConnector.getDatabaseUrl(), DataConnector.getUsername(), DataConnector.getPassword());
             PreparedStatement updateRoomStatus = conn.prepareStatement("SELECT Room_ID, Expected_Checkout_Date FROM Checkin WHERE Room_ID IN (SELECT Room_ID FROM room WHERE Room_Status = 'Chờ trả' OR Room_Status = 'Đang thuê')")) {
            ResultSet rs = updateRoomStatus.executeQuery();
            while (rs.next()) {
                String roomID = rs.getString("Room_ID");
                LocalDateTime expectedCheckoutDate = rs.getTimestamp("Expected_Checkout_Date").toLocalDateTime();
                expectedCheckoutDates.put(roomID, expectedCheckoutDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        waitCheckOut.setItems(roomList);
        waitCheckOut.setRowFactory(tv -> new TableRow<RoomDisplay>() {
            @Override
            protected void updateItem(RoomDisplay item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("-fx-background-color: transparent;");
                } else if (item.getRoomStatus().equals("Chờ trả")) {
                    LocalDateTime expectedCheckoutDate = expectedCheckoutDates.get(item.getRoomID());
                    if (expectedCheckoutDate != null) {
                        Duration duration = Duration.between(expectedCheckoutDate, LocalDateTime.now());
                        long hours = duration.toHours();
                        if (hours <= 1) {
                            item.setRoomStatus("Sắp trả");
                            setStyle("");
                        } else if (hours > 1) {
                            item.setRoomStatus("Quá hạn + " + hours + " giờ");
                            setStyle("-fx-background-color: #ff0000;");
                        }
                    }
                } else if (item.getRoomStatus().startsWith("Quá hạn")) {
                    setStyle("-fx-background-color: #ff0000;");
                } else if (item.getRoomStatus().equals("Sắp trả")){
                    setStyle("-fx-background-color: yellow;");
                } else if (item.getRoomStatus().equals("Đang thuê")){
                    setStyle("-fx-background-color: green;");
                } else {
                    setStyle("");
                }
            }
        });
    }



    public void handleSearchBox(KeyEvent event) {
        String search = searchBox.getText();
        ObservableList<RoomDisplay> filteredList = FXCollections.observableArrayList();
        filteredList.clear();
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
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
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
                customerDisplay.add(new Customer(customerOrdinary, rsCustomer.getString("Guest_ID"), rsCustomer.getString("Guest_Name"), rsCustomer.getInt("Number_Of_Checkin"), rsCustomer.getString("Guest_Tier"), rsCustomer.getInt("Guest_BirthYear"), rsCustomer.getString("Guest_Phone"), rsCustomer.getString("Guest_Notes")));
                customerOrdinary++;
            }


            // Get the all the rooms that the customer hired
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE Room_Status = 'Chờ trả' OR  Room_Status = 'Đang thuê' and Room_ID IN (SELECT Room_ID FROM checkin WHERE Guest_ID = ?)");
            ps.setString(1, customerDisplay.get(0).getCccd());
            String guestID = customerDisplay.get(0).getCccd();
            ResultSet rs = ps.executeQuery();

            // Add all the rooms to the list to use in the next scene
            ArrayList<RoomDetail> roomDisplay = new ArrayList<>();
            int i = 1;
            while (rs.next()) {
                roomDisplay.add(new RoomDetail(rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getFloat("Room_Price"), rs.getString("Room_Status")));
                i++;
            }


            // Load the next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckoutDetails.fxml"));
            Parent root = loader.load();

            //edited here
            CheckoutDetails controller = loader.<CheckoutDetails>getController();

            // Set the customer list to the next scene
            controller.setCustomer(guestID);
            // Set the room list to the next scene
            // Set the room ID to the next scene so we can easily find customer base on room ID
            // Set the employee ID to the next scene
            controller.setEmployeeID(employeeID);
            // Init the data to the next scene
            controller.setData();
            // Set the scene
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void refreshTable() {
        roomList.clear();
        ArrayList<RoomDisplay> roomDisplay = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);

            // Update status of room from "Đang thuê" to "Chờ trả" if the check-out date is today
            PreparedStatement psUpdateRoomStatus = conn.prepareStatement("UPDATE room SET Room_Status = 'Chờ trả' WHERE Room_Status = 'Đang thuê' and Room_ID IN (SELECT Room_ID FROM checkin WHERE curdate() >= Expected_Checkout_Date)");
            psUpdateRoomStatus.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE Room_Status = 'Chờ trả' OR Room_Status = 'Đang thuê'");
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
        waitCheckOut.setItems(roomList);
    }
}


