package app.ie303hotelmanagement;


import effects.ButtonAnimation;
import effects.TextFieldAnimation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;


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
    private ImageView avatar;
    @FXML private Button changePasswordButton;

    private String employeeName;

    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();
    private String employeeID  = EmployeeSingleton.getInstance().getEmployeeID(); // đây là biến để lưu lại employeeID khi chuyển qua lại giữa các trang
    // đây là hàm để lấy employeeID từ trang login

    @FXML
    void initialize() throws SQLException {
        ButtonAnimation.addHoverEffect(changePasswordButton);
        //test
        System.out.println("Singleton employeeID: " + EmployeeSingleton.getInstance().getEmployeeID());
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
        //set effects
        /*String fullName = nameId1.getText();
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[nameParts.length - 1];
        navDashboardButton.setText("Xin chào, " + firstName);
        ButtonAnimation.addTextAnimation(navDashboardButton, navDashboardButton.getText());*/

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
    @FXML public void handleChangePassword(){
        //switch to change password scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Đổi mật khẩu");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}