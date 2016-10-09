package net.emenbee.lib.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.emenbee.lib.except.CommandConstructionException;
import net.emenbee.lib.player.Senders;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class CommandConsumer {

    private final List<String> aliases = Lists.newArrayList();
    private final Map<CommandCheck, String> checks = Maps.newLinkedHashMap();
    private String permission;

    private CommandSender sender;
    private String label;
    private List<String> args;

    public CommandConsumer() {
        this.checkNoArgs();
    }

    final void dispatch(CommandSender sender, String label, String[] args) {
        for (CommandCheck check : this.checks.keySet()) {
            if (!check.test(sender)) {
                Senders.send(sender, this.checks.get(check));
                return;
            }
        }
        if (this.permission != null && sender.hasPermission(this.permission)) {
            Senders.send(sender, "&cYou don't have permission to do this!");
            return;
        }
        this.sender = sender;
        this.label = label;
        this.args = Lists.newArrayList(args);
        this.execute();
        this.clean();
    }

    private void clean() {
        this.sender = null;
        this.label = null;
        this.args = null;
    }

    final List<String> getAliases() {
        return this.aliases;
    }

    final String getPermission() {
        return this.permission;
    }

    private void checkNoArgs() {
        try {
            this.getClass().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new CommandConstructionException();
        }
    }

    final protected CommandConsumer addCheck(String message, CommandCheck check) {
        this.checks.put(check, message);
        return this;
    }

    final protected CommandConsumer addChecks(String message, CommandCheck... checks) {
        Stream.of(checks).forEach(check -> this.checks.put(check, message));
        return this;
    }

    final protected CommandConsumer addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    final protected CommandConsumer addAliases(String... aliases) {
        this.aliases.addAll(Lists.newArrayList(aliases));
        return this;
    }

    final protected CommandConsumer addPermission(String permission) {
        this.permission = permission;
        return this;
    }

    protected final String getLabel() {
        return this.label;
    }

    protected final CommandSender getSender() {
        return this.sender;
    }

    protected final <T extends CommandSender> T getCastSender() {
        return (T) this.getSender();
    }

    protected final List<String> getArgs() {
        return Lists.newArrayList(this.args);
    }

    protected final String getArg(int index) {
        if (index >= this.getArgsAmount()) {
            return null;
        }
        return this.args.get(index);
    }

    protected final int getArgsAmount() {
        return this.args.size();
    }

    protected abstract void execute();

}
