package net.emenbee.lib;

import net.emenbee.lib.command.PluginLoadManager;
import net.emenbee.lib.gui.GUIManager;
import net.emenbee.lib.manage.ManagingPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lib extends ManagingPlugin {

    public static Lib get() {
        return JavaPlugin.getPlugin(Lib.class);
    }

    @Override
    public void onEnable() {
        this.loadManagers();
    }

    @Override
    public void onDisable() {
        this.disableManagers();
    }

    private void loadManagers() {
        loadManagers(
                new PluginLoadManager(this),
                new GUIManager(this)
        );
        this.enableManagers();
    }

}
