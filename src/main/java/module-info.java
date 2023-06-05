module com.example.ie303hotelmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jasperreports;
    requires javafx.swing;
    requires jrviewer.fx;
    requires pdfbox;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires opencv;
    opens app.ie303hotelmanagement to javafx.fxml;
    exports app.ie303hotelmanagement;
}