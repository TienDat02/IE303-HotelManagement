package app.ie303hotelmanagement;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;
    private static String employeeID;
    public static String getEmployeeID() {
        return employeeID;
    }
    @FXML
    void handleLogin(ActionEvent event) throws SQLException, IOException {
        //Get the username and password from the input fields
        String inputUsername = username.getText();
        String inputPassword = password.getText();

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "tiendat1102");

        // Prepare the SQL statement to retrieve the account with the given username and password
        String sql = "SELECT * FROM account WHERE account_name = ? AND account_password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, inputUsername);
        stmt.setString(2, inputPassword);

        // Execute the SQL statement and get the result set
        ResultSet rs = stmt.executeQuery();

        //Check if the result set has any rows
        if (rs.next()) {
            // If there is a match, direct to the DASHBOARD scene*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent dashboardParent = loader.load();
            Scene dashboardScene = new Scene(dashboardParent);
            Stage window = (Stage) login.getScene().getWindow();
            window.setScene(dashboardScene);
            window.show();
            String sql1 = "SELECT Employee_ID FROM account WHERE account_name = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, inputUsername);
            ResultSet rs1 = stmt1.executeQuery();
            String employeeID = null;
            if (rs1.next()) {
                employeeID = rs1.getString("Employee_ID");
                DashboardController dashboardController = loader.getController();
                dashboardController.initialize(employeeID);
            }
        } else {
            // If there is no match, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText("Sai tên đăng nhập hoặc mật khẩu");
            alert.setContentText("Vui lòng thử lại");
            alert.showAndWait();
        }
        conn.close();
        // Close the database connection

    }


}
