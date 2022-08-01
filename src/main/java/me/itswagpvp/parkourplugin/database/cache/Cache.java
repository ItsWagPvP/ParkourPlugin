package me.itswagpvp.parkourplugin.database.cache;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import me.itswagpvp.parkourplugin.database.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class Cache implements Listener {

    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();
    private static final HashMap<Player, Double> playersTimes = new HashMap<>();

    public static void savePlayer(Player p, double value) {
        playersTimes.put(p, value);
    }

    public static double getValue(Player p) {
        return playersTimes.getOrDefault(p, 0D);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            double value = PlayerData.getPlayerTime(e.getPlayer().getUniqueId());
            savePlayer(e.getPlayer(), value);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        playersTimes.remove(e.getPlayer());
    }

}
