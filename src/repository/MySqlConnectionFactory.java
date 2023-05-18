package repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionFactory {

    private final String username = "rmi";
    private final String password = "root";
    private final String databaseName = "rmi";
    private Connection connection;
    private static volatile MySqlConnectionFactory instance;

    private MySqlConnectionFactory() {
        establishConnection();
    }

    private void establishConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:MySql://localhost:3306/" + this.getDatabaseName() + "?serverTimezone=Asia/Hebron"
                    , this.getUsername(), this.getPassword());
        } catch (Exception e) {
            System.out.println("EXCEPTION ! Couldn't Create Connection " + e);
        }
    }

    public static MySqlConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (MySqlConnectionFactory.class) {
                if (instance == null) {
                    instance = new MySqlConnectionFactory();
                }
            }
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public Connection getConnection() {
        return connection;
    }
}
