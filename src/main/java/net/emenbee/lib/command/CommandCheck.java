package net.emenbee.lib.command;

import org.bukkit.command.CommandSender;

import java.util.function.Predicate;

public interface CommandCheck extends Predicate<CommandSender> {

}
