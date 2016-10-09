package net.emenbee.lib.data;

import co.insou.pool.CredentialPackageFactory;
import co.insou.pool.Pool;
import net.emenbee.lib.manage.Manager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DataManager<T extends JavaPlugin> extends Manager<T> {

    private final DataInfo info;
    private final int min;
    private final int max;

    private Pool pool;
    private DataConsumer dataConsumer;

    protected DataManager(T plugin, DataInfo info) {
        this(plugin, info, 3, 3);
    }

    protected DataManager(T plugin, DataInfo info, int min, int max) {
        super(plugin);
        this.info = info;
        this.min = min;
        this.max = max;
    }

    public void boot() {
        this.pool = new Pool(CredentialPackageFactory.get(this.info.getUsername(), this.info.getPassword()));

        this.pool.withUrl(this.info.getUrl());

        this.pool.withMin(this.min);
        this.pool.withMax(this.max);

        this.pool.build();

        this.dataConsumer = new DataConsumer(this);
    }

    public void shutdown() {
        if (this.dataConsumer != null) {
            this.dataConsumer.shutdown();
        }
        if (this.pool != null && !this.pool.isClosed()) {
            this.pool.close();
        }
    }

    protected void table(String statement) {
        try (Connection connection = this.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() throws SQLException {
        return this.pool.getConnection();
    }

    public void data(DataStatement statement) {
        this.dataConsumer.add(statement);
    }

}
