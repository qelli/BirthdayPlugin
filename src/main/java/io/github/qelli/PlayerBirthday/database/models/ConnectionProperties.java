package io.github.qelli.PlayerBirthday.database.models;

import org.bukkit.configuration.file.FileConfiguration;

public class ConnectionProperties {
    private final long idleTimeout, maxLifetime, connectionTimeout, leakDetectionThreshold, keepAliveTime;
    private final int minimumIdle, maximumPoolSize;
    private final String testQuery, characterEncoding;

    public ConnectionProperties(long idleTimeout, long maxLifetime, long connectionTimeout, long leakDetectionThreshold,
            long keepAliveTime, int minimumIdle, int maximumPoolSize, String testQuery, String characterEncoding) {
        this.idleTimeout = idleTimeout;
        this.maxLifetime = maxLifetime;
        this.connectionTimeout = connectionTimeout;
        this.leakDetectionThreshold = leakDetectionThreshold;
        this.keepAliveTime = keepAliveTime;
        this.minimumIdle = minimumIdle;
        this.maximumPoolSize = maximumPoolSize;
        this.testQuery = testQuery;
        this.characterEncoding = characterEncoding;
    }

    public static ConnectionProperties fromConfig(FileConfiguration config) {

        String rootPath = "connection_properties.";

        long connectionTimeout = config.getLong(rootPath + "connection_timeout");
        long idleTimeout = config.getLong(rootPath + "idle_timeout");
        long keepAliveTime = config.getLong(rootPath + "keep_alive_time");
        long maxLifeTime = config.getLong(rootPath + "max_life_time");
        int minimumIdle = config.getInt(rootPath + "minimum_idle");
        int maximumPoolSize = config.getInt(rootPath + "maximum_pool_size");
        long leakDetectionThreshold = config.getLong(rootPath + "leak_detection_threshold");
        String characterEncoding = config.getString(rootPath + "character_encoding", "utf8");
        String testQuery = config.getString(rootPath + "connection_test_query");
        return new ConnectionProperties(idleTimeout, maxLifeTime, connectionTimeout, leakDetectionThreshold,
                keepAliveTime, minimumIdle, maximumPoolSize, testQuery, characterEncoding);
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public long getMaxLifetime() {
        return maxLifetime;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public long getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public String getTestQuery() {
        return testQuery;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }
}
