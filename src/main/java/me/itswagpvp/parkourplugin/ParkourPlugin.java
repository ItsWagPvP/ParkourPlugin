package me.itswagpvp.parkourplugin;

import me.itswagpvp.parkourplugin.database.MySQL;
import me.itswagpvp.parkourplugin.database.cache.Cache;
import me.itswagpvp.parkourplugin.database.cache.TopManager;
import me.itswagpvp.parkourplugin.listener.ParkourHandler;
import me.itswagpvp.parkourplugin.listener.PlayerHandler;
import me.itswagpvp.parkourplugin.listener.ScoreboardHandler;
import me.itswagpvp.parkourplugin.utils.Checkpoints;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ParkourPlugin extends JavaPlugin {

    private static ParkourPlugin plugin;

    public static ParkourPlugin getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();
        new Checkpoints().createCheckpointsFile();
        Checkpoints.loadCheckpoints();

        new MySQL(plugin).load();
        new TopManager().updateTop();
        ScoreboardLib.setPluginInstance(plugin);

        Bukkit.getPluginManager().registerEvents(new ParkourHandler(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), plugin);
        Bukkit.getPluginManager().registerEvents(new ScoreboardHandler(), plugin);
        Bukkit.getPluginManager().registerEvents(new Cache(), plugin);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
