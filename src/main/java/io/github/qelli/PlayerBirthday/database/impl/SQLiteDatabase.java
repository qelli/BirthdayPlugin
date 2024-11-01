package io.github.qelli.PlayerBirthday.database.impl;

import java.io.File;
import java.io.IOException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.database.PooledSQLDatabase;
import io.github.qelli.PlayerBirthday.database.models.ConnectionProperties;
import io.github.qelli.PlayerBirthday.database.models.SQLDatabaseType;

public final class SQLiteDatabase extends PooledSQLDatabase {
    
    private static final String FILE_NAME = "playerdata.db";

    private final String filePath;
    private final ConnectionProperties connectionProperties;

    public SQLiteDatabase(PlayerBirthday plugin, ConnectionProperties connectionProperties) {
        super(plugin);
        this.connectionProperties = connectionProperties;
        this.filePath = this.plugin.getDataFolder().getPath() + File.separator + FILE_NAME;
    }

    @Override
    public SQLDatabaseType getDatabaseType() {
        return SQLDatabaseType.SQLITE;
    }

    @Override
    public void connect() {

        this.createDBFile();

        final HikariConfig hikari = new HikariConfig();

        hikari.setPoolName("xprison-" + POOL_COUNTER.getAndIncrement());

        hikari.setDriverClassName("org.sqlite.JDBC");
        hikari.setJdbcUrl("jdbc:sqlite:" + this.filePath);

        hikari.setConnectionTimeout(connectionProperties.getConnectionTimeout());
        hikari.setIdleTimeout(connectionProperties.getIdleTimeout());
        hikari.setKeepaliveTime(connectionProperties.getKeepAliveTime());
        hikari.setMaxLifetime(connectionProperties.getMaxLifetime());
        hikari.setMinimumIdle(connectionProperties.getMinimumIdle());
        hikari.setMaximumPoolSize(1);
        hikari.setLeakDetectionThreshold(connectionProperties.getLeakDetectionThreshold());
        hikari.setConnectionTestQuery(connectionProperties.getTestQuery());

        this.hikari = new HikariDataSource(hikari);
    }

    private void createDBFile() {
        File dbFile = new File(this.filePath);
        try {
            dbFile.createNewFile();
        } catch (IOException e) {
            this.plugin.getLogger().warning(String.format("Unable to create %s", FILE_NAME));
            e.printStackTrace();
        }
    }

}
