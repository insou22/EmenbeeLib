package net.emenbee.lib.except;

import java.util.Collection;

public final class Assert {

    private Assert() {
        throw new UtilInstantiationException();
    }

    public static <T> T notNull(T object) {
        if (object == null) {
            throw new AssertionException("The asserted object was null");
        }
        return object;
    }

    public static <T extends Collection> T notEmpty(T collection) {
        if (collection.isEmpty()) {
            throw new AssertionException("The asserted collection was empty");
        }
        return collection;
    }

    public static final class AssertionException extends RuntimeException {

        private AssertionException() {
            super();
        }

        private AssertionException(String message) {
            super(message);
        }

    }

}
