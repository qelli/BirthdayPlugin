package io.github.qelli.PlayerBirthday.utils;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public final class PlayerUtils {
    public static void sendColoredMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
