package nc.edu.warehouse.database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String USER = "root";
    public static final String PASSWORD = "522000";
    public static final String URL = "jdbc:mysql://localhost:3306/warehousedb";

    Logger log = Logger.getLogger(ConnectionFactory.class);

    private ConnectionFactory(){
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection(){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection(){
        return instance.createConnection();
    }
}
