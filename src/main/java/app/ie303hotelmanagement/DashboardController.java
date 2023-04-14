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
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Optional;

public class DashboardController {
    @FXML
    private Button LogoutButton;

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
    private Button helloButton;
    @FXML
    private Button checkInButton;

    @FXML
    void handleLogoutButton(ActionEvent event) throws IOException {
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
    @FXML
    void initialize(NhanVien nhanVien) {
        helloButton.setText("Xin chào, " + nhanVien.getTenNhanVien());
        employeeID.setText(nhanVien.getMaNhanVien());
        name.setText(nhanVien.getTenNhanVien());
        dateOfBirth.setText(nhanVien.getNgaySinh().toString());
        gender.setText(nhanVien.getGioiTinh());
        address.setText(nhanVien.getDiaChi());
        phone.setText(nhanVien.getSoDienThoai());
        email.setText(nhanVien.getEmail());
        position.setText(nhanVien.getChucVu());
        cccd.setText(nhanVien.getCCCD());
    }
    @FXML
    void handleCheckInButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Checkin.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) checkInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


