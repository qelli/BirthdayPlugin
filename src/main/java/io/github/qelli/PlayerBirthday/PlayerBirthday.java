package io.github.qelli.PlayerBirthday;

import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.qelli.PlayerBirthday.commands.BirthdayCommand;
import io.github.qelli.PlayerBirthday.commands.BirthdayCommandAutocomplete;
import io.github.qelli.PlayerBirthday.config.Configuration;
import io.github.qelli.PlayerBirthday.database.SQLDatabase;
import io.github.qelli.PlayerBirthday.database.impl.SQLiteDatabase;
import io.github.qelli.PlayerBirthday.database.models.ConnectionProperties;
import io.github.qelli.PlayerBirthday.listeners.CheckPlayerBirthdayOnJoin;
import io.github.qelli.PlayerBirthday.manager.BirthdayManager;
import io.github.qelli.PlayerBirthday.repo.BirthdayRepository;
import io.github.qelli.PlayerBirthday.repo.BirthdayRepositoryImpl;

public class PlayerBirthday extends JavaPlugin {

    private SQLDatabase database;
    private BirthdayRepository repository;
    private BirthdayManager manager;
    private Configuration messages;
    private String dateFormat;

    @Override
    public void onEnable() {
        try{
            if (!getDataFolder().exists()){
                getDataFolder().mkdirs();
                saveDefaultConfig();
            }

            if(!this.initDatabase()) {
                getLogger().severe("Failed to initialize database.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            this.dateFormat = getConfig().getStringList("date-formats").get(0);

            this.messages = new Configuration(this, "messages.yml");

            this.repository = new BirthdayRepositoryImpl(this.database);
            this.repository.createTables();

            this.manager = new BirthdayManager(this);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to initialize! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        this.getCommand(BirthdayCommand.COMMAND_NAME).setExecutor(new BirthdayCommand(this));
        this.getCommand(BirthdayCommand.COMMAND_NAME).setTabCompleter(new BirthdayCommandAutocomplete(this));

        getServer().getPluginManager().registerEvents(new CheckPlayerBirthdayOnJoin(this), this);

        getLogger().info("Plugin successfully enabled!");
    }
    @Override
    public void onDisable() {
        getLogger().info("PlayerBirthday has been disabled!");
        database.close();
    }

    public BirthdayManager getManager() {
        return this.manager;
    }

    public BirthdayRepository getRepository() {
        return this.repository;
    }

    public boolean isDebugMode() {
        return getConfig().getBoolean("debug-mode");
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public String getMessage(String key) {
        return this.messages.getConfig().getString(key);
    }

    public List<String> getMessages(String key) {
        return this.messages.getConfig().getStringList(key);
    }

    private boolean initDatabase() {
        String type = getConfig().getString("database-type");
        ConnectionProperties properties = ConnectionProperties.fromConfig(getConfig());

        try {
            switch(type) {
                case "mysql":
                    // this.database = new MySQL(properties);
                    break;
                case "sqlite":
                    this.database = new SQLiteDatabase(this, properties);
                    break;
                default:
                    getLogger().severe("Invalid database.type specified in config.yml!");
                    return false;
            }
            this.database.connect();
            return true;
        } catch(Exception e) {
            getLogger().severe("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}
