package app.ie303hotelmanagement;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarControl extends GridPane {

    private int month;
    private int year;

    public CalendarControl() {
        this.month = LocalDate.now().getMonthValue();
        this.year = LocalDate.now().getYear();
        updateCalendar();
    }

    public void updateCalendar() {
        this.getChildren().clear();

        // Add month and year labels
        Label monthLabel = new Label(Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault()));
        Label yearLabel = new Label(Integer.toString(year));
        this.add(monthLabel, 0, 0, 7, 1);
        this.add(yearLabel, 7, 0, 7, 1);

        // Add day labels
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            this.add(dayLabel, i, 1);
        }

        // Add days
        LocalDate date = LocalDate.of(year, month, 1);
        int row = 2;
        int col = date.getDayOfWeek().getValue() % 7;
        while (date.getMonthValue() == month) {
            Label dayLabel = new Label(Integer.toString(date.getDayOfMonth()));
            this.add(dayLabel, col, row);
            col = (col + 1) % 7;
            if (col == 0) {
                row++;
            }
            date = date.plusDays(1);
        }
    }

    public void setMonth(int month) {
        this.month = month;
        updateCalendar();
    }

    public void setYear(int year) {
        this.year = year;
        updateCalendar();
    }
}