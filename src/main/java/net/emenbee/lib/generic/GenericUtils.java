package net.emenbee.lib.generic;

import net.emenbee.lib.except.UtilInstantiationException;

@SuppressWarnings("unchecked")
public class GenericUtils {

    private GenericUtils() {
        throw new UtilInstantiationException();
    }

    public static <T> T cast(Object object) {
        return (T) object;
    }

    public static <T> boolean castable(Object object, Class<T> clazz) {
        try {
            T t = (T) object;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public static <T> T cast(Object object, Class<T> clazz) {
        return GenericUtils.cast(object);
    }

}
