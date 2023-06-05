package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class EmployeeInfoController {

    @FXML
    private Button navServiceButton;
    @FXML
    private Button navRoomButton;
    @FXML
    private Button navDashboardButton;
    @FXML
    private Button navCheckinButton;
    @FXML
    private Button navEmployeeButton;
    @FXML
    private Button navCheckoutButton;
    @FXML
    private Button navCustomerButton;
    @FXML
    private Button navReportButton;
    @FXML
    private ImageView imgNhanVien;
    @FXML
    private TextField employeeIDD;
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
    private Button LogoutButton;
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID();
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
    public void initData(NhanVien nhanVien) {
        employeeIDD.setText(nhanVien.getMaNhanVien());
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
        //set avatar
        String imageName = employeeIDD + ".png";
        File imageFile = new File("src/main/resources/images/" + imageName);
        if (!imageFile.exists()) {
            imageName = "default.png"; // assuming the default image's name is default.png
            imageFile = new File("src/main/resources/images/" + imageName);
        }
        Image image = new Image(imageFile.toURI().toString());
        imgNhanVien.setImage(image);
        Rectangle clip = new Rectangle(imgNhanVien.getFitWidth(), imgNhanVien.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imgNhanVien.setClip(clip);
    }

    @FXML
    void handleBtnBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleBtnSave(ActionEvent event) throws ParseException, SQLException {
        // retrieve the updated information from the text fields
        String id = employeeIDD.getText();
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

        if (id.equals("") || id == null || updatedName.equals("") || updatedName == null || dateOfBirth.getText().equals("") || dateOfBirth.getText() == null || updatedGender.equals("") || updatedGender == null || updatedAddress.equals("") || updatedAddress == null || updatedPhone.equals("") || updatedPhone == null || updatedEmail.equals("") || updatedEmail == null || updatedCCCD.equals("") || updatedCCCD == null || updatedPosition.equals("") || updatedPosition == null || salary.getText().equals("") || salary.getText() == null || beginDate.getText().equals("") || beginDate.getText() == null || updatedStatus.equals("") || updatedStatus == null) {
            // If any field is empty or null, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lỗi");
            alert.setHeaderText("Vui lòng nhập đầy đủ thông tin");
            alert.showAndWait();
            return;
        }

        NhanVien updatedNhanVien = new NhanVien(id, updatedName, updatedDateOfBirth, updatedGender, updatedAddress, updatedPhone, updatedEmail, updatedCCCD, updatedPosition, updatedSalary, updatedBeginDate, updatedStatus);

        Connection conn = DriverManager.getConnection(connectUrl, username, password);
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