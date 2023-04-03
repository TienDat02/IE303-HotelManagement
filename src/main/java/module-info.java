module com.example.ie303hotelmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.ie303hotelmanagement to javafx.fxml;
    exports com.example.ie303hotelmanagement;
}