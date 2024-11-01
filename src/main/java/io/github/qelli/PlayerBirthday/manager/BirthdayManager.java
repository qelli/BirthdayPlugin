package io.github.qelli.PlayerBirthday.manager;

import java.util.UUID;

import io.github.qelli.PlayerBirthday.PlayerBirthday;

public class BirthdayManager {
    private final PlayerBirthday plugin;

    public BirthdayManager(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    public void setPlayerBirthday(UUID uuid, String birthday) {
        boolean isValidated = !requiresValidation();
        this.plugin.getRepository().savePlayerBirthday(uuid, birthday, isValidated);
    }

    public String getPlayerBirthday(UUID uuid) {
        return this.plugin.getRepository().getPlayerBirthday(uuid);
    }

    public void disablePlayerBirthday(UUID uuid) {
        this.plugin.getRepository().togglePlayerBirthday(uuid, false);
    }

    public void enablePlayerBirthday(UUID uuid) {
        this.plugin.getRepository().togglePlayerBirthday(uuid, true);
    }

    public boolean requiresValidation() {
        return plugin.getConfig().getBoolean("require-validation");
    }
}
