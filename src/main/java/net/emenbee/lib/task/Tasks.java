package net.emenbee.lib.task;

import net.emenbee.lib.Lib;
import net.emenbee.lib.except.UtilInstantiationException;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public final class Tasks {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();

    private Tasks() {
        throw new UtilInstantiationException();
    }

    public static int runSync(Runnable runnable) {
        return Tasks.scheduler.runTask(Lib.get(), runnable).getTaskId();
    }

    public static int runSyncLater(Runnable runnable, long ticks) {
        return Tasks.scheduler.runTaskLater(Lib.get(), runnable, ticks).getTaskId();
    }

    public static int runSyncRepeating(Runnable runnable, long tickRepeat) {
        return Tasks.runSyncLaterRepeating(runnable, 0, tickRepeat);
    }

    public static int runSyncLaterRepeating(Runnable runnable, long later, long tickRepeat) {
        return Tasks.scheduler.scheduleSyncRepeatingTask(Lib.get(), runnable, later, tickRepeat);
    }

    public static int runAsync(Runnable runnable) {
        return Tasks.scheduler.runTaskAsynchronously(Lib.get(), runnable).getTaskId();
    }

    public static int runAsyncLater(Runnable runnable, long ticks) {
        return Tasks.runSyncLater(() -> Tasks.runAsync(runnable), ticks);
    }

    public static int runAsyncRepeating(Runnable runnable, long tickRepeat) {
        return Tasks.runAsyncLaterRepeating(runnable, 0, tickRepeat);
    }

    public static int runAsyncLaterRepeating(Runnable runnable, long later, long tickRepeat) {
        return Tasks.runSyncLaterRepeating(() -> Tasks.runAsync(runnable), later, tickRepeat);
    }

    public static int runLaterRepeating(Runnable runnable, boolean sync, long later, long tickRepeat) {
        return sync ?
                Tasks.runSyncLaterRepeating(runnable, later, tickRepeat) :
                Tasks.runAsyncLaterRepeating(runnable, later, tickRepeat);
    }

    public static void cancel(int id) {
        Tasks.scheduler.cancelTask(id);
    }

}
