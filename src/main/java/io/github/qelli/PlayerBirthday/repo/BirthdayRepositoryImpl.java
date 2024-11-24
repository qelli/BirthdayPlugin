package io.github.qelli.PlayerBirthday.repo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import io.github.qelli.PlayerBirthday.database.SQLDatabase;

public class BirthdayRepositoryImpl implements BirthdayRepository {

    private static final String TABLE_NAME = "PlayerBirthday_Birthdays";
    private static final String UUID_COLNAME = "UUID";
    private static final String BIRTHDAY_COLNAME = "birthday";
    private static final String IS_ENABLED_COLNAME = "is_enabled";
    private static final String IS_VALIDATED_COLNAME = "is_validated";
    private static final String LAST_BROADCAST_COLNAME = "last_broadcast";

    private final SQLDatabase database;

    public BirthdayRepositoryImpl(SQLDatabase database) {
        this.database = database;
    }

    @Override
    public void savePlayerBirthday(UUID uuid, String birthday, boolean isValidated) {
        this.database.executeSql(
            new StringBuilder()
                .append("INSERT INTO " + TABLE_NAME + " VALUES(?,?,?)")
            .toString(),
            uuid.toString(), birthday, isValidated
        );
    }

    @Override
    public String getPlayerBirthday(UUID uuid) {
        try (Connection con = this.database.getConnection(); PreparedStatement statement = database.prepareStatement(con,"SELECT * FROM " + TABLE_NAME + " WHERE " + UUID_COLNAME + "=? AND " + IS_VALIDATED_COLNAME + "=?")) {
			statement.setString(1, uuid.toString());
            statement.setBoolean(2, true);
			try (ResultSet set = statement.executeQuery()) {
				if (set.next()) {
					return set.getString(BIRTHDAY_COLNAME);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return null;
    }

    @Override
    public void createTables() {
        this.database.executeSql(
            new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " (")
                .append(UUID_COLNAME + " varchar(36) NOT NULL UNIQUE, ")
                .append(BIRTHDAY_COLNAME + " DATE NOT NULL, ")
                .append(IS_VALIDATED_COLNAME + " bool NOT NULL, ")
                .append(LAST_BROADCAST_COLNAME + " DATE NOT NULL, ")
                .append(IS_ENABLED_COLNAME + " bool NOT NULL, ")
                .append("PRIMARY KEY(" + UUID_COLNAME + "));")
            .toString()
        );
    }

    public void togglePlayerBirthday(UUID uuid, boolean isEnabled) {
        this.database.executeSql(
            new StringBuilder()
                .append("UPDATE " + TABLE_NAME + " SET " + IS_ENABLED_COLNAME + "=? WHERE " + UUID_COLNAME + "=?")
            .toString(),
            isEnabled, uuid.toString()
        );
    }

    @Override
    public void clearTableData() {
        // this.database.executeSqlAsync("DELETE FROM " + TABLE_NAME);
    }
    
}
