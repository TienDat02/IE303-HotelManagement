package app.ie303hotelmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import effects.NavbarAnimations;
public class NavbarController {
    @FXML private Button navDashboardButton;
    @FXML private Button navCheckinButton;
    @FXML private Button navServiceButton;
    @FXML private Button navRoomButton;
    @FXML private Button navEmployeeButton;
    @FXML private Button LogoutButton;
    @FXML private Button navCheckoutButton;
    @FXML private Button navCustomerButton;
    @FXML private Button navReportButton;

    public void setEmployeeID(String employeeID) {
        EmployeeSingleton.getInstance().setEmployeeID(employeeID);
        System.out.println("NavbarController: " + employeeID);
    }

    @FXML
    public void initialize() {
        if (EmployeeSingleton.getInstance().getIsManager() == false) {
            navEmployeeButton.setVisible(false);
            navReportButton.setVisible(false);
            navRoomButton.setVisible(false);
        }
        NavbarAnimations navbarAnimations = new NavbarAnimations();
        switch (ChosenSceneSingleton.getInstance().getChosenScene()) {
            case "Checkin":
                navbarAnimations.ChosenButtonEffect(navCheckinButton);
                break;
            case "Service":
                navbarAnimations.ChosenButtonEffect(navServiceButton);
                break;
            case "Room":
                navbarAnimations.ChosenButtonEffect(navRoomButton);
                break;
            case "Employee":
                navbarAnimations.ChosenButtonEffect(navEmployeeButton);
                break;
            case "Checkout":
                navbarAnimations.ChosenButtonEffect(navCheckoutButton);
                break;
            case "Customer":
                navbarAnimations.ChosenButtonEffect(navCustomerButton);
                break;
            case "Report":
                navbarAnimations.ChosenButtonEffect(navReportButton);
                break;
        };
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Checkin")) {
            navbarAnimations.addHoverEffect(navCheckinButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Service")) {
            navbarAnimations.addHoverEffect(navServiceButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Room")) {
            navbarAnimations.addHoverEffect(navRoomButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Employee")) {
            navbarAnimations.addHoverEffect(navEmployeeButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Checkout")) {
            navbarAnimations.addHoverEffect(navCheckoutButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Customer")) {
            navbarAnimations.addHoverEffect(navCustomerButton);
        }
        if (!ChosenSceneSingleton.getInstance().getChosenScene().equals("Report")) {
            navbarAnimations.addHoverEffect(navReportButton);
        }
    }
    @FXML
    public void handleNavDashboardButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent parent = loader.load();
        OverviewController overviewController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navDashboardButton.getScene().getWindow();
        ChosenSceneSingleton.getInstance().setChosenScene("Dashboard");
        //dashboardController.initialize(EmployeeSingleton.getInstance().getEmployeeID());
        window.setScene(scene);
    }

    @FXML
    public void handleNavCheckinButton(ActionEvent event) throws IOException {
        ChosenSceneSingleton.getInstance().setChosenScene("Checkin");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent parent = loader.load();
        CheckinController checkinController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navCheckinButton.getScene().getWindow();
        window.setScene(scene);
    }

    @FXML
    public void handleNavServiceButton(ActionEvent event) throws IOException{
        ChosenSceneSingleton.getInstance().setChosenScene("Service");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Service.fxml"));
        Parent parent = loader.load();
        ServiceController serviceController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navServiceButton.getScene().getWindow();
        window.setScene(scene);
    }

    @FXML
    public void handleNavRoomButton(ActionEvent event) throws IOException{
        ChosenSceneSingleton.getInstance().setChosenScene("Room");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
        Parent parent = loader.load();
        RoomController roomController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navRoomButton.getScene().getWindow();
        window.setScene(scene);
    }

    @FXML
    public void handleNavEmployeeButton(ActionEvent event) throws IOException{
        ChosenSceneSingleton.getInstance().setChosenScene("Employee");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Employee.fxml"));
        Parent parent = loader.load();
        EmployeeController employeeController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navEmployeeButton.getScene().getWindow();
        window.setScene(scene);
    }

    @FXML
    public void handleNavCheckoutButton(ActionEvent event) throws IOException{
        ChosenSceneSingleton.getInstance().setChosenScene("Checkout");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
        Parent parent = loader.load();
        CheckOutController checkoutController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navCheckoutButton.getScene().getWindow();
        window.setScene(scene);
    }

    @FXML
    public void handleNavCustomerButton(ActionEvent event) throws IOException {
        ChosenSceneSingleton.getInstance().setChosenScene("Customer");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
        Parent parent = loader.load();
        CustomerController customerController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navCustomerButton.getScene().getWindow();
        window.setScene(scene);
    }
    @FXML
    public void handleNavReportButton(ActionEvent event) throws IOException {
        ChosenSceneSingleton.getInstance().setChosenScene("Report");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
        Parent parent = loader.load();
        ReportController reportController = loader.getController();
        Scene scene = new Scene(parent);
        Stage window = (Stage) navReportButton.getScene().getWindow();
        window.setScene(scene);
    }
    @FXML
    public void handleLogoutButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-Page.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage) LogoutButton.getScene().getWindow();
        window.setScene(scene);
    }

}
