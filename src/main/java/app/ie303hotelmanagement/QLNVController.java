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

    final ObservableList<NhanVien> custList = FXCollections.observableArrayList();

    @FXML
    void handleReportBtn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Report.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) reportBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");
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
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hotelmanagement", "root", "123456");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM employee WHERE Employee_ID = ?");
            stmt.setString(1, selectedNhanVien.getMaNhanVien());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            // Remove the selected employee from the table view
            tblNhanVien.getItems().remove(selectedNhanVien);
        }
    }

}

