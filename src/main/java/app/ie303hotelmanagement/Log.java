package app.ie303hotelmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Log {
    private LocalDateTime logTime;
    private String logIndex;
    private String logDetail;
    private String logStatus;
    public Log(LocalDateTime logTime, String logIndex, String logStatus, String logDetails) {
        this.logTime = logTime;
        this.logIndex = logIndex;
        this.logDetail = logDetails;
        this.logStatus = logStatus;
    }
    public void insertLog() {
        try {
            Connection connection = DriverManager.getConnection(DataConnector.getDatabaseUrl(), DataConnector.getUsername(), DataConnector.getPassword());
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Log (Log_Time, Log_Index, Log_Status, Log_Detail, Employee_ID) VALUES (?, ?, ?, ?, ?)");
            statement.setTimestamp(1, Timestamp.valueOf(logTime));
            statement.setString(2, logIndex);
            statement.setString(3, logStatus);
            statement.setString(4, logDetail);
            statement.setString(5, EmployeeSingleton.getInstance().getEmployeeID());

            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public LocalDateTime getLogTime() {
        return logTime;
    }
    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }
    public String getLogIndex() {
        return logIndex;
    }
    public void setLogIndex(String logIndex) {
        this.logIndex = logIndex;
    }
    public String getLogDetail() {
        return logDetail;
    }
    public void setLogDetail(String logDetails) {
        this.logDetail = logDetails;
    }
    public String getLogStatus() {
        return logStatus;
    }
    public void setLogStatus(String logStatus) {
        this.logStatus = logStatus;
    }
}
