package me.manu.essencesmpplugin.commands;

import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EssenceCommand implements ICommand {
    @Override
    public String getName() {
        return "essence";
    }

    @Override
    public String getDescription() {
        return "Opens the essence menu.";
    }

    @Override
    public String getPermission() {
        return "essencesmpplugin.essence";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return;
        }
        Player player = (Player) sender;
        player.sendMessage("Opening essence menu...");
    }
}
