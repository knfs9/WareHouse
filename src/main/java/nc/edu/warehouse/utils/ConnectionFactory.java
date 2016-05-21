package nc.edu.warehouse.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance = new ConnectionFactory();
    private Connection connection;
    private Properties props;
    private static final Logger log = Logger.getLogger(ConnectionFactory.class);

    private ConnectionFactory() {
        InputStream fis = getClass().getClassLoader().getResourceAsStream("connection.properties");
        props = new Properties();
        try {
            props.load(fis);
            String driverClass = props.getProperty("DRIVER_CLASS");
            Class.forName(driverClass);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                log.info("Connection already have instance");
                return connection;
            } else {
                String url = props.getProperty("URL");
                String user = props.getProperty("USER");
                String password = props.getProperty("PASSWORD");
                connection = DriverManager.getConnection(url, user, password);
                log.info("Connection created");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        return instance.createConnection();
    }
}
