package me.itswagpvp.parkourplugin;

import me.itswagpvp.parkourplugin.utils.Checkpoints;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class ParkourPlugin extends JavaPlugin {

    private static ParkourPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();
        new Checkpoints().createCheckpointsFile();

        ScoreboardLib.setPluginInstance(plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ParkourPlugin getInstance() {
        return plugin;
    }
}
