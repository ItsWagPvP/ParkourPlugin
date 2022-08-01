package me.itswagpvp.parkourplugin.database.cache;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import me.itswagpvp.parkourplugin.database.Database;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class TopManager {

    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    private static final ConcurrentHashMap<Integer, String> topNames = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Integer, Double> topValues = new ConcurrentHashMap<>();

    public void updateCache(int position, String name, double value) {
        topNames.put(position, name);
        topValues.put(position, value);
    }

    public String getName(int position) {
        if (topNames.get(position) == null) return "-";
        return Bukkit.getOfflinePlayer(UUID.fromString(topNames.get(position))).getName();
    }

    public String getValue(int position) {
        if (topValues.get(position) == null) return "-";
        double seconds = topValues.get(position);
        return "" + seconds;
    }

    public void updateTop() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            try {
                Connection connection = Database.getDatabase().getConnection();
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM playerdata ORDER BY time ASC");
                ResultSet rs = ps.executeQuery();

                int i = 1;
                while (rs.next()) {
                    if (i <= 5) {
                        String name = rs.getString("uuid");
                        double value = rs.getDouble("time");

                        new TopManager().updateCache(i, name, value);

                    } else break;

                    i++;
                }

            } catch (SQLException ex) {
                plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
            }

        }, 0, 20 * 10L);
    }
}
