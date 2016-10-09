package net.emenbee.lib.reflection;

public interface ConstructorAccessor<T> {

    T create(Object... args);

    Class<T> getAccessingClass();

    Class<?>[] getParams();

}
