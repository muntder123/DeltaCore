package club.deltapvp.core.impl.sql.h2;

import club.deltapvp.deltacore.api.utilities.sql.SQLConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connector implements SQLConnector {

    private final String path;
    private final String name;
    private final String endSuffix;
    private Connection connection;

    public H2Connector(String path, String name) {
        this(path, name, "db");
    }

    public H2Connector(String path, String databaseName, String endSuffix) {
        this.path = path;
        this.name = databaseName;
        this.endSuffix = endSuffix;
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");

        connection = DriverManager.getConnection("jdbc:h2:" + path + "/" + name + "." + endSuffix);
    }

    @Override
    public void disconnect() {
        if (!isConnected()) return;

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to disconnect from database");
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
