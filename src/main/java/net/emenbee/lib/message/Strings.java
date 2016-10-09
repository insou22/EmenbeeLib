package net.emenbee.lib.message;

import net.emenbee.lib.except.UtilInstantiationException;

public final class Strings {

    private Strings() {
        throw new UtilInstantiationException();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static String format(String string, Object... args) {
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (!string.contains("{" + i + "}")) {
                string = string + ", " + String.valueOf(arg);
            } else {
                string = string.replace("{" + i + "}", String.valueOf(arg));
            }
        }
        return string;
    }

}
