package me.itswagpvp.parkourplugin.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class JsonUtils {
    public static JSONObject getCheckpoints() {
        try {
            File file = new Checkpoints().getFile();
            FileInputStream fis;
            byte[] data;
            fis = new FileInputStream(file);
            data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, StandardCharsets.UTF_8);
            return new JSONObject(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
