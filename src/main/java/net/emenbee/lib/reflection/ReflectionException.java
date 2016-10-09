package net.emenbee.lib.reflection;

import java.util.Arrays;

public class ReflectionException extends RuntimeException {

    public ReflectionException(String message) {
        super(message);
    }

    public static class ClassException extends ReflectionException {

        public ClassException(String className) {
            super("Could not find class \"" + className + "\"");
        }

    }

    public static class FieldNotFoundException extends ReflectionException {

        public FieldNotFoundException(Class<?> clazz, String fieldName) {
            super("Could not find field \"" + clazz.getName() + "->" + fieldName + "\"");
        }

    }

    public static class FieldInaccessibleException extends ReflectionException {

        public FieldInaccessibleException(Class<?> clazz, String fieldName) {
            super("Could not access field \"" + clazz.getName() + "->" + fieldName + "\"");
        }

    }

    public static class MethodNotFoundException extends ReflectionException {

        public MethodNotFoundException(Class<?> clazz, String methodName, Class<?>... params) {
            super("Could not find method \"" + clazz.getName() + "::" + methodName + " " + Arrays.toString(params) + "\"");
        }

    }

    public static class MethodInaccessibleException extends ReflectionException {

        public MethodInaccessibleException(Class<?> clazz, String methodName, Class<?>... params) {
            super("Could not access method \"" + clazz.getName() + "::" + methodName + " " + Arrays.toString(params) + "\"");
        }

    }

    public static class ConstructorNotFoundException extends ReflectionException {

        public ConstructorNotFoundException(Class<?> constructorClass, Class<?>... params) {
            super("Could not find constructor \"" + constructorClass.getName() + " " + Arrays.toString(params) + "\"");
        }

    }

    public static class ConstructorInaccessibleException extends ReflectionException {

        public ConstructorInaccessibleException(Class<?> constructorClass, Class<?>... params) {
            super("Could not access constructor \"" + constructorClass.getName() + " " + Arrays.toString(params) + "\"");
        }

    }

}
