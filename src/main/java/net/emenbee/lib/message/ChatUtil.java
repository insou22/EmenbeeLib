package net.emenbee.lib.message;

import net.emenbee.lib.except.UtilInstantiationException;
import org.bukkit.ChatColor;

public final class ChatUtil {

    private ChatUtil() {
        throw new UtilInstantiationException();
    }

    public static String color(String text) {
        return text == null ? "" : ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String strip(String text) {
        return text == null ? "" : ChatColor.stripColor(text);
    }

    public static boolean equalsNoColor(String text1, String text2) {
        return ChatUtil.strip(text1).equals(ChatUtil.strip(text2));
    }

}
