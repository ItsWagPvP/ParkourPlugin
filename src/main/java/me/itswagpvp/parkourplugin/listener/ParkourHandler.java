package me.itswagpvp.parkourplugin.listener;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import me.itswagpvp.parkourplugin.database.cache.Cache;
import me.itswagpvp.parkourplugin.database.data.PlayerData;
import me.itswagpvp.parkourplugin.utils.Checkpoints;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class ParkourHandler implements Listener {

    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    private static final HashMap<Player, Boolean> playerDelay = new HashMap<>();
    private static final HashMap<Player, Integer> playerCheckpoints = new HashMap<>();
    private static final HashMap<Player, Boolean> playersInParkour = new HashMap<>();
    private static final HashMap<Player, Long> playerTime = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        Location loc = block.getLocation();

        if (!alreadyStepped(player)) {
            saveStep(player);
        } else {
            e.setCancelled(true);
            return;
        }

        if (loc.distance(Checkpoints.getStart()) == 0D) {
            player.sendMessage("§aParkour started!");
            playersInParkour.put(player, true);
            playerCheckpoints.put(player, 0);

            playerTime.put(player, System.currentTimeMillis());
            return;
        }

        if (playersInParkour.containsKey(player)) {
            if (loc.distance(Checkpoints.getEnd()) == 0D) {
                if (!playersInParkour.containsKey(player)) {
                    player.sendMessage("§cYou're not in parkour!");
                    return;
                }

                double time = (System.currentTimeMillis() - playerTime.get(player)) / 1000D;
                player.sendMessage("§cParkour ended in " + time + " seconds!");

                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    double bestPlayerValue = PlayerData.getPlayerTime(player.getUniqueId());
                    if (time < bestPlayerValue) {
                        Cache.savePlayer(player, time);
                        PlayerData.save(player.getUniqueId(), time);
                    }
                });

                playersInParkour.remove(player);
                playerCheckpoints.remove(player);
                playerTime.remove(player);
                return;
            }

            if (loc.distance(Checkpoints.getCheckpoints().get(playerCheckpoints.get(player) + 1)) == 0D) {
                player.sendMessage("§aCheckpoint n" + (playerCheckpoints.get(player) + 1) + " reached!");
                playerCheckpoints.put(player, playerCheckpoints.get(player) + 1);
            }
        }
    }

    private boolean alreadyStepped(Player p) {
        return playerDelay.containsKey(p);
    }

    private void saveStep(Player p) {
        playerDelay.put(p, true);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> playerDelay.remove(p), 20L);
    }

}
