package net.emenbee.lib.player;

import net.emenbee.lib.except.UtilInstantiationException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

public class Players {

    private Players() {
        throw new UtilInstantiationException();
    }

    public static Player get(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public static Player get(String name) {
        return Bukkit.getPlayer(name);
    }

    public static boolean isOnline(UUID uuid) {
        return Players.get(uuid) != null;
    }

    public static boolean isOnline(String name) {
        return Players.get(name) != null;
    }

    public static Collection<? extends Player> online() {
        return Bukkit.getOnlinePlayers();
    }

    public static Stream<? extends Player> streamOnline() {
        return Players.online().stream();
    }

    public static void send(Player player, String message) {
        if (player == null || message == null) return;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
