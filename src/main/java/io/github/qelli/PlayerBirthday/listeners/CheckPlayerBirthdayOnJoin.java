package io.github.qelli.PlayerBirthday.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.qelli.PlayerBirthday.PlayerBirthday;

public class CheckPlayerBirthdayOnJoin implements Listener {

    private PlayerBirthday plugin;

    public CheckPlayerBirthdayOnJoin(PlayerBirthday plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if(!this.plugin.getConfig().getBoolean("happy-birthday-broadcast.enabled")) {
            return;
        }

        Player p = event.getPlayer();

        // Check if player birthday is today
        // Check if player hasn't been greeted this year

        for (String s : this.plugin.getMessages("happy_birthday.message")) {
            Bukkit.broadcastMessage(s.replace("%player%", p.getName()));
        }

        for (String cmd : this.plugin.getConfig().getStringList("happy-birthday-broadcast.rewards")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", p.getName()));
        }

    }
}
