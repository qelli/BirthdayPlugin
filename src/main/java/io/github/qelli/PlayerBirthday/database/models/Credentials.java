package io.github.qelli.PlayerBirthday.database.models;

import org.bukkit.configuration.file.FileConfiguration;
// import org.apache.commons.lang.Validate;

public class Credentials {

    private final String host, databaseName, userName, password;
    private final int port;

    public Credentials(String host, String databaseName, String userName, String password, int port) {
        this.host = host;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    public static Credentials fromConfig(FileConfiguration config) {

        String rootPath = "mysql.";

        String host = config.getString(rootPath + "host");
        String dbName = config.getString(rootPath + "database");
        String userName = config.getString(rootPath + "username");
        String password = config.getString(rootPath + "password");
        int port = config.getInt(rootPath + "port");

        // Validate.notNull(host);
        // Validate.notNull(dbName);
        // Validate.notNull(userName);
        // Validate.notNull(password);

        return new Credentials(host, dbName, userName, password, port);
    }

    public String getHost() {
        return host;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }
}
