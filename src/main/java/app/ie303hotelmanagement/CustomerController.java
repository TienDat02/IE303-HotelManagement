package app.ie303hotelmanagement;

import effects.ButtonAnimation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;

// Define CustomerController class
public class CustomerController {
    @FXML private TextField inputCCCD;
    @FXML private TextField inputName;
    @FXML private TextField inputPhoneNumber;
    @FXML private TextField inputNote;
    @FXML private TextField inputBirthYear;
    @FXML private ComboBox<String> inputTier;
    @FXML private TextField inputNumberOfCheckin;
    @FXML private TextField findingCustomer;
    @FXML private Button addCustomerButton;
    @FXML private Button alterCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button policyChangeButton;
    @FXML private Button updateTierButton;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Long> sttColumn;
    @FXML private TableColumn<Customer, Integer> birthYearColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> cccdColumn;
    @FXML private TableColumn<Customer, Long> phoneNumberColumn;
    @FXML private TableColumn<Customer, String> noteColumn;
    @FXML private TableColumn<Customer, String> tierColumn;
    @FXML private TableColumn<Customer, Integer> numberOfCheckinColumn;

    long count = 1;
    private String databaseUrl = DataConnector.getDatabaseUrl() ;
    private String databaseUsername = DataConnector.getUsername();
    private String databasePassword = DataConnector.getPassword();
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();

    @FXML
    public void initialize() {

        ObservableList<String> tierList = FXCollections.observableArrayList();
        tierList.addAll("Mới", "Bạc", "Vàng", "Kim Cương");
        inputTier.setItems(tierList);
        System.out.println("diamondPercent: " + TiersSingleton.getInstance().getDiamondDiscount());
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
                String guestTier = rs.getString("Guest_Tier");
                int numberOfCheckin = rs.getInt("Number_Of_Checkin");
                Customer customer = new Customer(count, guestID, guestName, numberOfCheckin, guestTier, guestBirthYear, guestPhone, guestNote);
                customerTable.getItems().add(customer);
                count++;
            }

            cccdColumn.setCellValueFactory(new PropertyValueFactory<>("cccd"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            birthYearColumn.setCellValueFactory(new PropertyValueFactory<>("birthYear"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
            tierColumn.setCellValueFactory(new PropertyValueFactory<>("tier"));
            numberOfCheckinColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfCheckin"));

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
            customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    inputCCCD.setText(newSelection.getCccd());
                    inputName.setText(newSelection.getName());
                    inputBirthYear.setText(String.valueOf(newSelection.getBirthYear()));
                    inputPhoneNumber.setText(newSelection.getPhoneNumber());
                    inputNote.setText(newSelection.getNote());
                    inputTier.setValue(newSelection.getTier());
                    inputNumberOfCheckin.setText(String.valueOf(newSelection.getNumberOfCheckin()));
                }
            });
            conn.close();
            ButtonAnimation.addHoverEffect(addCustomerButton);
            ButtonAnimation.addHoverEffect(alterCustomerButton);
            ButtonAnimation.addHoverEffect(deleteCustomerButton);
            ButtonAnimation.addHoverEffect(updateTierButton);
            ButtonAnimation.addHoverEffect(policyChangeButton);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML public void handleCustomerUpdateTier(){
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            PreparedStatement stmt = conn.prepareStatement("Select * from guest");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String guestID = rs.getString("Guest_ID");
                PreparedStatement getBill = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                getBill.setString(1, guestID);
                getBill.setInt(2, TiersSingleton.getInstance().getDiamondNearestCheckouts());
                ResultSet billResult = getBill.executeQuery();
                if (billResult.next() && billResult.getFloat("Total") >= TiersSingleton.getInstance().getDiamondNearestCheckoutValue()) {
                    PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Kim cương' WHERE Guest_ID = ?");
                    stmt6.setString(1, guestID);
                    stmt6.executeUpdate();
                } else {
                    PreparedStatement getBill2 = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                    getBill2.setString(1, guestID);
                    getBill2.setInt(2, TiersSingleton.getInstance().getGoldNearestCheckouts());
                    ResultSet billResult2 = getBill2.executeQuery();
                    if (billResult2.next() && billResult2.getFloat("Total") >= TiersSingleton.getInstance().getGoldNearestCheckoutValue()) {
                        PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Vàng' WHERE Guest_ID = ?");
                        stmt6.setString(1, guestID);
                        stmt6.executeUpdate();
                    } else {
                        PreparedStatement getBill3 = conn.prepareStatement("SELECT sum(Total_Cost) as Total FROM bill where Guest_ID = ? order by Bill_Date LIMIT ?");
                        getBill3.setString(1, guestID);
                        getBill3.setInt(2, TiersSingleton.getInstance().getSilverNearestCheckouts());
                        ResultSet billResult3 = getBill3.executeQuery();
                        if (billResult3.next() && billResult3.getFloat("Total") >= TiersSingleton.getInstance().getSilverNearestCheckoutValue()) {
                            PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Bạc' WHERE Guest_ID = ?");
                            stmt6.setString(1, guestID);
                            stmt6.executeUpdate();
                        } else {
                            PreparedStatement stmt6 = conn.prepareStatement("UPDATE guest SET Guest_Tier = 'Mới' WHERE Guest_ID = ?");
                            stmt6.setString(1, guestID);
                            stmt6.executeUpdate();
                        }
                        getBill3.close();
                    }
                }
                getBill.close();
            }
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
        String tier = inputTier.getValue();
        int numberOfCheckin = Integer.parseInt(inputNumberOfCheckin.getText());

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
            alert.setHeaderText("Database connection error");
            alert.setContentText("Không thể kết nối đến cơ sở dữ liệu. Vui lòng kiểm tra lại.");
            alert.showAndWait();
            return;
        }

        // If the customer doesn't exist, insert the new customer into the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO guest (Guest_ID, Guest_Name, Number_Of_Checkin, Guest_Tier, Guest_BirthYear, Guest_Phone, Guest_Notes) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, cccd);
            stmt.setString(2, name);
            stmt.setInt(3, numberOfCheckin);
            stmt.setString(4, tier);
            stmt.setInt(5, birthYear);
            stmt.setString(6, phoneNumber);
            stmt.setString(7, note);
            stmt.executeUpdate();

            // Add the new customer to the customerTable
            Customer customer = new Customer(count, cccd, name,numberOfCheckin, tier, birthYear, phoneNumber, note);
            customerTable.getItems().add(customer);


            // Show an alert to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thêm khách hàng thành công");
            alert.setContentText("Đã thêm khách hàng " + name + " vào cơ sở dữ liệu.");
            alert.showAndWait();
            Log addCustomerLog = new Log(LocalDateTime.now(), "Thêm khách hàng", "Thành công", "Thêm khách hàng có CCCD " + cccd + " vào cơ sở dữ liệu.");
            addCustomerLog.insertLog();
        } catch (SQLException e) {
            // If there's an error with the database connection, show an alert to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Database error");
            alert.setContentText("Không thể thêm khách hàng vào cơ sở dữ liệu. Vui lòng kiểm tra lại.");
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
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Không có khách hàng được chọn");
            alert.setContentText("Hãy chọn khách hàng cần xóa.");
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
            Log deleteCustomerLog = new Log(LocalDateTime.now(), "Xóa khách hàng", "Thành công", "Thêm khách hàng có CCCD " + selectedCustomer.getCccd() + " vào cơ sở dữ liệu.");
            deleteCustomerLog.insertLog();
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
    public void handleUpdateCustomer(ActionEvent event) {
        // Get the input values from the text fields
        String cccd = inputCCCD.getText();
        String name = inputName.getText();
        int birthYear = Integer.parseInt(inputBirthYear.getText());
        String phoneNumber = inputPhoneNumber.getText();
        String note = inputNote.getText();
        int numberOfCheckin = Integer.parseInt(inputNumberOfCheckin.getText());
        String tier = inputTier.getValue().toString();

        // Get the selected customer from the customerTable
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        // Check if the customer already exists in the database
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, cccd);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // If the customer already exists, update their information in the database
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE guest SET Guest_Name = ?, Number_Of_Checkin = ?, Guest_Tier = ?, Guest_BirthYear = ?, Guest_Phone = ?, Guest_Notes = ? WHERE Guest_ID = ?");
                updateStmt.setString(1, name);
                updateStmt.setInt(2, numberOfCheckin);
                updateStmt.setString(3, tier);
                updateStmt.setInt(4, birthYear);
                updateStmt.setString(5, phoneNumber);
                updateStmt.setString(6, note);
                updateStmt.setString(7, cccd);
                updateStmt.executeUpdate();

                // Update the customer in the customerTable
                Customer updatedCustomer = new Customer(selectedCustomer.getSTT(), cccd, name, numberOfCheckin, tier, birthYear, phoneNumber, note);
                customerTable.getItems().set(customerTable.getSelectionModel().getSelectedIndex(), updatedCustomer);

                // Show an alert to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Cập nhật thông tin khách hàng thành công");
                alert.setContentText("Đã cập nhật thông tin khách hàng " + name + " trong cơ sở dữ liệu.");
                alert.showAndWait();
                Log updateCustomerLog = new Log(LocalDateTime.now(), "Sửa khách hàng", "Thành công", "Sửa thông khách hàng có CCCD " + cccd + " trong cơ sở dữ liệu.");
                updateCustomerLog.insertLog();
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
    public void getPolicyChange(){
        //open ChangePolicy.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePolicy.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Thay đổi chính sách");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}