package app.ie303hotelmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ArrayList;

public class ChiTietCheckOut {

    @FXML
    private Button LogoutButton;

    @FXML
    private TextField Name;

    // Customer table
    @FXML
    private TableView<Customer> customer;
    @FXML
    private TableColumn<Customer, Integer> ordinalNumberColumn;
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
    private TableView<RoomDisplay> room;

    private ArrayList<RoomDisplay> customerRoom;

    private ArrayList<Customer> customerList;

    private String roomID;

    private String customerID;

    public TextField getName() {
        return Name;
    }

    public void setName(TextField name) {
        Name = name;
    }

    public ArrayList<RoomDisplay> getCustomerRoom() {
        return customerRoom;
    }

    public void setCustomerRoom(ArrayList<RoomDisplay> customerRoom) {
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

    public ChiTietCheckOut(String customerID, TextField name, ArrayList<RoomDisplay> customerRoom, ArrayList<Customer> customerList, String roomID) {
        this.customerID = customerID;
        Name = name;
        this.customerRoom = customerRoom;
        this.customerList = customerList;
        this.roomID = roomID;
    }

    public ChiTietCheckOut() {
        this.customerID = "";
        this.customerRoom = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.roomID = "";
        this.Name = new TextField();
    }

    private Connection connect() {
        // SQL connection string
        String url = "jdbc:sqlite:hotel.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
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

    public void displayCustomerList() {
        displayCustomerList = FXCollections.observableArrayList(getCustomerList());
        ordinalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("STT"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Cccd"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerBirthYearColumn.setCellValueFactory(new PropertyValueFactory<>("BirthYear"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("Note"));
        customer.setItems(displayCustomerList);
    }
}

