package net.emenbee.lib.manage;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import net.emenbee.lib.generic.GenericUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class ManagingPlugin extends JavaPlugin {

    private final Map<Class<? extends Manager>, Manager> managers = new ConcurrentLinkedHashMap.Builder<Class<? extends Manager>, Manager>().build();

    protected final void loadManagers(Manager... managers) {
        Stream.of(managers).forEach(manager -> this.managers.put(manager.getClass(), manager));
    }

    protected final Collection<Manager> allManagers() {
        return this.managers.values();
    }

    protected final void enableManagers() {
        this.allManagers().forEach(Manager::enable);
    }

    protected final void disableManagers() {
        this.allManagers().forEach(Manager::onDisable);
    }

    public final <T extends Manager> T getManager(Class<? extends T> type) {
        return GenericUtils.cast(managers.get(type));
    }

}
