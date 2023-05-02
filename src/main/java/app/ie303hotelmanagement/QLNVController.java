package app.ie303hotelmanagement;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;


public class QLNVController {
    @FXML
    private TableView<NhanVien> tblNhanVien;
    @FXML
    private TableColumn<NhanVien, Integer> colSTT;
    @FXML
    private TableColumn<NhanVien, String> colmaNhanVien;
    @FXML
    private TableColumn<NhanVien, String> colTenNhanVien;
    @FXML
    private TableColumn<NhanVien, String> colSoDienThoai;
    @FXML
    private TableColumn<NhanVien, String> colChucVu;
    @FXML
    private TextField txtTimNV;
    @FXML
    private Button btnAddEmployee;
    @FXML
    private Button btnDelEmployee;
    @FXML
    private Button LogoutButton;
    @FXML
    private Button reportBtn;
    @FXML
    private Button navCheckoutButton;
    @FXML
    private Button navCheckinButton;
    @FXML
    private Button navServiceButton;
    @FXML
    private Button navRoomButton;
    @FXML
    private Button navDashboardButton;
    @FXML
    private Button navEmployeeButton;
    @FXML
    private Button navCustomerButton;
    @FXML
    private Button navReportButton;
    private String employeeID;
    private String connectUrl = "jdbc:mysql://127.0.0.1:3306/hotelmanagement";
    private String username = "root";
    private String password = "tiendat1102";

    final ObservableList<NhanVien> custList = FXCollections.observableArrayList();

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public void initialize() throws SQLException {
        colSTT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<NhanVien, Integer> p) {
                return new ReadOnlyObjectWrapper<>(tblNhanVien.getItems().indexOf(p.getValue()) + 1);
            }
        });
        colmaNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        colTenNhanVien.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        colSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        colChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
        setEmployee();
        tblNhanVien.setItems(custList);
        searchFilter();
    }

    @FXML
    private void searchFilter() {
        FilteredList<NhanVien> filterData = new FilteredList<>(custList, e -> true);
        txtTimNV.setOnKeyReleased(e -> {
            txtTimNV.textProperty().addListener((observable, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super NhanVien>) cust -> {
                    if (newValue == null) {
                        return true;
                    }
                    String toLowerCaseFilter = newValue.toLowerCase();
                    if (cust.getMaNhanVien().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    } else if (cust.getTenNhanVien().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    } else if (cust.getSoDienThoai().toLowerCase().contains(toLowerCaseFilter)) {
                        return true;
                    } else return cust.getChucVu().toLowerCase().contains(toLowerCaseFilter);
                });
            });

            final SortedList<NhanVien> customers = new SortedList<>(filterData);
            customers.comparatorProperty().bind(tblNhanVien.comparatorProperty());
            tblNhanVien.setItems(customers);
        });

    }

    @FXML
    public void setEmployee() throws SQLException {
        Connection conn = DriverManager.getConnection(connectUrl, username, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
        while (rs.next()) {
            String maNhanVien = rs.getString("Employee_ID");
            String tenNhanVien = rs.getString("Employee_Name");
            Date ngaySinh = rs.getDate("Employee_DateofBirth");
            String gioiTinh = rs.getString("Employee_Gender");
            String diaChi = rs.getString("Employee_Address");
            String soDienThoai = rs.getString("Employee_Phone");
            String email = rs.getString("Employee_Email");
            String CCCD = rs.getString("Employee_CCCD");
            String chucVu = rs.getString("Employee_Position");
            double luong = rs.getDouble("Employee_Salary");
            Date ngayVaoLam = rs.getDate("Employee_BeginDate");
            String trangThai = rs.getString("Employee_Status");
            custList.add(new NhanVien(maNhanVien, tenNhanVien, ngaySinh, gioiTinh, diaChi, soDienThoai, email, CCCD, chucVu, luong, ngayVaoLam, trangThai));
        }
        rs.close();
        stmt.close();
        conn.close();
    }
    public void handlePrintEmployeeList() throws JRException, SQLException, ClassNotFoundException {
        // Establish a connection to the database
        Connection connection = DriverManager.getConnection(connectUrl, username, password);

        // Create a statement
        Statement statement = connection.createStatement();

        // Execute a query and get the result set
        ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

        // Convert the result set to a JRDataSource
        JRDataSource dataSource = new JRResultSetDataSource(resultSet);

        // Compile the report
        JasperReport jasperReport = JasperCompileManager.compileReport("C:/Users/TienDat/JaspersoftWorkspace/Employee/EmployeeReport.jrxml");

        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        // Export the report to a .docx file
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "C:/Users/TienDat/JaspersoftWorkspace/Employee/EmployeeReport.html");

        // Close the result set, statement, and connection
        resultSet.close();
        statement.close();
        connection.close();
    }

    @FXML
    void handleNhanVienClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            // Get the selected NhanVien object from tblNhanVien
            NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();
            // Open EmployeeInfoPage with the selected NhanVien data
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeInfoPage.fxml"));
            Parent root = loader.load();
            EmployeeInfoController controller = loader.getController();
            controller.initData(selectedNhanVien);
            Scene scene = new Scene(root);
            Stage stage = (Stage) tblNhanVien.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void handleBtnAddEmployee(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EmployeeInfoPage.fxml"));
        Parent root = loader.load();
        EmployeeInfoController controller = loader.getController();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "tiendat1102");
        // Create a statement to execute the query
        Statement stmt = conn.createStatement();
        // Execute the query and store the result in a ResultSet
        ResultSet rs = stmt.executeQuery("SELECT MAX(Employee_ID) FROM employee");
        // Get the biggest value of Employee_ID from the ResultSet
        String newId = "";
        if (rs.next()) {
            newId = rs.getString(1);
        }
        // Increment the value of id by 1 to generate a new ID
        newId = String.valueOf(Integer.parseInt(newId) + 1);
        // Close the ResultSet and statement
        rs.close();
        stmt.close();
        controller.initData(new NhanVien(newId, "", Date.valueOf("0000-01-01"), "", "", "", "", "", "", 0, Date.valueOf("0000-01-01"), ""));
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnAddEmployee.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleBtnDelEmployee(ActionEvent event) throws IOException, SQLException {
        // Get the selected NhanVien object from tblNhanVien
        NhanVien selectedNhanVien = tblNhanVien.getSelectionModel().getSelectedItem();
        if (selectedNhanVien == null) {
            // No employee selected, show an alert and return
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn một nhân viên để xóa.");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }
        // Confirm with the user before deleting the employee
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn xóa nhân viên này?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete the selected employee from the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "tiendat1102");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM employee WHERE Employee_ID = ?");
            stmt.setString(1, selectedNhanVien.getMaNhanVien());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            // Remove the selected employee from the table view
            tblNhanVien.getItems().remove(selectedNhanVien);
        }
    }
    void handleCheckinButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkin.fxml"));
        Parent dashboardParent = loader.load();
        CheckinController checkinController = loader.getController();
        checkinController.setEmployeeID(employeeID);
        System.out.println("employeeID in DashboardController: " + employeeID); // Add this line
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navCheckinButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }
    @FXML
    public void handleNavDashboardButton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent dashboardParent = loader.load();
        DashboardController dashboardController = loader.getController();
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) navDashboardButton.getScene().getWindow();
        dashboardController.initialize(employeeID);
        window.setScene(dashboardScene);
    }
    //Navitation
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
        System.out.println("employeeID in DashboardController: " + employeeID); // Add this line
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
        Stage window = (Stage) navEmployeeButton.getScene().getWindow();
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
}
