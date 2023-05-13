package app.ie303hotelmanagement;

public class DataConnector {
    private static String databaseUrl = "jdbc:mysql://localhost:3306/HotelManagement";
    private static String username = "root";
    private static String password = "123456";
    public static String getDatabaseUrl() {
        return databaseUrl;
    }
    public static void setDatabaseUrl(String databaseUrl) {
        DataConnector.databaseUrl = databaseUrl;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        DataConnector.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        DataConnector.password = password;
    }
}
