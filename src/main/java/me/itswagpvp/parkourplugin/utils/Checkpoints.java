package me.itswagpvp.parkourplugin.utils;

import me.itswagpvp.parkourplugin.ParkourPlugin;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Checkpoints {
    private FileConfiguration checkpointsConfig;
    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    public FileConfiguration getCheckpointsFile() {
        return this.checkpointsConfig;
    }

    public void createCheckpointsFile() {
        File checkpointsConfigFile = new File(plugin.getDataFolder(), "checkpoints.json");
        if (!checkpointsConfigFile.exists()) {
            checkpointsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("checkpoints.json", false);
        }

        checkpointsConfig = new YamlConfiguration();
        try {
            checkpointsConfig.load(checkpointsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
