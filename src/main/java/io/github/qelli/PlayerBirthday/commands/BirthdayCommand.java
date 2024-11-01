package io.github.qelli.PlayerBirthday.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.utils.PlayerUtils;

public class BirthdayCommand implements CommandExecutor {

    public static final String COMMAND_NAME = "birthday";
    
    private final PlayerBirthday plugin;

    public BirthdayCommand(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_executer"));
            return true;
        }
        if(!sender.hasPermission("qelliplayerbirthday.setbirthday")) {
            PlayerUtils.sendColoredMessage(sender, plugin.getMessage("no_permission"));
            return true;
        }
        if(args.length < 1) {
            PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_usage") + getUsage());
            return true;
        }

        // args[0] can be "set" or "disable"
        switch(args[0].toLowerCase()) {
            case "set":
                if(args.length != 2) {
                    break;
                }
                return setBirthday((Player) sender, args[1], "MX");
            case "enable":
                return toggleBroadcast((Player) sender, true);
            case "disable":
                return toggleBroadcast((Player) sender, false);
        }
        PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_usage") + getUsage());
        return true;
    }

    private String getUsage() {
        return "/birthday <set/disable> <" + plugin.getDateFormat() + ">";
    }

    private boolean setBirthday(Player player, String rawDate, String timezone) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(plugin.getDateFormat());
            Date date = formatter.parse(rawDate);
            plugin.getManager().setPlayerBirthday(player.getUniqueId(), date.toString());
            // date.
            return true;
        } catch(Exception e) {
            // PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_date_format") + getUsage());
            return false;
        }
    }

    private boolean toggleBroadcast(Player player, boolean enable) {
        if(enable) {
            plugin.getManager().enablePlayerBirthday(player.getUniqueId());
        } else {
            plugin.getManager().disablePlayerBirthday(player.getUniqueId());
        }
        return true;
    }

}
