package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class DashboardController {
    @FXML
    private Button LogoutButton;
    @FXML
    private Text nameId1;
    @FXML
    private TextField employeeID;
    @FXML
    private TextField name;
    @FXML
    private TextField dateOfBirth;
    @FXML
    private TextField gender;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField position;
    @FXML
    private TextField cccd;
    @FXML
    private Button navDashboardButton;
    @FXML
    private Button navCheckInButton;
    @FXML
    private Button navEmployeeButton;
    @FXML
    private Button navCheckoutButton;

    private String employeeID1; // đây là biến để lưu lại employeeID khi chuyển qua lại giữa các trang
    // đây là hàm để lấy employeeID từ trang login
    void setEmployeeID(String employeeID1) throws SQLException {
        this.employeeID1 = employeeID1;
        System.out.println("employeeID = " + employeeID);
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "tiendat1102");
        String sql2 = "SELECT * FROM employee WHERE Employee_ID= ?";
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        stmt2.setString(1, employeeID1);
        ResultSet rs2 = stmt2.executeQuery();
        if (rs2.next()) {
            nameId1.setText(rs2.getString("Employee_Name"));
            employeeID.setText(rs2.getString("Employee_ID"));
            name.setText(rs2.getString("Employee_Name"));
            dateOfBirth.setText(rs2.getDate("Employee_DateofBirth").toString());
            gender.setText(rs2.getString("Employee_Gender"));
            address.setText(rs2.getString("Employee_Address"));
            phone.setText(rs2.getString("Employee_Phone"));
            email.setText(rs2.getString("Employee_Email"));
            position.setText(rs2.getString("Employee_Position"));
            cccd.setText(rs2.getString("Employee_CCCD"));
            employeeID1 = rs2.getString("Employee_ID");
        }
        navDashboardButton.setText("Xin chào, " + nameId1.getText());
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

    @FXML// đây là hàm để chuyển qua trang Checkin
    void handleCheckinButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent dashboardParent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID1);
        System.out.println("employeeID in DashboardController: " + employeeID1); // Add this line
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckInButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavCheckoutButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check-out.fxml"));
        Parent dashboardParent = loader.load();
        CheckOutController checkOut = loader.getController();
        checkOut.setEmployeeID(employeeID1);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckoutButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

}