package net.emenbee.lib.except;

public class UtilInstantiationException extends RuntimeException {

    public UtilInstantiationException() {
        super("Cannot be instantiated!");
    }

}
