package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection = null;
    private static String url = "jdbc:mysql://104.237.13.60:3306/dbkoel";
    private static String user = "dbuser05";
    private static String password = "pa$$05";


    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}