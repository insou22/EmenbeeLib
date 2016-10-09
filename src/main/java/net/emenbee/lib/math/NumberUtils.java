package net.emenbee.lib.math;

import net.emenbee.lib.except.UtilInstantiationException;
import net.emenbee.lib.generic.GenericUtils;

public final class NumberUtils {

    private NumberUtils() {
        throw new UtilInstantiationException();
    }

    public static boolean parseable(Class<? extends Number> format, String string) {
        return NumberUtils.parse(format, string) != null;
    }

    public static <T extends Number> T parse(Class<T> format, String string) {
        if (format == null || string == null) {
            return null;
        }
        try {
            if (format.equals(Integer.class)) {
                return GenericUtils.cast(Integer.parseInt(string));
            }
            if (format.equals(Double.class)) {
                return GenericUtils.cast(Double.parseDouble(string));
            }
            if (format.equals(Long.class)) {
                return GenericUtils.cast(Long.parseLong(string));
            }
            if (format.equals(Float.class)) {
                return GenericUtils.cast(Float.parseFloat(string));
            }
            if (format.equals(Short.class)) {
                return GenericUtils.cast(Short.parseShort(string));
            }
            if (format.equals(Byte.class)) {
                return GenericUtils.cast(Byte.parseByte(string));
            }
        } catch (NumberFormatException ignored) {}
        return null;
    }

    public static int floor(double x) {
        return (int) Math.floor(x);
    }

    public static int ceil(double x) {
        return (int) Math.ceil(x);
    }

}
