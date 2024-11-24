package io.github.qelli.PlayerBirthday.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import io.github.qelli.PlayerBirthday.PlayerBirthday;
import io.github.qelli.PlayerBirthday.utils.Constants;

public class BirthdayCommandAutocomplete implements TabCompleter {

    private PlayerBirthday plugin;

    public BirthdayCommandAutocomplete(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch(args.length) {
            case 1:
                List<String> actions = Arrays.asList("set", "disable");
                if(sender.hasPermission(Constants.ADMIN_COMMAND_PERM)) {
                    actions.add("approve");
                    actions.add("view");
                }
                return actions;
            case 2:
                String action = args[0].toLowerCase();
                switch(action) {
                    case "set":
                        return plugin.getConfig().getStringList("date-formats");
                    case "disable":
                        return Arrays.asList();
                    case "approve":
                    case "view":
                        if(!sender.hasPermission(Constants.ADMIN_COMMAND_PERM)) {
                            return Arrays.asList();
                        }
                        return plugin.getServer().getOnlinePlayers().stream()
                                .map(player -> player.getName())
                                .collect(Collectors.toList());
                }
        }
        return Arrays.asList();
    }

}
