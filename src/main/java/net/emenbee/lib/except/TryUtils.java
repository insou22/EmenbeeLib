package net.emenbee.lib.except;

public final class TryUtils {

    private TryUtils() {
        throw new UtilInstantiationException();
    }

    public static void sneaky(SneakyRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T sneaky(SneakySupplier<T> supplier) {
        try {
            return supplier.supply();
        } catch (Exception e) {
            throw new SneakyException(e);
        }
    }

    private static final class SneakyException extends RuntimeException {

        public SneakyException(Exception exception) {
            super(exception);
        }

    }

}
