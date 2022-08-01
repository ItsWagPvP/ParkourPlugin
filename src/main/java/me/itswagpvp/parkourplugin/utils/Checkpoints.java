package me.itswagpvp.parkourplugin.utils;

import com.google.common.collect.Iterables;
import me.itswagpvp.parkourplugin.ParkourPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Checkpoints {
    private static final ParkourPlugin plugin = ParkourPlugin.getInstance();

    private static final List<Location> checkpoints = new ArrayList<>();

    public static List<Location> getCheckpoints() {
        return checkpoints;
    }

    public static void loadCheckpoints() {
        checkpoints.clear();
        JSONArray array = JsonUtils.getCheckpoints().getJSONArray("checkpointsData");
        for (Object o : array) {
            JSONObject jsonLineItem = (JSONObject) o;

            World w = Bukkit.getWorld(jsonLineItem.get("worldName").toString());
            int x = Integer.parseInt(jsonLineItem.get("x").toString());
            int y = Integer.parseInt(jsonLineItem.get("y").toString());
            int z = Integer.parseInt(jsonLineItem.get("z").toString());
            checkpoints.add(new Location(w, x, y, z));
        }
    }

    public static Location getStart() {
        return getCheckpoints().get(0);
    }

    public static Location getEnd() {
        return Iterables.getLast(getCheckpoints());
    }

    public File getFile() {
        return new File(plugin.getDataFolder(), "checkpoints.json");
    }

    public void createCheckpointsFile() {
        File checkpointsConfigFile = new File(plugin.getDataFolder(), "checkpoints.json");
        if (!checkpointsConfigFile.exists()) {
            checkpointsConfigFile.getParentFile().mkdirs();
            plugin.saveResource("checkpoints.json", false);
        }
    }

}
