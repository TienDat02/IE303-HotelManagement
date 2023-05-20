package app.ie303hotelmanagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
public class AlterServiceController {
    @FXML
    private TextField serviceStatus;
    @FXML
    private TextField serviceName;
    @FXML
    private TextField servicePrice;
    @FXML
    private TextField serviceDescription;
    @FXML
    private Button btnAlter;
    @FXML
    private Button btnBack;
    private String operateServiceID;
    private String databaseUrl = DataConnector.getDatabaseUrl();
    private String databaseUsername = DataConnector.getUsername();
    private String databasePassword = DataConnector.getPassword();
    private Service serviceAlter=new Service();

    public void setOperateServiceID(String operateServiceID) {
        this.operateServiceID = operateServiceID;
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM service WHERE Service_ID = ?");
            stmt.setString(1, operateServiceID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                serviceName.setText(rs.getString("Service_Name"));
                servicePrice.setText(rs.getString("Service_Price"));
                serviceDescription.setText(rs.getString("Service_Description"));
                serviceStatus.setText(rs.getString("Service_Status"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText("Không tìm thấy dịch vụ");
                alert.setContentText("Không tìm thấy dịch vụ với ID " + operateServiceID);
                alert.showAndWait();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void AlterService(ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            PreparedStatement stmt = conn.prepareStatement("UPDATE service SET Service_Name = ?, Service_Price = ?, Service_Description = ?, Service_Status = ? WHERE Service_ID = ?");
            stmt.setString(1, serviceName.getText());
            stmt.setString(2, servicePrice.getText());
            stmt.setString(3, serviceDescription.getText());
            stmt.setString(4, serviceStatus.getText());
            stmt.setString(5, operateServiceID);
            stmt.executeUpdate();
            conn.close();

            // Display success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thành công");
            alert.setHeaderText("Cập nhật dịch vụ thành công");
            alert.setContentText("Dịch vụ với ID " + operateServiceID + " đã được cập nhật thành công.");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();

            // Display error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lôi");
            alert.setHeaderText("Cập nhật dịch vụ thất bại");
            alert.setContentText("Thất bại trong việc cập nhật dịch vụ với ID " + operateServiceID + ".");
            alert.showAndWait();
        }
    }
}
