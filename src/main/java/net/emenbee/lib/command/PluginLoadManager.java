package net.emenbee.lib.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.emenbee.lib.Lib;
import net.emenbee.lib.except.CommandConstructionException;
import net.emenbee.lib.manage.Manager;
import net.emenbee.lib.reflection.ConstructorAccessor;
import net.emenbee.lib.reflection.FieldAccessor;
import net.emenbee.lib.reflection.Reflection;
import net.emenbee.lib.reflection.ReflectionException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PluginLoadManager extends Manager<Lib> {

    private static final FieldAccessor<Map> ENCLOSED_MAP_ACCESSOR = Reflection.getField(SimpleCommandMap.class, "knownCommands", Map.class);

    private CommandMap commandMap;
    private final Map<Plugin, List<CommandConsumer>> loadedClasses = Maps.newHashMap();

    public PluginLoadManager(Lib plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        FieldAccessor<CommandMap> mapAccessor = Reflection.getField(SimplePluginManager.class, "commandMap", CommandMap.class);
        this.commandMap = mapAccessor.get(this.plugin.getServer().getPluginManager());

        for (Plugin plugin : this.plugin.getServer().getPluginManager().getPlugins()) {
            this.loadPlugin(plugin);
        }
        this.plugin.getServer().getPluginManager().registerEvents(new PluginListener(), this.plugin);
    }

    @EventHandler
    public void onDisable() {
        for (Plugin plugin : this.plugin.getServer().getPluginManager().getPlugins()) {
            this.unloadPlugin(plugin);
        }
    }

    private void loadPlugin(Plugin plugin) {
        if (plugin.getClass().getPackage() == null) {
            return;
        }
        String packageName = plugin.getClass().getPackage() == null ? "" : plugin.getClass().getPackage().getName();
        Class<? extends Plugin> pluginClass = plugin.getClass();

        this.plugin.getLogger().info("Found plugin : " + packageName + "." + pluginClass.getSimpleName());

        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));

        Set<Class<? extends CommandConsumer>> pluginClasses = reflections.getSubTypesOf(CommandConsumer.class);

        pluginClasses.stream().forEach(clazz -> {
            if (clazz.isAnnotationPresent(FindCommand.class)) {

                ConstructorAccessor<? extends CommandConsumer> construct;
                try {
                    construct = Reflection.getConstructor(clazz);
                } catch (ReflectionException.ConstructorNotFoundException e) {
                    throw new CommandConstructionException();
                }

                this.loadCommand(plugin, construct.create());

            }
        });
    }

    private void loadCommand(Plugin plugin, CommandConsumer consumer) {
        if (this.loadedClasses.containsKey(plugin) && this.loadedClasses.get(plugin) != null) {
            for (CommandConsumer loaded : this.loadedClasses.get(plugin)) {
                if (loaded.getClass().equals(consumer.getClass())) {
                    return;
                }
            }
        }
        if (!this.loadedClasses.containsKey(plugin)) {
            this.loadedClasses.put(plugin, Lists.newArrayList());
        }
        this.loadedClasses.get(plugin).add(consumer);

        String commandName = consumer.getClass().getDeclaredAnnotation(FindCommand.class).command();

        Command command = new Command(commandName) {
            @Override
            public boolean execute(CommandSender sender, String label, String[] args) {
                consumer.dispatch(sender, label, args);
                return false;
            }
        };

        command.setAliases(consumer.getAliases());

        this.commandMap.register(commandName, command);
    }

    private void unloadPlugin(Plugin plugin) {
        if (this.loadedClasses.containsKey(plugin)) {
            List<CommandConsumer> consumers = this.loadedClasses.get(plugin);

            Map enclosedMap = PluginLoadManager.ENCLOSED_MAP_ACCESSOR.get(this.commandMap);

            consumers.forEach(consumer -> enclosedMap.remove(consumer.getClass().getDeclaredAnnotation(FindCommand.class).command()));
        }
    }

    private class PluginListener implements Listener {

        @EventHandler
        public void on(PluginEnableEvent event) {
            PluginLoadManager.this.loadPlugin(event.getPlugin());
        }

        @EventHandler
        public void on(PluginDisableEvent event) {
            PluginLoadManager.this.unloadPlugin(event.getPlugin());
        }

    }

}
