package io.github.qelli.PlayerBirthday.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.qelli.PlayerBirthday.PlayerBirthday;

public class Configuration {
    
    private File file;
    private FileConfiguration config;

    public Configuration(PlayerBirthday plugin, String path) {
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
    }

    public Configuration(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

}
