package app.ie303hotelmanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    @FXML
    void handleLogin(ActionEvent event) throws SQLException, IOException {
        //Get the username and password from the input fields
        String inputUsername = username.getText();
        String inputPassword = password.getText();

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "");

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
            Parent dashboardParent = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene dashboardScene = new Scene(dashboardParent);
            Stage window = (Stage) login.getScene().getWindow();
            window.setScene(dashboardScene);
            window.show();
        } else {
            // If there is no match, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Đăng nhập thất bại");
            alert.setHeaderText("Sai tên đăng nhập hoặc mật khẩu");
            alert.setContentText("Vui lòng thử lại");
            alert.showAndWait();
       }

        // Close the database connection
        conn.close();
    }

}
