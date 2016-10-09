package net.emenbee.lib.except;

@FunctionalInterface
public interface SneakySupplier<T> {

    T supply() throws Exception;

}
