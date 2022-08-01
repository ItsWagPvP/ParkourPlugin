package me.itswagpvp.parkourplugin.listener;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import me.itswagpvp.parkourplugin.database.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerHandler implements Listener {

    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            UUID uuid = e.getPlayer().getUniqueId();
            if (PlayerData.getPlayerTime(uuid) == -1) {
                PlayerData.save(uuid, 0D);
            }
        });
    }
}
