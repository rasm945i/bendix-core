package dk.rasmusbendix.bendixcore;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BendixCore extends JavaPlugin {

    private static HashMap<String, HashMap<String, CustomConfig>> configMap = new HashMap<>();

    // Im starting to doubt if this even is useful.

    @Override
    public void onEnable() {
        // Each plugin makes sure to load their own configs when they need to access them.
    }

    @Override
    public void onDisable() {
        saveAllConfigs();
    }

    private void saveAllConfigs() {
        for(String key : configMap.keySet()) {

            HashMap<String, CustomConfig> confs = configMap.get(key);
            for(String confName : confs.keySet()) {

                confs.get(confName).saveConfig();

            }

        }
    }


    // All "create" or "load" config methods are essentially the same.
    // They have different names for human purposes.
    public static CustomConfig loadConfig(JavaPlugin plugin, String filename) {
        return createConfig(plugin, filename);
    }

    public static CustomConfig loadConfig(JavaPlugin plugin, String filename, String folder) {
        return createConfig(plugin, filename, folder);
    }

    public static CustomConfig createConfig(JavaPlugin plugin, String filename) {
        return createConfig(plugin, filename, "");
    }

    public static CustomConfig createConfig(JavaPlugin plugin, String filename, String folder) {

        CustomConfig config = new CustomConfig(plugin, filename, folder);
        HashMap<String, CustomConfig> all = getConfigs(plugin);
        all.put(filename, config);
        configMap.put(plugin.getName(), all);

        return config;

    }

    public static HashMap<String, CustomConfig> getConfigs(JavaPlugin plugin) {
        return configMap.getOrDefault(plugin.getName(), new HashMap<>());
    }

}
