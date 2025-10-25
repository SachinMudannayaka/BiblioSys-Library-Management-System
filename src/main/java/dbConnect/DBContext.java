package dbConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    /* USE THIS METHOD FOR YOUR DATABASE CONNECTION */
    public Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, userID, password);
    }

    /* Database configuration - update username/password as needed */
    private final String serverName = "localhost"; // 127.0.0.1
    private final String dbName = "Library_Manager";
    private final String portNumber = "3306";  // Default MySQL port
    private final String userID = "root";
    private final String password = ""; // your MySQL password
}






