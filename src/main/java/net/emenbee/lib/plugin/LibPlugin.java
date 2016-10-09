package net.emenbee.lib.plugin;

import net.emenbee.lib.gui.GUIManager;
import net.emenbee.lib.gui.GUIPlayer;
import net.emenbee.lib.locale.Locale;
import net.emenbee.lib.manage.ManagingPlugin;
import net.emenbee.lib.player.PlayerWrapper;
import net.emenbee.lib.task.Tasks;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class LibPlugin extends ManagingPlugin {

    protected final AtomicBoolean loaded = new AtomicBoolean(false);

    private Locale locale;

    @Override
    public final void onEnable() {
        this.locale = new Locale();
        this.locale.populate(this);

        this.onPluginEnable();

        Tasks.runAsync(() -> {
            this.onPluginAsyncEnable();
            this.loaded.set(true);
        });
    }

    protected void onPluginEnable() {}

    protected void onPluginAsyncEnable() {}

    public Locale getLocale() {
        return this.locale;
    }

    public String getMessage(String key, Object... args) {
        return this.getLocale().getFormatted(key, args);
    }

    public GUIPlayer gui(PlayerWrapper player) {
        return this.gui(player.get());
    }

    public GUIPlayer gui(Player player) {
        GUIManager guiManager = this.getManager(GUIManager.class);

        if (guiManager == null) {
            return null;
        }

        return guiManager.getPlayer(player);
    }

    public boolean isLoaded() {
        return this.loaded.get();
    }

}
