package me.itswagpvp.parkourplugin.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.itswagpvp.parkourplugin.utils.ScoreboardUtils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ScoreboardHandler implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        //if (e.getFrom().distance(e.getTo()) < 1) return;

        Location loc = e.getTo();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
        for (ProtectedRegion region : set) {
            if (region.getId().equals("parkour")) {
                ScoreboardUtils.showScoreboard(e.getPlayer());
            }
        }
    }

}
