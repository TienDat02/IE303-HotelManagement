package app.ie303hotelmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeSingleton {
    private static EmployeeSingleton instance = new EmployeeSingleton();
    private String employeeID;
    private Boolean isManager = false;

    private EmployeeSingleton() {}

    public static EmployeeSingleton getInstance() {
        return instance;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
        try {
            Connection conn = DriverManager.getConnection(DataConnector.getDatabaseUrl(), DataConnector.getUsername(), DataConnector.getPassword());
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM account WHERE Employee_ID = '" + employeeID + "'");
            stmt.executeQuery();
            ResultSet rs = stmt.getResultSet();
            if (rs.next()){
                isManager = rs.getBoolean("isAdmin");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getEmployeeID() {
        return employeeID;
    }
    public void setIsManager(Boolean isManager) {
        this.isManager = isManager;
    }
    public Boolean getIsManager() {
        return isManager;
    }
}