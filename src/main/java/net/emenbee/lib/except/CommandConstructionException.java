package net.emenbee.lib.except;

public class CommandConstructionException extends RuntimeException {

    public CommandConstructionException() {
        super("CommandConsumer does not have a No-Args (0 argument) constuctor");
    }

}
