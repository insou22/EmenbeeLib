package net.emenbee.lib.player;

import net.emenbee.lib.except.UtilInstantiationException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Senders {

    private Senders() {
        throw new UtilInstantiationException();
    }

    public static void send(CommandSender sender, String message) {
        if (sender == null || message == null) return;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
