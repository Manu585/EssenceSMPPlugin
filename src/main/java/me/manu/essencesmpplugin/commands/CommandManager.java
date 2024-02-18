package me.manu.essencesmpplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandManager implements CommandExecutor, TabExecutor {
    private final JavaPlugin plugin;
    private final String packagePath = "me.manu.essencesmpplugin.commands";
    private Map<String, ICommand> commandInstances = new HashMap<>();


    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        Reflections reflections = new Reflections(packagePath);
        Set<Class<? extends ICommand>> commandClasses = reflections.getSubTypesOf(ICommand.class);

        for (Class<? extends ICommand> cmdClass : commandClasses) {
            try {
                ICommand commandInstance = cmdClass.getDeclaredConstructor().newInstance();
                commandInstances.put(commandInstance.getName().toLowerCase(), commandInstance);
                plugin.getCommand(commandInstance.getName()).setExecutor(this);
                // Optionally set a TabCompleter if your commands require it
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Dispatch the command to the appropriate ICommand instance
        // This assumes your ICommand implementations are singletons or otherwise accessible
        ICommand cmd = findCommandInstance(command.getName());
        if (cmd != null) {
            if (sender.hasPermission(cmd.getPermission())) {
                cmd.execute(sender, args);
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
            return true;
        }
        return false;
    }

    private ICommand findCommandInstance(String name) {
        return commandInstances.get(name.toLowerCase());
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // Implement tab completion logic if needed
        return new ArrayList<>();
    }
}