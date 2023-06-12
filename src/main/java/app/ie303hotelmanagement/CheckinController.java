package app.ie303hotelmanagement;

import effects.ButtonAnimation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class CheckinController {
    @FXML private TextField inputCCCD;
    private ObservableList<String> guestIDs = FXCollections.observableArrayList();
    @FXML private TextField inputName;
    @FXML private TextField inputYearOfBirth;
    @FXML private TextField inputPhone;
    @FXML private TextField inputCheckinTime;
    @FXML private TextField inputCheckoutTime;
    @FXML private TableView<Room> roomTable;
    @FXML private TableColumn<Room, Integer> sTT;
    @FXML private TableColumn<Room, String> roomID;
    @FXML private TableColumn<Room, String> roomType;
    @FXML private TableColumn<Room, Integer> floor;
    @FXML private TableColumn<Room, String> status;
    @FXML private TextField inputRoom;
    @FXML private DatePicker inputCheckinDate;
    @FXML private TextField inputNumberOfGuest;
    @FXML private TextField inputNote;
    @FXML private DatePicker inputCheckoutDate;

    @FXML private Button checkButton;
    @FXML private Spinner<Integer> inputExpectedCheckinHour;
    @FXML private Spinner<Integer> inputExpectedCheckinMinute;
    @FXML private Spinner<Integer> inputExpectedCheckoutHour;
    @FXML private Spinner<Integer> inputExpectedCheckoutMinute;
    @FXML private DatePicker inputExpectedCheckinDate;
    @FXML private DatePicker inputExpectedCheckoutDate;
    @FXML private Button reservateButton;
    @FXML private Button cancelReservation;
    @FXML private Button checkinButton;

    private String employeeID = EmployeeSingleton.getInstance().getEmployeeID();//đây là biến để lưu lại employeeID khi chuyển qua lại giữa các trang
    private int count = 1;
    private String connectionString = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
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
            PreparedStatement stmt2 = conn.prepareStatement("SELECT Room_ID, Number_ofGuest FROM reservation WHERE Reservation_Status = 'Đặt trước' AND Guest_ID = ?");
            stmt2.setString(1, inputCCCD.getText());
            ResultSet rs2 = stmt2.executeQuery();
            StringBuilder reservedRooms = new StringBuilder();
            StringBuilder numberOfGuest = new StringBuilder();
            while (rs2.next()) {
                reservedRooms.append(rs2.getString("Room_ID")).append(",");
                numberOfGuest.append(rs2.getString("Number_ofGuest")).append(",");
            }
            if (reservedRooms.length() > 0) {
                reservedRooms.deleteCharAt(reservedRooms.length() - 1); // remove last comma
            }
            if (numberOfGuest.length() > 0) {
                numberOfGuest.deleteCharAt(numberOfGuest.length() - 1); // remove last comma
            }
            rs2.close();
            PreparedStatement stmt3 = conn.prepareStatement("SELECT Expected_Checkin_Date, Expected_Checkout_Date, Number_ofGuest FROM reservation WHERE Reservation_Status = 'Đặt trước' AND Guest_ID = ?");
            stmt3.setString(1, inputCCCD.getText());
            ResultSet rs3 = stmt3.executeQuery();
            if (rs3.next()) {
                inputCheckinDate.setValue(rs3.getDate("Expected_Checkin_Date").toLocalDate());
                inputCheckoutDate.setValue(rs3.getDate("Expected_Checkout_Date").toLocalDate());
                inputRoom.setText(reservedRooms.toString());
                inputNumberOfGuest.setText(numberOfGuest.toString());
                inputCheckinTime.setText(rs3.getTime("Expected_Checkin_Date").toString());
                inputCheckoutTime.setText(rs3.getTime("Expected_Checkout_Date").toString());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stmt = connection.prepareStatement("UPDATE reservation, room SET reservation.Reservation_Status = 'Quá hạn', room.Room_Status = 'Quá hạn' WHERE reservation.Room_ID = room.Room_ID AND reservation.Expected_Checkin_Date < ? AND reservation.Reservation_Status = 'Đặt trước'");
            stmt.setString(1, (String.valueOf(LocalDateTime.now())));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SpinnerValueFactory<Integer> checkinHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        inputExpectedCheckinHour.setValueFactory(checkinHourFactory);

        // Set up inputExpectedCheckoutHour spinner
        SpinnerValueFactory<Integer> checkoutHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        inputExpectedCheckoutHour.setValueFactory(checkoutHourFactory);
        SpinnerValueFactory<Integer> checkinMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        inputExpectedCheckinMinute.setValueFactory(checkinMinuteFactory);

        SpinnerValueFactory<Integer> checkoutMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
        inputExpectedCheckoutMinute.setValueFactory(checkoutMinuteFactory);
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room where Room_Status = 'Trống' or Room_Status = 'Đặt trước' or Room_Status = 'Quá hạn'");
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

        roomTable.setRowFactory(tv -> new TableRow<Room>() {
            @Override
            public void updateItem(Room room, boolean empty) {
                super.updateItem(room, empty);
                if (room == null) {
                    setStyle("");
                } else if (room.getStatus().equals("Trống")) {
                    setStyle("-fx-background-color: #92B0CA;");
                } else if (room.getStatus().equals("Đặt trước")) {
                    setStyle("-fx-background-color: #0F4C81;");
                } else {
                    setStyle("-fx-background-color: #FF0000;");
                }
            }
        });

        //auto complete
        ButtonAnimation.addHoverEffect(checkinButton);
        ButtonAnimation.addHoverEffect(reservateButton);

        roomTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    Connection connection = DriverManager.getConnection(connectionString, username, password);
                    inputRoom.setText(newSelection.getRoomID());
                    PreparedStatement stmt4 = connection.prepareStatement("SELECT * FROM reservation WHERE (Reservation_Status = 'Đặt trước' OR Reservation_Status = 'Quá hạn') AND Room_ID = ?");
                    stmt4.setString(1, inputRoom.getText());
                    ResultSet rs4 = stmt4.executeQuery();
                    if (rs4.next()) {
                        inputCheckinDate.setValue(rs4.getDate("Expected_Checkin_Date").toLocalDate());
                        inputCheckoutDate.setValue(rs4.getDate("Expected_Checkout_Date").toLocalDate());
                        inputNumberOfGuest.setText(rs4.getString("Number_ofGuest"));
                        inputCheckinTime.setText(rs4.getTime("Expected_Checkin_Date").toString());
                        inputCheckoutTime.setText(rs4.getTime("Expected_Checkout_Date").toString());
                        inputCCCD.setText(rs4.getString("Guest_ID"));
                        PreparedStatement stmt5 = connection.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
                        stmt5.setString(1, rs4.getString("Guest_ID"));
                        ResultSet rs5 = stmt5.executeQuery();
                        if (rs5.next()) {
                            inputName.setText(rs5.getString("Guest_Name"));
                            inputYearOfBirth.setText(rs5.getString("Guest_BirthYear"));
                            inputPhone.setText(rs5.getString("Guest_Phone"));
                            if (rs5.getString("Guest_Name")==null){
                                inputName.setText("");
                            }
                        }
                        rs5.close();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @FXML
    private void handleInputDate() {
        LocalDate checkinDate = inputExpectedCheckinDate.getValue();
        LocalDate checkoutDate = inputExpectedCheckoutDate.getValue();
        if (checkinDate != null && checkoutDate != null) {
            if (checkinDate.isAfter(checkoutDate)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Ngày trả phòng phải sau ngày nhận phòng");
                alert.setContentText("Vui lòng nhập lại ngày trả phòng");
                alert.showAndWait();
                inputExpectedCheckoutDate.setValue(null);
            }
        }
    }
    @FXML
    private void handleCheckButton() {
        // Get the input values
        int checkinHour = inputExpectedCheckinHour.getValue();
        int checkinMinute = inputExpectedCheckinMinute.getValue();
        LocalDate checkinDate = inputExpectedCheckinDate.getValue();
        int checkoutHour = inputExpectedCheckoutHour.getValue();
        int checkoutMinute = inputExpectedCheckoutMinute.getValue();
        LocalDate checkoutDate = inputExpectedCheckoutDate.getValue();

        // Combine the input values into DateTime objects
        LocalDateTime checkinDateTime = LocalDateTime.of(checkinDate, LocalTime.of(checkinHour, checkinMinute));
        LocalDateTime checkoutDateTime = LocalDateTime.of(checkoutDate, LocalTime.of(checkoutHour, checkoutMinute));

        try {
            roomTable.getItems().clear();
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            // Prepare the SQL statement
            String sql = "SELECT * FROM room, reservation WHERE room.Room_ID = reservation.Room_ID AND Room_Status = 'Trống' OR Room_Status = 'Đặt trước'";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Execute the query and iterate over the results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Check if the room meets the criteria
                LocalDateTime expectedCheckinDateTime = rs.getTimestamp("Expected_Checkin_Date").toLocalDateTime();
                if (expectedCheckinDateTime.isAfter(checkinDateTime) && expectedCheckinDateTime.isAfter(checkoutDateTime)) {
                    // Check if the room is not reserved during the requested period
                    PreparedStatement reservationStmt = conn.prepareStatement("SELECT Room_ID FROM reservation WHERE Room_ID = ? AND Expected_Checkin_Date < ? AND Expected_Checkout_Date > ?");
                    reservationStmt.setString(1, rs.getString("Room_ID"));
                    reservationStmt.setString(2, checkoutDate.toString());
                    reservationStmt.setString(3, checkinDate.toString());
                    ResultSet reservationRs = reservationStmt.executeQuery();
                    if (!reservationRs.next()) {
                        // Create a new Room object and add it to the table
                        Room room = new Room(count, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status"));
                        roomTable.getItems().add(room);
                        count++;
                    }
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            String sql = "SELECT * FROM room, checkin WHERE room.Room_ID = checkin.Room_ID AND Expected_Checkout_Date <" + "'" + checkinDate + "'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(count, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status"));
                roomTable.getItems().add(room);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            String sql = "SELECT * FROM room WHERE Room_Status = 'Trống' AND Room_ID NOT IN (SELECT Room_ID FROM reservation WHERE Expected_Checkin_Date < ? AND Expected_Checkout_Date > ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, checkoutDate.toString());
            stmt.setString(2, checkinDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Room room = new Room(count, rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getInt("Room_Floor"), rs.getString("Room_Status"));
                roomTable.getItems().add(room);
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReservateButton(ActionEvent event) throws IOException {
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);
            String[] roomIDs = inputRoom.getText().split(","); // split input by comma
            String[] numberOfGuest = inputNumberOfGuest.getText().split(","); // split input by comma
            if (roomIDs.length != numberOfGuest.length) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Số phòng và số khách không khớp nhau, vui lòng thử lại.");
                alert.showAndWait();
                return;
            }
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
                LocalDate checkinDate = inputCheckinDate.getValue();
                LocalTime checkinTime = LocalTime.parse(inputCheckinTime.getText());
                LocalDateTime expectedCheckinDateTime = LocalDateTime.of(checkinDate, checkinTime);

                LocalDate checkoutDate = inputCheckoutDate.getValue();
                LocalTime checkoutTime = LocalTime.parse(inputCheckoutTime.getText());
                LocalDateTime expectedCheckoutDateTime = LocalDateTime.of(checkoutDate, checkoutTime);

                for (int i = 0; i < roomIDs.length; i++) {
                    String roomID = roomIDs[i];
                    String numberOfGuests = numberOfGuest[i];
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO reservation (Guest_ID, Employee_ID, Room_ID, Reserve_Date, Expected_Checkin_Date, Expected_Checkout_Date, Number_OfGuest, Reservation_Status) VALUES (?, ?, ?, ?, ?, ?, ?, 'Đặt trước')");
                    stmt2.setString(1, inputCCCD.getText());
                    stmt2.setString(2, employeeID);
                    stmt2.setString(3, roomID);
                    stmt2.setString(4, LocalDateTime.now().toString());
                    stmt2.setString(5, expectedCheckinDateTime.toString());
                    stmt2.setString(6, expectedCheckoutDateTime.toString());
                    stmt2.setString(7, numberOfGuests);
                    stmt2.executeUpdate();

                    PreparedStatement stmt3 = conn.prepareStatement("UPDATE room SET Room_Status = 'Đặt trước' WHERE Room_ID = ?");
                    stmt3.setString(1, roomID);
                    stmt3.executeUpdate();
                    Log reservationLog = new Log(LocalDateTime.now(), "Đặt phòng", "Thành công", "Đặt phòng cho khách có CCCD " + inputCCCD.getText() + "vào phòng " + roomID);
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
            String[] roomIDs = inputRoom.getText().split(",");
            String[] numberOfGuests = inputNumberOfGuest.getText().split(",");
            if(roomIDs.length != numberOfGuests.length) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Số lượng phòng và số lượng khách không khớp.");
                alert.showAndWait();
                return;
            }
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
                LocalDate checkinDate = inputCheckinDate.getValue();
                LocalTime checkinTime = LocalTime.parse(inputCheckinTime.getText());
                LocalDateTime expectedCheckinDateTime = LocalDateTime.of(checkinDate, checkinTime);

                LocalDateTime checkinDateTime = LocalDateTime.of(inputCheckinDate.getValue(),LocalTime.parse(inputCheckinTime.getText()));
                LocalDateTime checkoutDateTime = LocalDateTime.of(inputCheckoutDate.getValue(), LocalTime.parse(inputCheckoutTime.getText()));


                PreparedStatement stmt4 = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
                stmt4.setString(1, inputCCCD.getText());
                ResultSet rs2 = stmt4.executeQuery();
                if (!rs2.next()) {
                    PreparedStatement stmt5 = conn.prepareStatement("INSERT INTO guest (Guest_ID, Guest_Name, Number_Of_Checkin, Guest_Tier, Guest_Phone, Guest_BirthYear, Guest_Notes) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    stmt5.setString(1, inputCCCD.getText());
                    stmt5.setString(2, inputName.getText());
                    stmt5.setInt(3, 1);
                    stmt5.setString(4, "Mới");
                    stmt5.setString(5, inputPhone.getText());
                    stmt5.setString(6, inputYearOfBirth.getText());
                    stmt5.setString(7, inputNote.getText());
                    stmt5.executeUpdate();
                } else {
                    PreparedStatement stmt5 = conn.prepareStatement("UPDATE guest SET Number_Of_Checkin = Number_Of_Checkin + 1 WHERE Guest_ID = ?");
                    stmt5.setString(1, inputCCCD.getText());
                    stmt5.executeUpdate();
                    stmt5.close();
                    PreparedStatement getBill = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                    getBill.setString(1, inputCCCD.getText());
                    getBill.setInt(2, TiersSingleton.getInstance().getDiamondNearestCheckouts());
                    ResultSet billResult = getBill.executeQuery();
                    if (billResult.next() && billResult.getFloat("Total") >= TiersSingleton.getInstance().getDiamondNearestCheckoutValue()) {
                        PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Kim cương' WHERE Guest_ID = ?");
                        stmt6.setString(1, inputCCCD.getText());
                        stmt6.executeUpdate();
                    } else {
                        PreparedStatement getBill2 = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                        getBill2.setString(1, inputCCCD.getText());
                        getBill2.setInt(2, TiersSingleton.getInstance().getGoldNearestCheckouts());
                        ResultSet billResult2 = getBill2.executeQuery();
                        if (billResult2.next() && billResult2.getFloat("Total") >= TiersSingleton.getInstance().getGoldNearestCheckoutValue()) {
                            PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Vàng' WHERE Guest_ID = ?");
                            stmt6.setString(1, inputCCCD.getText());
                            stmt6.executeUpdate();
                        } else {
                            PreparedStatement getBill3 = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                            getBill3.setString(1, inputCCCD.getText());
                            getBill3.setInt(2, TiersSingleton.getInstance().getSilverNearestCheckouts());
                            ResultSet billResult3 = getBill3.executeQuery();
                            if (billResult3.next() && billResult3.getFloat("Total") >= TiersSingleton.getInstance().getSilverNearestCheckoutValue()) {
                                PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Bạc' WHERE Guest_ID = ?");
                                stmt6.setString(1, inputCCCD.getText());
                                stmt6.executeUpdate();
                            } else {
                                PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Mới' WHERE Guest_ID = ?");
                                stmt6.setString(1, inputCCCD.getText());
                                stmt6.executeUpdate();
                            }
                            getBill3.close();
                        }
                    }
                    getBill.close();
                }
                for (int i = 0; i < roomIDs.length; i++) {
                    String roomID = roomIDs[i];
                    int numberOfGuest = Integer.parseInt(numberOfGuests[i].trim());
                    PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO Checkin (Guest_ID, Room_ID, Checkin_Date, Expected_Checkout_Date, Number_OfGuest, Employee_ID) VALUES (?, ?, ?, ?, ?, ?)");
                    stmt2.setString(1, inputCCCD.getText());
                    stmt2.setString(2, roomID);
                    stmt2.setTimestamp(3, Timestamp.valueOf(checkinDateTime));
                    stmt2.setTimestamp(4, Timestamp.valueOf(checkoutDateTime));
                    stmt2.setInt(5, numberOfGuest);
                    stmt2.setString(6, employeeID);
                    stmt2.executeUpdate();

                    PreparedStatement stmt3 = conn.prepareStatement("UPDATE room SET Room_Status = 'Đang thuê' WHERE Room_ID = ?");
                    stmt3.setString(1, roomID);
                    stmt3.executeUpdate();

                    Log checkinlog = new Log(LocalDateTime.now(), "Nhận phòng", "Thành công", "Checkin cho khách hàng " + inputCCCD.getText() + " vào phòng " + roomID);
                    checkinlog.insertLog();
                }
                // update reservation
                PreparedStatement stmt6 = conn.prepareStatement("UPDATE reservation SET Reservation_Status = 'Thành công' WHERE Guest_ID = ?");
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
        if (inputCCCD.getText().isEmpty() || inputRoom.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng nhập CCCD và số phòng.");
            alert.showAndWait();
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(connectionString, username, password);

            // create confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Xác nhận");
            alert.setHeaderText("Bạn có chắc chắn muốn hủy đặt phòng?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reservation WHERE Guest_ID = ? AND (Reservation_Status = 'Đặt trước' OR Reservation_Status = 'Quá hạn')");
                stmt.setString(1, inputCCCD.getText());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (!rs.getString("Room_ID").equals(inputRoom.getText())) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Lỗi");
                        alert2.setHeaderText("Không tìm thấy đặt phòng.");
                        alert2.showAndWait();
                        return;
                    }
                }
                PreparedStatement stmt2 = conn.prepareStatement("UPDATE room SET Room_Status = 'Trống' WHERE Room_ID = ?");
                stmt2.setString(1, inputRoom.getText());
                stmt2.executeUpdate();
                PreparedStatement stmt3 = conn.prepareStatement("UPDATE reservation SET Reservation_Status = 'Hủy' WHERE Guest_ID = ? AND Room_ID = ?");
                stmt3.setString(1, inputCCCD.getText());
                stmt3.setString(2, inputRoom.getText());
                stmt3.executeUpdate();

                Log cancelReservationLog = new Log(LocalDateTime.now(), "Hủy đặt phòng", "Thành công", "Đã hủy đặt phòng cho khách hàng " + inputCCCD.getText() + ", phòng " + roomID);
                cancelReservationLog.insertLog();
                conn.close();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Thành công");
                successAlert.setHeaderText("Hủy đặt phòng thành công.");
                successAlert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
                Parent parent = loader.load();
                CheckinController checkinController = loader.getController();
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
}

