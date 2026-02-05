package pl.craftverse.shop;

import java.sql.*;

public class Database {

    private final CraftVerseShop plugin;
    private Connection connection;

    public Database(CraftVerseShop plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://" +
                plugin.getConfig().getString("database.host") + ":" +
                plugin.getConfig().getInt("database.port") + "/" +
                plugin.getConfig().getString("database.name"),
                plugin.getConfig().getString("database.user"),
                plugin.getConfig().getString("database.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
