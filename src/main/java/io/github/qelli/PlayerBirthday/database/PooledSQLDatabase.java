package io.github.qelli.PlayerBirthday.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.zaxxer.hikari.HikariDataSource;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.database.models.SQLDatabaseType;

public class PooledSQLDatabase extends SQLDatabase {

    protected static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);
    protected HikariDataSource hikari;

    public PooledSQLDatabase(PlayerBirthday parent) {
        super(parent);
    }

    @Override
    public void connect() {}

    @Override
    public void close() {
        if(this.hikari != null) {
            this.hikari.close();
            this.plugin.getLogger().info("Database connection closed.");
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return this.hikari.getConnection();
        } catch(SQLException e) {
            this.plugin.getLogger().severe("Failed to get connection: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SQLDatabaseType getDatabaseType() {
        return SQLDatabaseType.SQLITE;
    }
    
}
