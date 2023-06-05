package app.ie303hotelmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ChangePolicyController {
    @FXML
    private TextField diamondPercent;
    @FXML private TextField goldPercent;
    @FXML private TextField silverPercent;
    @FXML private TextField diamondNumber;
    @FXML private TextField goldNumber;
    @FXML private TextField silverNumber;
    @FXML private TextField diamondValue;
    @FXML private TextField goldValue;
    @FXML private TextField silverValue;
    String databaseUrl = DataConnector.getDatabaseUrl();
    String databaseUsername = DataConnector.getUsername();
    String databasePassword = DataConnector.getPassword();
    @FXML
    public void initialize(){
        diamondPercent.setText(String.valueOf(TiersSingleton.getInstance().getDiamondDiscount()));
        goldPercent.setText(String.valueOf(TiersSingleton.getInstance().getGoldDiscount()));
        silverPercent.setText(String.valueOf(TiersSingleton.getInstance().getSilverDiscount()));
        diamondNumber.setText(String.valueOf(TiersSingleton.getInstance().getDiamondNearestCheckouts()));
        goldNumber.setText(String.valueOf(TiersSingleton.getInstance().getGoldNearestCheckouts()));
        silverNumber.setText(String.valueOf(TiersSingleton.getInstance().getSilverNearestCheckouts()));
        diamondValue.setText(String.valueOf(TiersSingleton.getInstance().getDiamondNearestCheckoutValue()));
        goldValue.setText(String.valueOf(TiersSingleton.getInstance().getGoldNearestCheckoutValue()));
        silverValue.setText(String.valueOf(TiersSingleton.getInstance().getSilverNearestCheckoutValue()));
    }
    @FXML
    public void handleChangePolicy(){
        TiersSingleton.getInstance().setDiamondDiscount(Integer.parseInt(diamondPercent.getText()));
        TiersSingleton.getInstance().setGoldDiscount(Integer.parseInt(goldPercent.getText()));
        TiersSingleton.getInstance().setSilverDiscount(Integer.parseInt(silverPercent.getText()));
        TiersSingleton.getInstance().setDiamondNearestCheckouts(Integer.parseInt(diamondNumber.getText()));
        TiersSingleton.getInstance().setGoldNearestCheckouts(Integer.parseInt(goldNumber.getText()));
        TiersSingleton.getInstance().setSilverNearestCheckouts(Integer.parseInt(silverNumber.getText()));
        TiersSingleton.getInstance().setDiamondNearestCheckoutValue(Float.parseFloat(diamondValue.getText()));
        try {
            Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
            PreparedStatement stmt = conn.prepareStatement("UPDATE  tiers SET Discount = ?, Value_Condition = ?, Nearest_Checkouts = ? WHERE Tier_Name = ?");
            stmt.setInt(1, TiersSingleton.getInstance().getDiamondDiscount());
            stmt.setDouble(2, TiersSingleton.getInstance().getDiamondNearestCheckoutValue());
            stmt.setInt(3, TiersSingleton.getInstance().getDiamondNearestCheckouts());
            stmt.setString(4, "Kim cương");
            stmt.executeUpdate();
            stmt.setInt(1, TiersSingleton.getInstance().getGoldDiscount());
            stmt.setDouble(2, TiersSingleton.getInstance().getGoldNearestCheckoutValue());
            stmt.setInt(3, TiersSingleton.getInstance().getGoldNearestCheckouts());
            stmt.setString(4, "Vàng");
            stmt.executeUpdate();
            stmt.setInt(1, TiersSingleton.getInstance().getSilverDiscount());
            stmt.setDouble(2, TiersSingleton.getInstance().getSilverNearestCheckoutValue());
            stmt.setInt(3, TiersSingleton.getInstance().getSilverNearestCheckouts());
            stmt.setString(4, "Bạc");
            stmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thay đổi chính sách thành công");
            alert.setContentText("Đã thay đổi chính sách thành công.");
            alert.showAndWait();
            Log changePolicyLog = new Log(LocalDateTime.now(), "Thay đổi chính sách", "Thành công", "Thay đổi chính sách thành công.");
            changePolicyLog.insertLog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
