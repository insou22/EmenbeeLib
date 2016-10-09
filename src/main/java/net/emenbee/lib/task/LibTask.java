package net.emenbee.lib.task;

public abstract class LibTask implements Runnable {

    private final boolean sync;
    private final long tickRepeat;
    private final long tickDelay;

    private int id = -1;

    public LibTask(boolean sync, long tickRepeat) {
        this(sync, tickRepeat, 0L);
    }

    public LibTask(boolean sync, long tickRepeat, long tickDelay) {
        this.sync = sync;
        this.tickRepeat = tickRepeat;
        this.tickDelay = tickDelay;
    }

    public void start() {
        this.id = Tasks.runLaterRepeating(this, this.sync, this.tickDelay, this.tickRepeat);
    }

    public void cancel() {
        if (this.id == -1) {
            return;
        }
        Tasks.cancel(this.id);
    }

}
