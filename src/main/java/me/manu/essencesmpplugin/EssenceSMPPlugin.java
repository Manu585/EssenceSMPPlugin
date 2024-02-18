package me.manu.essencesmpplugin;

import me.manu.essencesmpplugin.commands.CommandManager;
import me.manu.essencesmpplugin.listener.SunsetEssenceListener;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssenceSMPPlugin extends JavaPlugin {

    public static EssenceSMPPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        new CommandManager(this);
        Bukkit.getPluginManager().registerEvents(new SunsetEssenceListener(), this);
        EssenceCreator.initEssences();
    }

    @Override
    public void onDisable() {

    }

    public static EssenceSMPPlugin getInstance() {
        return instance;
    }
}
