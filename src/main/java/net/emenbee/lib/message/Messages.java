package net.emenbee.lib.message;

import net.emenbee.lib.except.UtilInstantiationException;

public final class Messages {

    public static final String NOT_PLAYER = ChatUtil.color("&cYou must be a player!");

    private Messages() {
        throw new UtilInstantiationException();
    }

}
