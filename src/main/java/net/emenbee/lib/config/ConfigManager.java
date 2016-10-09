package net.emenbee.lib.config;

import net.emenbee.lib.except.TryUtils;
import net.emenbee.lib.generic.GenericUtils;
import net.emenbee.lib.manage.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public abstract class ConfigManager<T extends JavaPlugin> extends Manager<T> {

    protected transient final FileConfiguration config;

    public ConfigManager(T plugin) {
        super(plugin);
        this.config = plugin.getConfig();
    }

    @Override
    public void onEnable() {
        this.plugin.saveDefaultConfig();

        for (Field field : this.getClass().getFields()) {
            if (field.isAnnotationPresent(ConfigPopulate.class)) {
                TryUtils.sneaky(() -> field.set(this, this.get(field.getAnnotation(ConfigPopulate.class).key())));
            }
        }
    }

    protected void onConfigEnable() {}

    protected <V> V get(String key) {
        return GenericUtils.cast(key);
    }

}
