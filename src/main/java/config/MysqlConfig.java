package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
    private static final String url = "jdbc:mysql://localhost:3308/crm_app";
    private static final String userName = "root";
    private static final String password = "admin123";
    
    public static Connection getConnection() {
        Connection connection = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            System.out.println("Error database connection: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection, String location) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                System.out.println("Error close connection at " + location + " :" + e.getMessage());
            }
        }
    }
}
