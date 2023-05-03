package app.ie303hotelmanagement;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

// Define CustomerController class
public class CustomerController {
    @FXML
    private TextField inputCCCD;
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    private TextField inputNote;
    @FXML
    private TextField inputBirthYear;
    @FXML
    private TextField findingCustomer;
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button checkInButton;
    @FXML
    private Button alterCustomerButton;
    @FXML
    private Button deleteCustomerButton;
    @FXML
    private Button CustomerButton;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button ServiceButton;
    @FXML
    private Button RoomButton;
    @FXML
    private Button ReportButton;
    @FXML
    private Button EmployeeButton;
    @FXML
    private Button CheckOutButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Long> sttColumn;
    @FXML
    private TableColumn<Customer, Integer> birthYearColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> cccdColumn;
    @FXML
    private TableColumn<Customer, Long> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, String> noteColumn;
    long count = 1;
    private String databaseUrl = "jdbc:mysql://127.0.0.1:3306/hotelmanagement";
    private String databaseUsername = "ThanhTra0802";
    private String databasePassword = "thanhtra0802";


    public void initialize() {
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM guest");

            while (rs.next()) {
                String guestID = rs.getString("Guest_ID");
                String guestName = rs.getString("Guest_Name");
                int guestBirthYear = rs.getInt("Guest_BirthYear");
                String guestPhone = rs.getString("Guest_Phone");
                String guestNote = rs.getString("Guest_Notes");

                Customer customer = new Customer(count, guestID, guestName, guestBirthYear, guestPhone, guestNote);
                customerTable.getItems().add(customer);
                count++;
            }

            cccdColumn.setCellValueFactory(new PropertyValueFactory<>("cccd"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            birthYearColumn.setCellValueFactory(new PropertyValueFactory<>("birthYear"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));

            sttColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Customer, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Customer, Long> param) {
                    return new ReadOnlyObjectWrapper<>(param.getValue().getSTT());
                }
            });

            customerTable.getItems().addListener((javafx.collections.ListChangeListener.Change<? extends Customer> c) -> {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (int i = 0; i < c.getAddedSize(); i++) {
                            c.getList().get(i).setSTT(count);
                            count++;
                        }
                    }
                }
            });

            customerTable.getColumns().addAll(sttColumn, cccdColumn, nameColumn, birthYearColumn, phoneNumberColumn, noteColumn);
            customerTable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
                    if (selectedCustomer != null) {
                        inputCCCD.setText(selectedCustomer.getCccd());
                        inputName.setText(selectedCustomer.getName());
                        inputBirthYear.setText(String.valueOf(selectedCustomer.getBirthYear()));
                        inputPhoneNumber.setText(selectedCustomer.getPhoneNumber());
                        inputNote.setText(selectedCustomer.getNote());
                    }
                }
            });

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleAddCustomer(ActionEvent event) {
        // Get the input values from the text fields
        String cccd = inputCCCD.getText();
        String name = inputName.getText();
        int birthYear = Integer.parseInt(inputBirthYear.getText());
        String phoneNumber = inputPhoneNumber.getText();
        String note = inputNote.getText();

        // Check if the customer already exists in the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, cccd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // If the customer already exists, show an alert to the user and return
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Khách hàng đã tồn tại");
                alert.setContentText("Căn cước công dân đã tồn tại trong cơ sở dữ liệu. Vui lòng kiểm tra lại.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            // If there's an error with the database connection, show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Database error");
            alert.setContentText("Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra lại.");
            alert.showAndWait();
            return;
        }

        // If the customer doesn't exist, insert the new customer into the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO guest (Guest_ID, Guest_Name, Guest_BirthYear, Guest_Phone, Guest_Notes) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, cccd);
            stmt.setString(2, name);
            stmt.setInt(3, birthYear);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, note);
            stmt.executeUpdate();

            // Add the new customer to the customerTable
            Customer customer = new Customer(count, cccd, name, birthYear, phoneNumber, note);
            customerTable.getItems().add(customer);


            // Show an alert to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thêm khách hàng thành công");
            alert.setContentText("Đã thêm khách hàng " + name + " vào cơ sở dữ liệu.");
            alert.showAndWait();
        } catch (SQLException e) {
            // If there's an error with the database connection, show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error");
            alert.setContentText("There was an error connecting to the database.");
            alert.showAndWait();
        }
    }
    @FXML
    public void handleDeleteCustomer(ActionEvent event) {
        // Get the selected customer from the customerTable
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        inputName.setText(selectedCustomer.getName());
        inputCCCD.setText(selectedCustomer.getCccd());
        inputPhoneNumber.setText(selectedCustomer.getPhoneNumber());
        inputNote.setText(selectedCustomer.getNote());
        // If no customer is selected, show an alert to the user and return
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No customer selected");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
            return;
        }

        // Create a connection to the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            // Create a PreparedStatement to delete the selected guest from the guest table
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, selectedCustomer.getCccd());

            // Execute the update
            stmt.executeUpdate();

            // Remove the selected customer from the customerTable
            customerTable.getItems().remove(selectedCustomer);

            // Reset the STT column
            count = 1;
            for (Customer customer : customerTable.getItems()) {
                customer.setSTT(count);
                count++;
            }
            // Show an alert to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Xóa khách hàng thành công");
            alert.setContentText("Đã xóa khách hàng " + selectedCustomer.getName() + " khỏi cơ sở dữ liệu.");
            alert.showAndWait();
        } catch (SQLException e) {
            // If there's an error with the database connection, show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database error");
            alert.setContentText("There was an error connecting to the database.");
            alert.showAndWait();
        }
    }
    @FXML
    public void handleUpdateCustomer(ActionEvent event) {
        // Get the input values from the text fields
        String cccd = inputCCCD.getText();
        String name = inputName.getText();
        int birthYear = Integer.parseInt(inputBirthYear.getText());
        String phoneNumber = inputPhoneNumber.getText();
        String note = inputNote.getText();

        // Get the selected customer from the customerTable
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        // Check if the customer already exists in the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, cccd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // If the customer already exists, update their information in the database
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE guest SET Guest_Name = ?, Guest_BirthYear = ?, Guest_Phone = ?, Guest_Notes = ? WHERE Guest_ID = ?");
                updateStmt.setString(1, name);
                updateStmt.setInt(2, birthYear);
                updateStmt.setString(3, phoneNumber);
                updateStmt.setString(4, note);
                updateStmt.setString(5, cccd);
                updateStmt.executeUpdate();

                // Update the customer in the customerTable
                Customer updatedCustomer = new Customer(selectedCustomer.getSTT(), cccd, name, birthYear, phoneNumber, note);
                customerTable.getItems().set(customerTable.getSelectionModel().getSelectedIndex(), updatedCustomer);

                // Show an alert to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Cập nhật thông tin khách hàng thành công");
                alert.setContentText("Đã cập nhật thông tin khách hàng " + name + " trong cơ sở dữ liệu.");
                alert.showAndWait();
            } else {
                // If the customer doesn't exist, show an alert to the user
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Khách hàng không tồn tại");
                alert.setContentText("Căn cước công dân không tồn tại trong cơ sở dữ liệu. Vui lòng kiểm tra lại.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            // If there's an error with the database connection, show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Database error");
            alert.setContentText("Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra lại.");
            alert.showAndWait();
        }
    }
    @FXML
    public void filterSearch() {
        // Get the user's input from the findingCustomer TextField
        String query = findingCustomer.getText().toLowerCase();

        // Create a filtered list to hold the matching customers
        FilteredList<Customer> filteredList = new FilteredList<>(customerTable.getItems());

        // Set the predicate for the filtered list to match the user's input
        filteredList.setPredicate(customer -> {
            // Check if the customer's name, cccd, phone number, or note contains the user's input
            return customer.getName().toLowerCase().contains(query)
                    || customer.getCccd().toLowerCase().contains(query)
                    || customer.getPhoneNumber().toLowerCase().contains(query);
        });

        // Set the customerTable's items to the filtered list
        customerTable.setItems(filteredList);
    }
    @FXML
    public void handleReloadScene(ActionEvent event) {
        try {
            // Load the Customer.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) customerTable.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleCheckInButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckIn.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) checkInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCheckOutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CheckOut.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) CheckOutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleRoomButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Room.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) RoomButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleServiceButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Service.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ServiceButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleCustomerButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) CustomerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleEmployeeButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) EmployeeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleReportButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Statistic.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ReportButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void handleLogoutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}