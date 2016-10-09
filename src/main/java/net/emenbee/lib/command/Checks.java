package net.emenbee.lib.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public enum Checks implements CommandCheck {

    IS_PLAYER(Player.class::isInstance),
    IS_NOT_PLAYER(sender -> !Player.class.isInstance(sender)),
    IS_OP(CommandSender::isOp);

    private Predicate<CommandSender> predicate;

    Checks(Predicate<CommandSender> predicate) {
        this.predicate = predicate;
    }

    public boolean test(CommandSender sender) {
        return this.predicate.test(sender);
    }

}
