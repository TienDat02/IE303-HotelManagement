package app.ie303hotelmanagement;

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

import java.io.IOException;
import java.sql.*;


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
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String connectUsername = DataConnector.getUsername();
    private String connectPassword = DataConnector.getPassword();
    @FXML
    void handleLogin(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        //Get the username and password from the input fields
        String inputUsername = username.getText();
        String inputPassword = password.getText();

        // Connect to the database
        Connection conn = DriverManager.getConnection(connectUrl, connectUsername, connectPassword);

        // Prepare the SQL statement to retrieve the account with the given username and password
        String sql = "SELECT * FROM account WHERE account_name = ? AND account_password = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, inputUsername);
        stmt.setString(2, inputPassword);

        // Execute the SQL statement and get the result set
        ResultSet rs = stmt.executeQuery();

        //Check if the result set has any rows
        /*if (rs.next()) {
            // If there is a match, direct to the DASHBOARD scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Navbar.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) login.getScene().getWindow();
            window.setScene(scene);
            window.show();
            String sql1 = "SELECT Employee_ID FROM account WHERE account_name = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, inputUsername);
            ResultSet rs1 = stmt1.executeQuery();
            String employeeID = null;
            if (rs1.next()) {
                employeeID = rs1.getString("Employee_ID");
                NavbarController navbarController = loader.getController();
                navbarController.setEmployeeID(employeeID);
            } */
        if (rs.next()) {
            // If there is a match, direct to the DASHBOARD scene

            String sql1 = "SELECT Employee_ID FROM account WHERE account_name = ?";
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt1.setString(1, inputUsername);
            ResultSet rs1 = stmt1.executeQuery();
            String employeeID = null;
            if (rs1.next()) {
                employeeID = rs1.getString("Employee_ID");
                EmployeeSingleton employeeSingleton = EmployeeSingleton.getInstance();
                employeeSingleton.setEmployeeID(employeeID);
                System.out.println("Singleton employeeID: " + employeeSingleton.getEmployeeID());
                ChosenSceneSingleton.getInstance().setChosenScene("Dashboard");
                PreparedStatement setStatus = conn.prepareStatement("UPDATE account SET Status = 'Có mặt' WHERE Employee_ID = '" + employeeID + "'");
            }
            PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM tiers");
            ResultSet rs2 = stmt2.executeQuery();
            while (rs2.next()) {
                TiersSingleton tiersSingleton = TiersSingleton.getInstance();
                if (rs2.getString("Tier").equals("Kim cương")) {
                    tiersSingleton.setDiamondDiscount(rs2.getInt("Discount"));
                    tiersSingleton.setDiamondNearestCheckouts(rs2.getInt("Nearest_Checkouts"));
                    tiersSingleton.setDiamondNearestCheckoutValue(rs2.getFloat("Value_Condition"));
                } else if (rs2.getString("Tier").equals("Vàng")) {
                    tiersSingleton.setGoldDiscount(rs2.getInt("Discount"));
                    tiersSingleton.setGoldNearestCheckouts(rs2.getInt("Nearest_Checkouts"));
                    tiersSingleton.setGoldNearestCheckoutValue(rs2.getFloat("Value_Condition"));
                } else if (rs2.getString("Tier").equals("Bạc")) {
                    tiersSingleton.setSilverDiscount(rs2.getInt("Discount"));
                    tiersSingleton.setSilverNearestCheckouts(rs2.getInt("Nearest_Checkouts"));
                    tiersSingleton.setSilverNearestCheckoutValue(rs2.getFloat("Value_Condition"));
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage window = (Stage) login.getScene().getWindow();
            window.setScene(scene);
            window.show();
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
