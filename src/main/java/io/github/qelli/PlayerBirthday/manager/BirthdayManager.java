package io.github.qelli.PlayerBirthday.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.entity.Player;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.utils.PlayerUtils;

public class BirthdayManager {
    private final PlayerBirthday plugin;

    public BirthdayManager(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    public void setPlayerBirthday(Player player, String rawDate) {
        boolean isValidated = !requiresValidation();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(plugin.getDateFormat());
            Date date = formatter.parse(rawDate);
            this.plugin.getRepository().savePlayerBirthday(player.getUniqueId(), date.toString(), isValidated);
        } catch(Exception e) {
            PlayerUtils.sendColoredMessage(player, plugin.getMessage("invalid_date_format"));
        }
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
