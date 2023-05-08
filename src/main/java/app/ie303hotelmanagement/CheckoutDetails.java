package app.ie303hotelmanagement;

import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDetails {

    @FXML
    AnchorPane checkoutPane;
    private String connectUrl = DataConnector.getDatabaseUrl();
    private String username = DataConnector.getUsername();
    private String password = DataConnector.getPassword();


    @FXML
    private Text guestName;
    @FXML
    private Text guestID;
    @FXML
    private Text guestPhone;
    @FXML
    private Text guestNote;

    @FXML
    private Text totalMoney;

    @FXML
    private Button LogoutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button checkOutButton;

    @FXML
    private Text employeeName;
    @FXML
    private Text employeeID1;
    private String employeeID;
    private String customerID;
    @FXML private Button billButton;

    @FXML
    private javafx.scene.control.TableView<CheckoutRoomDetails> roomTable;
    @FXML private TableColumn<CheckoutRoomDetails, String> roomIDColumn;
    @FXML private TableColumn<CheckoutRoomDetails, String> roomTypeColumn;
    @FXML private TableColumn<CheckoutRoomDetails, Float> roomPriceColumn;
    @FXML private TableColumn<CheckoutDetails, Integer> roomFloorColumn;
    @FXML private TableColumn<CheckoutRoomDetails, Date> checkinDateColumn;
    @FXML private TableColumn<CheckoutDetails, Date> checkoutDateColumn;
    @FXML private TableColumn<CheckoutRoomDetails, Time> checkinTimeColumn;
    @FXML private TableColumn<CheckoutDetails, Time> checkoutTimeColumn;
    @FXML private TableColumn<CheckoutRoomDetails, Float> hoursColumn;
    @FXML private TableColumn<CheckoutRoomDetails, Float> roomTotalColumn;
    @FXML private Text roomsTotal;

    @FXML private javafx.scene.control.TableView<CheckoutServiceDetails> serviceTable;
    @FXML private TableColumn<CheckoutServiceDetails, String> serviceIDColumn;
    @FXML private TableColumn<CheckoutServiceDetails, String> serviceNameColumn;
    @FXML private TableColumn<CheckoutServiceDetails, String> serviceRoomIDColumn = new TableColumn<>("Room ID");
    @FXML private TableColumn<CheckoutServiceDetails, Float> servicePriceColumn = new TableColumn<>("Price");
    @FXML private TableColumn<CheckoutServiceDetails, Integer> serviceQuantityColumn = new TableColumn<>("Quantity");
    @FXML private TableColumn<CheckoutServiceDetails, Date> serviceDateColumn = new TableColumn<>("Date");
    @FXML private TableColumn<CheckoutServiceDetails, Time> serviceTimeColumn = new TableColumn<>("Time");
    @FXML private TableColumn<CheckoutServiceDetails, Float> serviceTotalColumn;
    @FXML private Text servicesTotal;
    @FXML private Button checkoutButton;
    @FXML private Button deleteRoomButton;
    @FXML private DatePicker nowDate;
    @FXML private TextField nowTime;




    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }


    public void setCustomer(String customerID) {
        this.customerID = customerID;
    }

    public void setData() {
        nowDate.setValue(LocalDate.now());
        nowTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        initialRoomTable();
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT Guest_Name, Guest_Phone, Guest_Notes FROM guest WHERE Guest_ID = ?");
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                guestID.setText(customerID);
                guestName.setText(rs.getString("Guest_Name"));
                guestPhone.setText(rs.getString("Guest_Phone"));
                guestNote.setText(rs.getString("Guest_Notes"));
            }
            stmt = conn.prepareStatement("SELECT Employee_Name FROM employee WHERE Employee_ID = ?");
            stmt.setString(1, employeeID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                employeeID1.setText(employeeID);
                employeeName.setText(rs.getString("Employee_Name"));
            }
            conn.close();
            initialServiceTable();
            float roomsTotalMoney = Float.parseFloat(roomsTotal.getText().replace(",","."));
            float servicesTotalMoney = Float.parseFloat(servicesTotal.getText().replace(",","."));
            totalMoney.setText(String.valueOf(roomsTotalMoney + servicesTotalMoney));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialRoomTable() {
        System.out.println("initialRoomTable");
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            PreparedStatement stmt = conn.prepareStatement("SELECT room.Room_ID, Room_Type, Room_Price, Room_Floor, Checkin_Date FROM checkin, room where room.Room_ID = checkin.Room_ID AND Guest_ID = ?");
            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Replace the column names with the actual names of your tableview columns
                roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
                roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
                roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
                roomFloorColumn.setCellValueFactory(new PropertyValueFactory<>("roomFloor"));
                checkinDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkinDate"));
                checkinTimeColumn.setCellValueFactory(new PropertyValueFactory<>("checkinTime"));

                checkoutTimeColumn.setCellValueFactory(new PropertyValueFactory<>("checkoutTime"));
                checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
                checkoutDateColumn.setCellValueFactory(cellData -> {
                    return new SimpleObjectProperty<>(Date.valueOf(LocalDate.now()));
                });
                checkoutTimeColumn.setCellValueFactory(cellData -> {
                    return new SimpleObjectProperty<>(Time.valueOf(LocalTime.now()));
                });
                hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
                roomTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
                // Create a new instance of your tableview object and add it to the tableview
                CheckoutRoomDetails checkoutRoomDetails = new CheckoutRoomDetails(rs.getString("Room_ID"), rs.getString("Room_Type"), rs.getFloat("Room_Price"), rs.getInt("Room_Floor"), rs.getDate("Checkin_Date"), rs.getTime("Checkin_Date"), Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
                roomTable.getItems().add(checkoutRoomDetails);
            }
            float total = 0;
            for (CheckoutRoomDetails room : roomTable.getItems()) {
                total += room.getTotal();
            }
            roomsTotal.setText(String.format("%.2f", total));
            conn.close();
            roomTable.setPlaceholder(new Label(""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialServiceTable() {
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            for (CheckoutRoomDetails room : roomTable.getItems()) {
                System.out.println(room.getRoomID());
                PreparedStatement stmt = conn.prepareStatement("select service.Service_ID, Service_Name, Room_ID, Service_Quantity, Service_Price, Service_Date, Service_Time from room_service, service where service.Service_ID = room_service.Service_ID and Room_ID = ?");
                stmt.setString(1, room.getRoomID());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Replace the column names with the actual names of your tableview columns
                    serviceIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
                    serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
                    if (serviceRoomIDColumn != null) {
                        serviceRoomIDColumn.setCellValueFactory(new PropertyValueFactory<>("serviceRoomID"));
                    }
                    serviceQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("serviceQuantity"));
                    servicePriceColumn.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
                    serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
                    serviceTimeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceTime"));
                    serviceTotalColumn.setCellValueFactory(new PropertyValueFactory<>("serviceTotal"));
                    // Create a new instance of your tableview object and add it to the tableview
                    CheckoutServiceDetails checkoutServiceDetails = new CheckoutServiceDetails(rs.getString("Service_ID"), rs.getString("Service_Name"), rs.getString("Room_ID"), rs.getInt("Service_Quantity"), rs.getFloat("Service_Price"), rs.getDate("Service_Date"), rs.getTime("Service_Time"));
                    serviceTable.getItems().add(checkoutServiceDetails);
                }
            }
            float total = 0;
            for (CheckoutServiceDetails service : serviceTable.getItems()) {
                total += service.getServiceTotal();
            }
            servicesTotal.setText(String.format("%.2f", total));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDeleteRoomButton() {
        CheckoutRoomDetails selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            // Delete corresponding rows in serviceTable
            List<CheckoutServiceDetails> servicesToDelete = new ArrayList<>();
            for (CheckoutServiceDetails service : serviceTable.getItems()) {
                if (service.getServiceRoomID().equals(selectedRoom.getRoomID())) {
                    servicesToDelete.add(service);
                }
            }
            serviceTable.getItems().removeAll(servicesToDelete);

            // Remove selected room from roomTable
            roomTable.getItems().remove(selectedRoom);

            // Recalculate totals
            float roomsTotalMoney = Float.parseFloat(roomsTotal.getText().replace(",","."));
            float servicesTotalMoney = Float.parseFloat(servicesTotal.getText().replace(",","."));
            float newServicesTotalMoney = 0;
            for (CheckoutServiceDetails service : serviceTable.getItems()) {
                newServicesTotalMoney += service.getServiceTotal();
            }
            servicesTotal.setText(String.format("%.2f", newServicesTotalMoney));
            totalMoney.setText(String.valueOf(roomsTotalMoney + newServicesTotalMoney));
        } else {
        // show an alert if no room is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Empty Selection");
        alert.setHeaderText("No Room Selected");
        alert.setContentText("Please select a room in the table");

        alert.showAndWait();
    }
    }
    @FXML
    public void handleCheckoutButton() {
        try {
            Connection conn = DriverManager.getConnection(connectUrl, username, password);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO checkout (Guest_ID, Room_ID, Checkout_Time, Checkout_Date, Total_Hours, Service_Total, Room_Total, Total, Employee_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement historyStmt = conn.prepareStatement("INSERT INTO checkin_history SELECT * FROM checkin WHERE Room_ID = ?");
            PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM checkin WHERE Room_ID = ?");
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE room SET Room_Status = 'Trống' WHERE Room_ID = ?");
            PreparedStatement usedServiceStmt = conn.prepareStatement("INSERT INTO used_service SELECT * FROM room_service WHERE ROOM_ID = ?");
            PreparedStatement deleteServiceStmt = conn.prepareStatement("DELETE FROM room_service WHERE ROOM_ID = ?");
            PreparedStatement insertBillStmt = conn.prepareStatement("INSERT INTO bill (Guest_ID, Bill_Date, Bill_Time, Total_Cost, Employee_ID) VALUES (?, ?, ?, ?, ?)");
            for (CheckoutRoomDetails room : roomTable.getItems()) {
                float serviceTotal = 0;
                for (CheckoutServiceDetails service : serviceTable.getItems()) {
                    if (service.getServiceRoomID().equals(room.getRoomID())) {
                        serviceTotal += service.getServiceTotal();
                    }
                }
                stmt.setString(1, customerID);
                stmt.setString(2, room.getRoomID());
                stmt.setDate(4, Date.valueOf(room.getCheckoutDate().toLocalDate()));
                stmt.setTime(3, Time.valueOf(room.getCheckoutTime().toLocalTime()));
                stmt.setFloat(5, room.getHours());
                stmt.setFloat(6, serviceTotal);
                stmt.setFloat(7, room.getTotal());
                stmt.setFloat(8, (serviceTotal+room.getTotal()));
                stmt.setString(9, employeeID);
                stmt.executeUpdate();
                historyStmt.setString(1, room.getRoomID());
                historyStmt.executeUpdate();
                deleteStmt.setString(1, room.getRoomID());
                deleteStmt.executeUpdate();
                updateStmt.setString(1, room.getRoomID());
                updateStmt.executeUpdate();
                usedServiceStmt.setString(1, room.getRoomID());
                usedServiceStmt.executeUpdate();
                deleteServiceStmt.setString(1, room.getRoomID());
                deleteServiceStmt.executeUpdate();
            }
            insertBillStmt.setString(1, customerID);
            insertBillStmt.setDate(2, Date.valueOf(nowDate.getValue()));
            insertBillStmt.setTime(3, Time.valueOf(nowTime.getText()));
            insertBillStmt.setFloat(4, Float.parseFloat(totalMoney.getText().replace(",",".")));
            insertBillStmt.setString(5, employeeID);
            insertBillStmt.executeUpdate();
            conn.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkout Successful");
            alert.setHeaderText(null);
            alert.setContentText("Checkout has been completed successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Checkout Failed");
            alert.setHeaderText(null);
            alert.setContentText("Checkout has failed. Please try again later.");
            alert.showAndWait();
        }
    }
    @FXML
    public void printBill() throws IOException {
        backButton.setVisible(false);
        checkoutButton.setVisible(false);
        deleteRoomButton.setVisible(false);
        billButton.setVisible(false);

        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Add a new page to the document with landscape orientation
        PDRectangle pageRect = new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth());
        PDPage page = new PDPage(pageRect);
        document.addPage(page);

        // Create a new content stream for the page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Get the root node of the checkoutPane
        Node root = checkoutPane;

        // Create a snapshot of the root node
        SnapshotParameters snapshotParams = new SnapshotParameters();
        WritableImage snapshot = root.snapshot(snapshotParams, null);

        // Convert the snapshot to a BufferedImage
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

        // Create a new PDImageXObject from the BufferedImage
        PDImageXObject pdImage = LosslessFactory.createFromImage(document, image);

        // Draw the image on the PDF page
        contentStream.drawImage(pdImage, 0, 0, PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth());

        // Close the content stream
        contentStream.close();

        // Save the document to a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(billButton.getScene().getWindow());
        if (file != null) {
            document.save(file);
        }

        // Close the document
        document.close();
        backButton.setVisible(true);
        checkoutButton.setVisible(true);
        deleteRoomButton.setVisible(true);
        billButton.setVisible(true);
    }
    public void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Check-out.fxml"));
        Parent dashboardParent = loader.load();
        CheckOutController checkOutController = loader.getController();
        checkOutController.setEmployeeID(employeeID);
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage) backButton.getScene().getWindow();
        window.setScene(dashboardScene);
    }


}
