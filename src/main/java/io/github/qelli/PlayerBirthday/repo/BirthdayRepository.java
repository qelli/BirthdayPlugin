package io.github.qelli.PlayerBirthday.repo;

import java.util.UUID;

public interface BirthdayRepository {
    void savePlayerBirthday(UUID uuid, String date, boolean isValidated);

    String getPlayerBirthday(UUID uuid);

    void togglePlayerBirthday(UUID uuid, boolean isEnabled);

	void createTables();

	void clearTableData();
}
