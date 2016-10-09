package net.emenbee.lib.reflection;

public interface FieldAccessor<T> {

    T get(Object instance);

    void set(Object instance, Object value);

    boolean containedIn(Object instance);

}
