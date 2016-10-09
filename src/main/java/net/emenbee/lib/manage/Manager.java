package net.emenbee.lib.manage;

import org.bukkit.plugin.java.JavaPlugin;

public class Manager<T extends JavaPlugin> {

    private boolean enabled = false;

    protected final T plugin;

    public Manager(T plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        if (this.enabled) {
            return;
        }
        enabled = true;
        this.onEnable();
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

}
