package net.emenbee.lib.reflection;

import net.emenbee.lib.except.UtilInstantiationException;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class Reflection {

    private static final String OBC_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();
    private static final String NMS_PACKAGE = OBC_PACKAGE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static final String VERSION = OBC_PACKAGE.replace("org.bukkit.craftbukkit", "").replace(".", "");

    public static String getVersion() {
        return Reflection.VERSION;
    }

    public static FieldAccessor<?> getField(String className, String fieldName) {
        return Reflection.getField(className, fieldName, Object.class);
    }

    public static FieldAccessor getField(String className, String fieldName, String fieldClassName) {
        return Reflection.getField(className, fieldName, Reflection.getClass(fieldClassName));
    }

    public static <T> FieldAccessor<T> getField(String className, String fieldName, Class<T> fieldType) {
        return Reflection.getField(Reflection.getClass(className), fieldName, fieldType);
    }

    public static <T> FieldAccessor<T> getField(Class<?> clazz, String fieldName, Class<T> fieldType) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName) && fieldType.isAssignableFrom(field.getType())) {
                field.setAccessible(true);

                return new FieldAccessor<T>() {

                    @Override
                    public T get(Object instance) {
                        try {
                            return (T) field.get(instance);
                        } catch (IllegalAccessException e) {
                            throw new ReflectionException.FieldInaccessibleException(clazz, fieldName);
                        }
                    }

                    @Override
                    public void set(Object instance, Object value) {
                        try {
                            field.set(instance, value);
                        } catch (IllegalAccessException e) {
                            throw new ReflectionException.FieldInaccessibleException(clazz, fieldName);
                        }
                    }

                    @Override
                    public boolean containedIn(Object instance) {
                        return field.getDeclaringClass().isAssignableFrom(instance.getClass());
                    }

                };
            }
        }

        if (clazz.getSuperclass() != null) {
            return Reflection.getField(clazz.getSuperclass(), fieldName, fieldType);
        }

        throw new ReflectionException.FieldNotFoundException(clazz, fieldName);
    }

    public static <T> MethodAccessor<T> getMethod(String className, String methodName, Class<?>... params) {
        return Reflection.getMethod(Reflection.getClass(className), methodName, null, params);
    }

    public static <T> MethodAccessor<T> getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        return Reflection.getMethod(clazz, methodName, null, params);
    }

    public static <T> MethodAccessor<T> getMethod(String className, String methodName, Class<T> returnType, Class<?>... params) {
        return Reflection.getMethod(Reflection.getClass(className), methodName, returnType, params);
    }

    public static <T> MethodAccessor<T> getMethod(Class<?> clazz, String methodName, Class<T> returnType, Class<?>... params) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (methodName.equals(method.getName()) && (returnType == null || method.getReturnType().equals(returnType)) && Arrays.equals(method.getParameterTypes(), params)) {
                method.setAccessible(true);

                return (target, args) -> {
                    try {
                        return (T) method.invoke(target, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new ReflectionException.MethodInaccessibleException(clazz, methodName, params);
                    }
                };
            }
        }

        if (clazz.getSuperclass() != null) {
            return Reflection.getMethod(clazz.getSuperclass(), methodName, returnType, params);
        }

        throw new ReflectionException.MethodNotFoundException(clazz, methodName, params);
    }

    public static <T> ConstructorAccessor<T> getConstructor(Class<T> clazz, Class<?>... params) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);

                return new ConstructorAccessor<T>() {
                    @Override
                    public T create(Object... args) {
                        try {
                            return (T) constructor.newInstance(args);
                        } catch (java.lang.InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new ReflectionException.ConstructorInaccessibleException(clazz);
                        }
                    }

                    @Override
                    public Class<T> getAccessingClass() {
                        return clazz;
                    }

                    @Override
                    public Class<?>[] getParams() {
                        return params;
                    }

                    @Override
                    public boolean equals(Object other) {
                        if (!(other instanceof ConstructorAccessor)) {
                            return false;
                        }
                        ConstructorAccessor accessor = (ConstructorAccessor) other;

                        return accessor.getAccessingClass().equals(this.getAccessingClass()) && Arrays.equals(accessor.getParams(), this.getParams());
                    }

                };
            }
        }

        throw new ReflectionException.ConstructorNotFoundException(clazz);
    }

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(className);
        }
    }

    public static Class<?> getNMSClass(String className) {
        return Reflection.getClass(NMS_PACKAGE + "." + className);
    }

    public static Class<?> getOBCClass(String className) {
        return Reflection.getClass(OBC_PACKAGE + "." + className);
    }


    private Reflection() {
        throw new UtilInstantiationException();
    }

}
