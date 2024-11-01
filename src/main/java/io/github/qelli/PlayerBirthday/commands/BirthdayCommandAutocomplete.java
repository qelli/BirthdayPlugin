package io.github.qelli.PlayerBirthday.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import io.github.qelli.PlayerBirthday.PlayerBirthday;

public class BirthdayCommandAutocomplete implements TabCompleter {

    private PlayerBirthday plugin;

    public BirthdayCommandAutocomplete(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch(args.length) {
            case 1:
                return Arrays.asList("set", "disable");
            case 2:
                String action = args[0].toLowerCase();
                switch(action) {
                    case "set":
                        return plugin.getConfig().getStringList("date-formats");
                    case "disable":
                        return Arrays.asList();
                }
        }
        return Arrays.asList();
    }

}
