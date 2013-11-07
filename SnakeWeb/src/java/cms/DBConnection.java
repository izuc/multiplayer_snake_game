package cms;
import java.sql.*;

public class DBConnection {
    private static Connection connection;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL_LOCATION = "jdbc:mysql://localhost/snake";
    private static final String DB_USER_NAME = "root";
    private static final String DB_PASSWORD = "";
    
    static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            DBConnection.connection = DriverManager.getConnection(JDBC_URL_LOCATION, DB_USER_NAME, DB_PASSWORD);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return DBConnection.connection;
    }
        
    static void closeConnection() {
        try {
            DBConnection.connection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
