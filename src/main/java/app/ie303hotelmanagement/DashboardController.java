package app.ie303hotelmanagement;


import animation.ButtonAnimation;
import animation.TextFieldAnimation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Optional;


public class DashboardController {
    @FXML
    private Button LogoutButton;
    @FXML
    private Text nameId1;
    @FXML
    private TextField employeeIDTxt;
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
    private ImageView avatar;
    private String employeeName;

    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
    private String employeeID; // đây là biến để lưu lại employeeID khi chuyển qua lại giữa các trang
    // đây là hàm để lấy employeeID từ trang login
    void initialize(String employeeID) throws SQLException {
        this.employeeID = employeeID;
        System.out.println("employeeID = " + employeeID);
        Connection conn = DriverManager.getConnection(databaseUrl, username, password);
        String sql2 = "SELECT * FROM employee WHERE Employee_ID= ?";
        PreparedStatement stmt2 = conn.prepareStatement(sql2);
        stmt2.setString(1, employeeID);
        ResultSet rs2 = stmt2.executeQuery();
        if (rs2.next()) {
            nameId1.setText(rs2.getString("Employee_Name"));
            employeeIDTxt.setText(rs2.getString("Employee_ID"));
            name.setText(rs2.getString("Employee_Name"));
            dateOfBirth.setText(rs2.getDate("Employee_DateofBirth").toString());
            gender.setText(rs2.getString("Employee_Gender"));
            address.setText(rs2.getString("Employee_Address"));
            phone.setText(rs2.getString("Employee_Phone"));
            email.setText(rs2.getString("Employee_Email"));
            position.setText(rs2.getString("Employee_Position"));
            cccd.setText(rs2.getString("Employee_CCCD"));


        }
        //set avatar
        String imageName = employeeID + ".png";
        File imageFile = new File("src/main/resources/images/" + imageName);
        if (!imageFile.exists()) {
            imageName = "default.png"; // assuming the default image's name is default.png
            imageFile = new File("src/main/resources/images/" + imageName);
        }
        Image image = new Image(imageFile.toURI().toString());
        avatar.setImage(image);
        Rectangle clip = new Rectangle(avatar.getFitWidth(), avatar.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        avatar.setClip(clip);
        //set animation
        String fullName = nameId1.getText();
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[nameParts.length - 1];
        navDashboardButton.setText("Xin chào, " + firstName);
        ButtonAnimation.addTextAnimation(navDashboardButton, navDashboardButton.getText());

        TextFieldAnimation.animateTextField(employeeIDTxt, employeeIDTxt.getText());
        TextFieldAnimation.animateTextField(name, name.getText());
        TextFieldAnimation.animateTextField(dateOfBirth, dateOfBirth.getText());
        TextFieldAnimation.animateTextField(gender, gender.getText());
        TextFieldAnimation.animateTextField(address, address.getText());
        TextFieldAnimation.animateTextField(phone, phone.getText());
        TextFieldAnimation.animateTextField(email, email.getText());
        TextFieldAnimation.animateTextField(position, position.getText());
        TextFieldAnimation.animateTextField(cccd, cccd.getText());
    }

    //Navigation
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
    @FXML
    public void handleNavCustomerButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent dashboardParent = loader.load();
        CustomerController customerController = loader.getController();
        customerController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCustomerButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavDashboardButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent dashboardParent = loader.load();
        DashboardController dashboardController = loader.getController();
        dashboardController.initialize(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navDashboardButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavServiceButton(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Service.fxml"));
        Parent dashboardParent = loader.load();
        ServiceController serviceController = loader.getController();
        serviceController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navServiceButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavRoomButton(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        Parent dashboardParent = loader.load();
        RoomController roomController = loader.getController();
        roomController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navRoomButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML// đây là hàm để chuyển qua trang Checkin
    public void handleNavCheckinButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent dashboardParent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckinButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

    @FXML
    public void handleNavCheckoutButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check-out.fxml"));
        Parent dashboardParent = loader.load();
        CheckOutController checkOut = loader.getController();
        checkOut.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckoutButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavEmployeeButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeePage.fxml"));
        Parent dashboardParent = loader.load();
        QLNVController qlnvController = loader.getController();
        qlnvController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckoutButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavReportButton(ActionEvent event) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent dashboardParent = loader.load();
        ReportController reportController = loader.getController();
        reportController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navReportButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }

}