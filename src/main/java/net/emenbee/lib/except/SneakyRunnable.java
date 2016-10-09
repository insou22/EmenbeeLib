package net.emenbee.lib.except;

@FunctionalInterface
public interface SneakyRunnable {

    void run() throws Exception;

}
