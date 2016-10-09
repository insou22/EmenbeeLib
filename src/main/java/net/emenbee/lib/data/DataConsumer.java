package net.emenbee.lib.data;

import com.google.common.collect.Queues;
import net.emenbee.lib.except.TryUtils;
import net.emenbee.lib.generic.GenericUtils;
import net.emenbee.lib.message.Strings;
import net.emenbee.lib.task.LibTask;
import net.emenbee.lib.task.Tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

final class DataConsumer {

    private final Queue<DataStatement> dataQueue = Queues.newConcurrentLinkedQueue();
    private final DataManager manager;
    private final DataTask task;

    DataConsumer(DataManager manager) {
        this.manager = manager;

        this.task = new DataTask();

        this.task.start();
    }

    void add(DataStatement statement) {
        this.dataQueue.add(statement);
    }

    void shutdown() {
        this.task.cancel();
    }

    private final class DataTask extends LibTask {

        private final Object lock = new Object();
        private final AtomicBoolean running = new AtomicBoolean(false);

        public DataTask() {
            super(false, 10);
        }

        @Override
        public void run() {
            if (this.running.get()) {
                return;
            }

            this.running.set(true);

            synchronized (this.lock) {

                Connection connection = TryUtils.sneaky(DataConsumer.this.manager::getConnection);

                while (!DataConsumer.this.dataQueue.isEmpty()) {

                    DataStatement statement = DataConsumer.this.dataQueue.poll();

                    TryUtils.sneaky(() -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(Strings.format(statement.getStatement()));

                        for (int i = 0; i < statement.getParams().length; i++) {
                            preparedStatement.setObject(i + 1, statement.getParams()[i]);
                        }

                        if (statement instanceof DataQuery) {
                            DataQuery query = GenericUtils.cast(statement);

                            ResultSet results = preparedStatement.executeQuery();

                            Tasks.runSync(() -> TryUtils.sneaky(() -> query.consume(results)));
                        } else {
                            preparedStatement.execute();
                        }
                    });

                }

                TryUtils.sneaky(connection::close);
            }

            this.running.set(false);
        }

    }

}
