package app.ie303hotelmanagement;

import effects.ButtonAnimation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ChangePasswordController {
    @FXML private PasswordField oldPassword;
    @FXML private PasswordField newPassword;
    @FXML private PasswordField retypePassword;
    @FXML private Button saveButton;

    @FXML public void initialize() {
        ButtonAnimation.addHoverEffect(saveButton);
    }
    @FXML public void handleSaveButton() {
        if (oldPassword.getText().equals("") || newPassword.getText().equals("") || retypePassword.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
            return;
        }
        if (!newPassword.getText().equals(retypePassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Mật khẩu mới không khớp");
            alert.showAndWait();
            return;
        }
        try {
            Connection conn = DriverManager.getConnection(DataConnector.getDatabaseUrl(), DataConnector.getUsername(), DataConnector.getPassword());
            PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM account WHERE Employee_ID = '" + EmployeeSingleton.getInstance().getEmployeeID() + "'");
            stmt1.executeQuery();
            if (stmt1.getResultSet().next()) {
                if (!stmt1.getResultSet().getString("account_password").equals(oldPassword.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText("Mật khẩu cũ không đúng");
                    alert.showAndWait();
                    return;
                }
            }
            PreparedStatement stmt2 = conn.prepareStatement("UPDATE account SET Password = ? WHERE Employee_ID = '" + EmployeeSingleton.getInstance().getEmployeeID() + "'");
            stmt2.setString(1, newPassword.getText());
            stmt2.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Đổi mật khẩu thành công");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
