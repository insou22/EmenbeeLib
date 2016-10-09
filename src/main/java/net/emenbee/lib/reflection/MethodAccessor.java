package net.emenbee.lib.reflection;

@FunctionalInterface
public interface MethodAccessor<T> {

    T call(Object target, Object... args);

}
