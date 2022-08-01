package me.itswagpvp.parkourplugin.database.data;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import me.itswagpvp.parkourplugin.database.Database;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerData {
    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    /**
     * Get player's best time
     *
     * @param uuid
     * @return -1 if the player doesn't exist, otherwise it will return the player time
     */
    public static double getPlayerTime(UUID uuid) {
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("You can't call this method from the main thread!");
        }
        Connection connection = Database.getDatabase().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM playerdata WHERE `uuid`=?;")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double time = resultSet.getDouble("time");
                statement.close();
                return time;
            } else return -1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

    }

    /**
     * Create the player data if it doesn't exist, if it exists the data is stored
     *
     * @param playerUUID
     * @param value
     */
    public static void save(UUID playerUUID, double value) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        Connection connection = Database.getDatabase().getConnection();
                        PreparedStatement ps = connection.prepareStatement("REPLACE INTO playerdata (uuid,time) VALUES (?,?);");
                        ps.setString(1, playerUUID.toString());
                        ps.setDouble(2, value);
                        ps.execute();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

}
