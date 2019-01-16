package dk.rasmusbendix.bendixcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

class CustomConfig {

    private JavaPlugin plugin;
    private String filename;

    private File configFile;
    private FileConfiguration config;

    public CustomConfig(JavaPlugin plugin, String filename, String folderName) {
        this.plugin = plugin;

        // Make sure file has .yml
        if(!filename.endsWith(".yml"))
            filename = filename + ".yml";

        this.filename = filename;

        File df = plugin.getDataFolder();
        if(!df.exists()) {
            plugin.getLogger().info("Creating directories " + df.getName());
            df.mkdirs();
        }

        if(!folderName.equals("")) {

            File folder = new File(df.toString() + File.separatorChar + folderName);
            if(!folder.exists()) {
                if(!folder.mkdirs())
                    plugin.getLogger().warning("Failed to make directory: " + folderName);
            }

            this.configFile = new File(df.toString() + File.separatorChar + folderName + File.separatorChar + this.filename);

            try {

                if(configFile.createNewFile())
                    plugin.getLogger().info("Created config file.");

            } catch(IOException e) {
                plugin.getLogger().info("Failed to create config file.");
                e.printStackTrace();
            }

        } else {
            this.configFile = new File(df, this.filename);
        }

        try {
            try {
                saveDefaultConfig();
                plugin.getLogger().info("Saved default config for " + filename);
            } catch(Exception e) {
                if (this.configFile.createNewFile())
                    plugin.getLogger().info("Created new file named " + filename);
                else
                    plugin.getLogger().warning("Failed to create " + filename);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public CustomConfig(JavaPlugin plugin, String filename) {
        this(plugin, filename, "");
    }



    public void reloadConfig() {

        Reader defConfigStream = null;

        try {

            this.config = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(this.configFile), "UTF-8"));

            if(plugin.getResource(this.filename) != null)
                defConfigStream = new InputStreamReader(plugin.getResource(this.filename), "UTF-8");

        } catch (UnsupportedEncodingException | FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }

        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.config.setDefaults(defConfig);
        }

    }

    public FileConfiguration getConfig() {

        if (this.config == null) {
            reloadConfig();
        }

        return this.config;

    }


    public void set(String path, Object o) {
        this.getConfig().set(path, o);
    }


    public boolean contains(String path) {
        return this.getConfig().contains(path);
    }


    public String getString(String path) {
        return this.getConfig().getString(path);
    }

    public String getString(String path, String def) {
        return this.getConfig().contains(path) ? getString(path) : def;
    }

    public int getInt(String path) {
        return this.getConfig().getInt(path);
    }

    public int getInt(String path, int def) {
        return this.getConfig().contains(path) ? getInt(path) : def;
    }

    public long getLong(String path) {
        return this.getConfig().getLong(path);
    }

    public long getLong(String path, long def) {
        return this.getConfig().contains(path) ? getLong(path) : def;
    }


    public void saveConfig() {

        if ((this.config == null) || (this.configFile == null)) {
            return;
        }

        try {
            getConfig().save(this.configFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't save config to " + this.configFile, ex);
        }
    }


    public void saveDefaultConfig() throws Exception {

        if (!this.configFile.exists()) {
            plugin.saveResource(this.filename, false);
        }

    }

}