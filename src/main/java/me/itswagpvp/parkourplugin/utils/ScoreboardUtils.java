package me.itswagpvp.parkourplugin.utils;

import me.itswagpvp.parkourplugin.database.cache.Cache;
import me.itswagpvp.parkourplugin.database.cache.TopManager;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class ScoreboardUtils {

    private static final HashMap<Player, Boolean> hasScoreboardEnabled = new HashMap<>();

    public static void showScoreboard(Player p) {
        if (hasScoreboardEnabled.containsKey(p)) return;
        hasScoreboardEnabled.put(p, true);
        Scoreboard scoreboard = ScoreboardLib.createScoreboard(p)
                .setHandler(new ScoreboardHandler() {

                    @Override
                    public String getTitle(Player player) {
                        return "§a§lParkour";
                    }

                    @Override
                    public List<Entry> getEntries(Player player) {
                        TopManager top = new TopManager();
                        return new EntryBuilder()
                                .blank()
                                .next("§7Best Attempt: §c" + Cache.getValue(p))
                                .blank()
                                .next("§7Leaderboard:")
                                .next("   §f#1 §8- §a" + top.getName(1) + "§8- §e" + top.getValue(1))
                                .next("   §f#2 §8- §a" + top.getName(2) + "§8- §e" + top.getValue(2))
                                .next("   §f#3 §8- §a" + top.getName(3) + "§8- §e" + top.getValue(3))
                                .next("   §f#4 §8- §a" + top.getName(4) + "§8- §e" + top.getValue(4))
                                .next("   §f#5 §8- §a" + top.getName(5) + "§8- §e" + top.getValue(5))
                                .build();
                    }

                })
                .setUpdateInterval(20L * 10L);
        scoreboard.activate();
    }
}
