package me.manu.essencesmpplugin.commands;

import org.bukkit.command.CommandSender;

public interface ICommand {
    String getName();
    String getDescription();
    String getPermission();
    void execute(CommandSender sender, String[] args);
}
