package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class EmployeeInfoController {

    @FXML
    private Button reportBtn;
    @FXML
    private ImageView image;
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
    private TextField cccd;
    @FXML
    private TextField position;
    @FXML
    private TextField salary;
    @FXML
    private TextField beginDate;
    @FXML
    private TextField status;
    @FXML
    private Label lblName;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSave;
    @FXML
    private Button checkInButton;
    @FXML
    private Button LogoutButton;

    public void initData(NhanVien nhanVien) {
        employeeID.setText(nhanVien.getMaNhanVien());
        name.setText(nhanVien.getTenNhanVien());
        dateOfBirth.setText(nhanVien.getNgaySinh().toString());
        gender.setText(nhanVien.getGioiTinh());
        address.setText(nhanVien.getDiaChi());
        phone.setText(nhanVien.getSoDienThoai());
        email.setText(nhanVien.getEmail());
        cccd.setText(nhanVien.getCCCD());
        position.setText(nhanVien.getChucVu());
        salary.setText(String.valueOf(nhanVien.getLuong()));
        beginDate.setText(nhanVien.getNgayVaoLam().toString());
        status.setText(nhanVien.getTrangThai());
        lblName.setText(nhanVien.getTenNhanVien());
    }

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
    void handleCheckInButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Checkin.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) checkInButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handlReportBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Report.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) reportBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleBtnBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleBtnSave(ActionEvent event) throws ParseException, SQLException {
        // retrieve the updated information from the text fields
        String id = employeeID.getText();
        String updatedName = name.getText();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date updatedDateOfBirth = new java.sql.Date(formatter.parse(dateOfBirth.getText()).getTime());
        String updatedGender = gender.getText();
        String updatedAddress = address.getText();
        String updatedPhone = phone.getText();
        String updatedEmail = email.getText();
        String updatedCCCD = cccd.getText();
        String updatedPosition = position.getText();
        double updatedSalary = Double.parseDouble(salary.getText());
        java.sql.Date updatedBeginDate = new java.sql.Date(formatter.parse(beginDate.getText()).getTime());
        String updatedStatus = status.getText();

        NhanVien updatedNhanVien = new NhanVien(id, updatedName, updatedDateOfBirth, updatedGender, updatedAddress, updatedPhone, updatedEmail, updatedCCCD, updatedPosition, updatedSalary, updatedBeginDate, updatedStatus);

        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");
        // Check if the employee ID already exists in the database
        String checkQuery = "SELECT * FROM employee WHERE Employee_ID=?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, id);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next()) {
            // If the employee ID already exists, update the existing row
            String query = "UPDATE employee SET Employee_Name=?, Employee_DateofBirth=?, Employee_Gender=?, Employee_Address=?, Employee_Phone=?, Employee_Email=?, Employee_CCCD=?, Employee_Position=?, Employee_Salary=?, Employee_BeginDate=?, Employee_Status=? WHERE Employee_ID=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, updatedNhanVien.getTenNhanVien());
            stmt.setDate(2, updatedNhanVien.getNgaySinh());
            stmt.setString(3, updatedNhanVien.getGioiTinh());
            stmt.setString(4, updatedNhanVien.getDiaChi());
            stmt.setString(5, updatedNhanVien.getSoDienThoai());
            stmt.setString(6, updatedNhanVien.getEmail());
            stmt.setString(7, updatedNhanVien.getCCCD());
            stmt.setString(8, updatedNhanVien.getChucVu());
            stmt.setDouble(9, updatedNhanVien.getLuong());
            stmt.setDate(10, updatedNhanVien.getNgayVaoLam());
            stmt.setString(11, updatedNhanVien.getTrangThai());
            stmt.setString(12, updatedNhanVien.getMaNhanVien());
            stmt.executeUpdate();
            stmt.close();
        } else {
            // If the employee ID does not exist, create a new row
            String query = "INSERT INTO employee (Employee_ID, Employee_Name, Employee_DateofBirth, Employee_Gender, Employee_Address, Employee_Phone, Employee_Email, Employee_CCCD, Employee_Position, Employee_Salary, Employee_BeginDate, Employee_Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, updatedNhanVien.getMaNhanVien());
            stmt.setString(2, updatedNhanVien.getTenNhanVien());
            stmt.setDate(3, updatedNhanVien.getNgaySinh());
            stmt.setString(4, updatedNhanVien.getGioiTinh());
            stmt.setString(5, updatedNhanVien.getDiaChi());
            stmt.setString(6, updatedNhanVien.getSoDienThoai());
            stmt.setString(7, updatedNhanVien.getEmail());
            stmt.setString(8, updatedNhanVien.getCCCD());
            stmt.setString(9, updatedNhanVien.getChucVu());
            stmt.setDouble(10, updatedNhanVien.getLuong());
            stmt.setDate(11, updatedNhanVien.getNgayVaoLam());
            stmt.setString(12, updatedNhanVien.getTrangThai());
            stmt.executeUpdate();
            stmt.close();
        }
        rs.close();
        checkStmt.close();
    }
}
