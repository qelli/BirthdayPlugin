package io.github.qelli.PlayerBirthday.database.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.database.PooledSQLDatabase;
import io.github.qelli.PlayerBirthday.database.models.ConnectionProperties;
import io.github.qelli.PlayerBirthday.database.models.Credentials;
import io.github.qelli.PlayerBirthday.database.models.SQLDatabaseType;

public final class MySQLDatabase extends PooledSQLDatabase {

    private final Credentials credentials;
    private final ConnectionProperties connectionProperties;

    public MySQLDatabase(PlayerBirthday parent, Credentials credentials, ConnectionProperties connectionProperties) {
        super(parent);
        this.connectionProperties = connectionProperties;
        this.credentials = credentials;
    }

    @Override
    public void connect() {
        final HikariConfig hikari = new HikariConfig();

        hikari.setPoolName("xprison-" + POOL_COUNTER.getAndIncrement());

        this.applyCredentials(hikari, credentials, connectionProperties);
        this.applyConnectionProperties(hikari, connectionProperties);
        this.addDefaultDataSourceProperties(hikari);
        this.hikari = new HikariDataSource(hikari);
    }

    private void applyCredentials(HikariConfig hikari, Credentials credentials, ConnectionProperties connectionProperties) {
        hikari.setJdbcUrl("jdbc:mysql://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDatabaseName() + "?characterEncoding=" + connectionProperties.getCharacterEncoding());
        hikari.setUsername(credentials.getUserName());
        hikari.setPassword(credentials.getPassword());
    }

    private void applyConnectionProperties(HikariConfig hikari, ConnectionProperties connectionProperties) {
        hikari.setConnectionTimeout(connectionProperties.getConnectionTimeout());
        hikari.setIdleTimeout(connectionProperties.getIdleTimeout());
        hikari.setKeepaliveTime(connectionProperties.getKeepAliveTime());
        hikari.setMaxLifetime(connectionProperties.getMaxLifetime());
        hikari.setMinimumIdle(connectionProperties.getMinimumIdle());
        hikari.setMaximumPoolSize(connectionProperties.getMaximumPoolSize());
        hikari.setLeakDetectionThreshold(connectionProperties.getLeakDetectionThreshold());
        hikari.setConnectionTestQuery(connectionProperties.getTestQuery());
    }

    private void addDefaultDataSourceProperties(HikariConfig hikari) {
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikari.addDataSourceProperty("useServerPrepStmts", true);
        hikari.addDataSourceProperty("useLocalSessionState", true);
        hikari.addDataSourceProperty("rewriteBatchedStatements", true);
        hikari.addDataSourceProperty("cacheResultSetMetadata", true);
        hikari.addDataSourceProperty("cacheServerConfiguration", true);
        hikari.addDataSourceProperty("elideSetAutoCommits", true);
        hikari.addDataSourceProperty("maintainTimeStats", false);
    }

    @Override
    public SQLDatabaseType getDatabaseType() {
        return SQLDatabaseType.MYSQL;
    }
    
}
