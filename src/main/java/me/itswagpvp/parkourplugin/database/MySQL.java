package me.itswagpvp.parkourplugin.database;

import me.itswagpvp.parkourplugin.ParkourPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class MySQL extends Database {

    final static String[] SQL_SETUP_STATEMENTS = {
            "CREATE TABLE IF NOT EXISTS playerdata (" +
                    "`uuid` VARCHAR(36) NOT NULL," +
                    "`time` DOUBLE NOT NULL, " +
                    "PRIMARY KEY (`uuid`)" +
                    ");",

    };
    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();
    final String host = plugin.getConfig().getString("mysql.host");
    final int port = plugin.getConfig().getInt("mysql.port");
    final String database = plugin.getConfig().getString("mysql.database");
    final String username = plugin.getConfig().getString("mysql.username");
    final String password = plugin.getConfig().getString("mysql.password");
    final String params = "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";

    private Connection connection;

    public MySQL(ParkourPlugin instance) {
        super(instance);
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    synchronized (plugin) {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = (DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + params, username, password));
                    }
                } catch (SQLException ex) {
                    plugin.getLogger().log(Level.SEVERE, "An exception occurred initialising the mySQL database: ", ex);
                } catch (ClassNotFoundException ex) {
                    plugin.getLogger().log(Level.SEVERE, "The mySQL JBDC library is missing! Please download and place this in the /lib folder.");
                }
            }
        } catch (SQLException exception) {
            plugin.getLogger().log(Level.WARNING, "An error occurred checking the status of the SQL connection: ", exception);
        }
        return connection;
    }

    @Override
    public void load() {
        connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            for (String tableCreationStatement : SQL_SETUP_STATEMENTS) statement.execute(tableCreationStatement);
            statement.close();
        } catch (SQLException exception) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred creating tables: ", exception);
            exception.printStackTrace();
        }

        initialize();
    }
}
