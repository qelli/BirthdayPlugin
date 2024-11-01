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

        for (String s : this.plugin.getMessages("happy_birthday.message")) {
            Bukkit.broadcastMessage(s.replace("{player}", p.getName()));
        }

        String command = "give " + p.getName() + " cake 1";
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}
