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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ArrayList;

public class ChiTietCheckOut {
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();


    @FXML
    private Text totalMoney;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button BackButton;

    @FXML
    private Button CheckOutButton;

    @FXML
    private Text checkoutDate;


    // Customer table
    @FXML
    private TableView<Customer> customer;
    @FXML
    private TableColumn<Customer, Long> ordinalNumberOfCustomerColumn;
    @FXML
    private TableColumn<Customer, String> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, Integer> customerBirthYearColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer, String> noteColumn;
    private ObservableList<Customer> displayCustomerList;

    // Room table
    @FXML
    private TableView<RoomDetail> room;
    @FXML
    private TableColumn<RoomDetail, Long> ordinalNumberOfRoomColumn;
    @FXML
    private TableColumn<RoomDetail, String> roomIDColumn;
    @FXML
    private TableColumn<RoomDetail, String> roomTypeColumn;
    @FXML
    private TableColumn<RoomDetail, Integer> roomFloorColumn;
    @FXML
    private TableColumn<RoomDetail, Float> roomPriceColumn;
    private ObservableList<RoomDetail> displayRoomList;

    // Service table
    @FXML
    private TableView<ServiceForCustomer> service;
    @FXML
    private TableColumn<ServiceForCustomer, Long> ordinalNumberOfServiceColumn;
    @FXML
    private TableColumn<ServiceForCustomer, String> serviceIDColumn;
    @FXML
    private TableColumn<ServiceForCustomer, String> serviceNameColumn;
    @FXML
    private TableColumn<ServiceForCustomer, Float> servicePriceColumn;
    @FXML
    private TableColumn<ServiceForCustomer, String> serviceDescriptionColumn;
    private ObservableList<ServiceForCustomer> displayServiceList;

    // Used Service table
    @FXML
    private TableView<UsedService> usedService;
    @FXML
    private TableColumn<UsedService, String> usedServiceIDColumn;
    @FXML
    private TableColumn<UsedService, String> usedServiceNameColumn;
    @FXML
    private TableColumn<UsedService, Float> usedServicePriceColumn;
    @FXML
    private TableColumn<UsedService, Integer> usedServiceQuantityColumn;
    @FXML
    private TableColumn<UsedService, Float> usedServiceTotalColumn;
    private ObservableList<UsedService> displayUsedServiceList;

    @FXML
    private Spinner<Integer> numberOfUsed;

    private ArrayList<RoomDetail> customerRoom;

    private ArrayList<Customer> customerList;

    private String roomID;

    private String customerID;
    int time = 0;


    private String employeeID;

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }



    public ArrayList<RoomDetail> getCustomerRoom() {
        return customerRoom;
    }

    public void setCustomerRoom(ArrayList<RoomDetail> customerRoom) {
        this.customerRoom = customerRoom;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public ChiTietCheckOut(String customerID, ArrayList<RoomDetail> customerRoom, ArrayList<Customer> customerList, String roomID) {
        this.customerID = customerID;
        this.customerRoom = customerRoom;
        this.customerList = customerList;
        this.roomID = roomID;
    }

    public ChiTietCheckOut() {
        this.customerID = "";
        this.customerRoom = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.roomID = "asd";
    }

    private Connection connect() {
        // SQL connection string
        String url = connectUrl;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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
    private ArrayList<UsedService> getUsedServices(String roomID) {
        ArrayList<UsedService> usedServiceList = new ArrayList<>();
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT service.Service_ID, service.Service_Name, room_service.Service_Quantity, service.Service_Price FROM service INNER JOIN room_service ON service.Service_ID = room_service.Service_ID WHERE room_service.Room_ID = '" + roomID + "';");
            while (rs.next()) {
                UsedService usedService = new UsedService(rs.getString("Service_ID"), rs.getString("Service_Name"),rs.getFloat("Service_Price") , rs.getInt("Service_Quantity"), rs.getFloat("Service_Price") * rs.getInt("Service_Quantity"));
                usedServiceList.add(usedService);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return usedServiceList;
    }
    public void setData() {
        System.out.println(employeeID);
        checkoutDate.setText(LocalDate.now().toString());
        CheckOutButton.setDisable(false);
        CheckOutButton.setText("Xác nhận thanh toán");
        displayCustomerList = FXCollections.observableArrayList(getCustomerList());
        ordinalNumberOfCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("STT"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Cccd"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerBirthYearColumn.setCellValueFactory(new PropertyValueFactory<>("BirthYear"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("Note"));
        customer.setItems(displayCustomerList);

        displayRoomList = FXCollections.observableArrayList(getCustomerRoom());
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("RoomID"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("RoomType"));
        roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("RoomFloor"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("RoomPrice"));
        room.setItems(displayRoomList);


        ArrayList<ServiceForCustomer> serviceList = new ArrayList<>();



        ordinalNumberOfServiceColumn.setCellValueFactory(new PropertyValueFactory<>("OrdinalNumber"));
        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceID"));
        servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        serviceDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM service");
            int i = 1;
            while (rs.next()) {
                ServiceForCustomer newService = new ServiceForCustomer(i, rs.getString("Service_ID"),rs.getString("Service_Name"), rs.getFloat("Service_Price"), rs.getString("Service_Description"));
                serviceList.add(newService);
                i++;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        displayServiceList = FXCollections.observableArrayList(serviceList);
        service.setItems(displayServiceList);

        ArrayList<UsedService> usedServiceList = new ArrayList<>();
        usedServiceNameColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
        usedServiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceID"));
        usedServicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        usedServiceQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        usedServiceTotalColumn.setCellValueFactory(new PropertyValueFactory<>("Total"));
        displayUsedServiceList = FXCollections.observableArrayList(usedServiceList);
        usedService.setItems(displayUsedServiceList);
        numberOfUsed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));


        for (RoomDetail roomDisplay : displayRoomList) {
            displayUsedServiceList.addAll(getUsedServices(String.valueOf(Integer.parseInt(roomDisplay.getRoomID()))));
        }

        calculateTotalPrice();

    }



    public void handleAddUsedServiceButton(MouseEvent event) {
        ServiceForCustomer selectedService = service.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            int quantity = numberOfUsed.getValue();
            float price = selectedService.getPrice();
            float total = price * quantity;
            UsedService usedService = new UsedService(selectedService.getServiceID(), selectedService.getServiceName(), price, quantity, total);
            displayUsedServiceList.add(usedService);
        }
        calculateTotalPrice();
    }

    public void handleRemoveUsedServiceButton(MouseEvent event) {
        UsedService selectedUsedService = usedService.getSelectionModel().getSelectedItem();
        if (selectedUsedService != null) {
            displayUsedServiceList.remove(selectedUsedService);
        }
        calculateTotalPrice();
    }

    public void updateUsedService(MouseEvent event) {
        UsedService selectedService = usedService.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            int quantity = numberOfUsed.getValue();
            float total = quantity * selectedService.getPrice();
            selectedService.setQuantity(quantity);
            selectedService.setTotal(total);
            usedService.refresh();
        }
        calculateTotalPrice();
    }

    public void handleBackButton(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check-out.fxml"));
        Parent dashboardParent = loader.load();
        CheckOutController checkOutController = loader.getController();
        checkOutController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) BackButton.getScene().getWindow();
        window.setScene(dashboardScene);

    }

    public void calculateTotalPrice() {
        float tempMoney = 0;

        for (RoomDetail roomDisplay : displayRoomList) {
            try {
                Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Checkin_Date FROM checkin WHERE Room_ID = '" + roomDisplay.getRoomID() + "'");
                while (rs.next()) {
                    time = (int) ChronoUnit.DAYS.between(rs.getDate("Checkin_Date").toLocalDate(), LocalDate.now());
                    tempMoney += roomDisplay.getRoomPrice() * (ChronoUnit.DAYS.between(rs.getDate("Checkin_Date").toLocalDate(), LocalDate.now()));
                }
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        for (UsedService usedService : displayUsedServiceList) {
            tempMoney += usedService.getTotal();
        }
        totalMoney.setText(String.format("%.2f", tempMoney));
    }

    public void handleCheckoutButton(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc về việc thanh toán hay không?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            for (Customer customerDisplay : displayCustomerList) {
                for (RoomDetail roomDisplay : displayRoomList) {
                    try {
                        Connection conn = connect();


                        //Statement stmt = conn.prepareStatement("INSERT INTO checkout ( guestID, checkOutTime, checkOutDate, totalTime, totalCost, roomID, employeeID) VALUES (?,?,?,?,?,?,?) ");
                        Statement stmt = conn.prepareStatement("INSERT INTO checkout ( Guest_ID, checkout_Time, checkout_Date, total_Time,Room_ID, employee_ID) VALUES (?,?,?,?,?,?) ");
                        ((PreparedStatement) stmt).setString(1, customerDisplay.getCccd());
                        ((PreparedStatement) stmt).setTime(2, java.sql.Time.valueOf(LocalTime.now()));
                        ((PreparedStatement) stmt).setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                        ((PreparedStatement) stmt).setInt(4, time);
                        ((PreparedStatement) stmt).setString(5, roomDisplay.getRoomID());
                        ((PreparedStatement) stmt).setString(6, employeeID);
                        ((PreparedStatement) stmt).executeUpdate();

                        Statement billStatement = conn.prepareStatement("INSERT INTO bill ( Guest_ID, Total_Cost, Room_ID) VALUES (?,?,?) ");
                        ((PreparedStatement) billStatement).setString(1, customerDisplay.getCccd());
                        float total = Float.parseFloat(totalMoney.getText().replace(",", "."));
                        ((PreparedStatement) billStatement).setFloat(2, total);
                        ((PreparedStatement) billStatement).setString(3, roomDisplay.getRoomID());

                        ((PreparedStatement) billStatement).executeUpdate();
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

                for (RoomDetail roomDisplay : displayRoomList) {
                    try {
                        Connection conn = connect();
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate("INSERT INTO used_service\n" +
                                "SELECT * FROM room_service\n" +
                                "WHERE Room_ID = '" + roomDisplay.getRoomID() + "'");
                        stmt.executeUpdate("DELETE FROM room_service WHERE Room_ID = '" + roomDisplay.getRoomID() + "'");
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        Connection conn = connect();
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate("DELETE FROM checkin WHERE Room_ID = '" + roomDisplay.getRoomID() + "'");
                        Statement stmt2 = conn.createStatement();
                        stmt2.executeUpdate("UPDATE room SET Room_Status = 'Trống' WHERE Room_ID = '" + roomDisplay.getRoomID() + "'");
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                CheckOutButton.setDisable(true);
                CheckOutButton.setText("Đã thanh toán");

                /*
                Parent root = FXMLLoader.load(getClass().getResource("Check-out.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) CheckoutButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                */

            }
        }
    }


}

