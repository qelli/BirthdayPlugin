package io.github.qelli.PlayerBirthday.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.utils.Constants;
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
        if(!sender.hasPermission(Constants.COMMAND_PERM) || !sender.hasPermission(Constants.ADMIN_COMMAND_PERM)) {
            PlayerUtils.sendColoredMessage(sender, plugin.getMessage("no_permission"));
            return true;
        }
        if(args.length < 1) {
            PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_usage") + getUsage());
            return true;
        }

        Player player = (Player) sender;

        switch(args[0].toLowerCase()) {
            case "set":
                if(args.length != 2) { // Why tf did I add this?
                    break;
                }
                // TODO: Check if player has already set their birthday
                plugin.getManager().setPlayerBirthday(player, args[1]);
                break;
            case "enable":
                plugin.getManager().enablePlayerBirthday(player.getUniqueId());
                break;
            case "disable":
                plugin.getManager().disablePlayerBirthday(player.getUniqueId());
                break;
            case "view":
                if(!sender.hasPermission(Constants.ADMIN_COMMAND_PERM)) {
                    PlayerUtils.sendColoredMessage(sender, plugin.getMessage("no_permission"));
                    return true;
                }
                // TODO: Show player birthday
                break;
            case "approve":
                if(!sender.hasPermission(Constants.ADMIN_COMMAND_PERM)) {
                    PlayerUtils.sendColoredMessage(sender, plugin.getMessage("no_permission"));
                    return true;
                }
                // TODO: Approve player birthday
            default:
                PlayerUtils.sendColoredMessage(sender, plugin.getMessage("invalid_usage") + getUsage());
        }
        return true;
    }

    private String getUsage() {
        return "/birthday <set/disable> <" + plugin.getDateFormat() + ">";
    }

}
