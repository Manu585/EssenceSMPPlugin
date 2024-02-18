package me.manu.essencesmpplugin;

import me.manu.essencesmpplugin.commands.CommandManager;
import me.manu.essencesmpplugin.listener.EssenceEventHandler;
import me.manu.essencesmpplugin.listener.GeneralListener;
import me.manu.essencesmpplugin.manager.CooldownManager;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.manager.PotionEffectManager;
import me.manu.essencesmpplugin.methods.GeneralMethods;
import org.bukkit.plugin.java.JavaPlugin;

public final class EssenceSMPPlugin extends JavaPlugin {
    public static EssenceSMPPlugin instance;
    private static PotionEffectManager potionEffectManager = new PotionEffectManager();
    private static CooldownManager cooldownManager = new CooldownManager();
    private static GeneralMethods generalMethods = new GeneralMethods();

    @Override
    public void onEnable() {
        instance = this;

        new CommandManager(this);
        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
        getServer().getPluginManager().registerEvents(new EssenceEventHandler(), this);

        EssenceCreator.initEssences();
    }

    @Override
    public void onDisable() {

    }

    public static EssenceSMPPlugin getInstance() {
        return instance;
    }

    public static PotionEffectManager getPotionEffectManager() {
        return potionEffectManager;
    }

    public static CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public static GeneralMethods getGeneralMethods() {
        return generalMethods;
    }
}
