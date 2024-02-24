package me.manu.essencesmpplugin;

import me.manu.essencesmpplugin.commands.CommandManager;
import me.manu.essencesmpplugin.config.ConfigManager;
import me.manu.essencesmpplugin.database.EssencePlayerDB;
import me.manu.essencesmpplugin.listener.CropGrowthListener;
import me.manu.essencesmpplugin.listener.EssenceEventHandler;
import me.manu.essencesmpplugin.listener.GeneralListener;
import me.manu.essencesmpplugin.manager.CooldownManager;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.manager.PotionEffectManager;
import me.manu.essencesmpplugin.manager.SneakCropGrowthManager;
import me.manu.essencesmpplugin.methods.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class EssenceSMPPlugin extends JavaPlugin {
    public static EssenceSMPPlugin instance;
    private static PotionEffectManager potionEffectManager = new PotionEffectManager();
    private static CooldownManager cooldownManager = new CooldownManager();
    private static GeneralMethods generalMethods = new GeneralMethods();
    private static EssencePlayerDB database;


    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        initDatabase();


        new ConfigManager();
        SneakCropGrowthManager sneakCropGrowthManager = new SneakCropGrowthManager(this);
        new CommandManager(this);
        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
        getServer().getPluginManager().registerEvents(new EssenceEventHandler(), this);
        getServer().getPluginManager().registerEvents(new CropGrowthListener(sneakCropGrowthManager), this);

        EssenceCreator.initEssences();
    }

    @Override
    public void onDisable() {
        try {
            database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDatabase() {
        try {
            database = new EssencePlayerDB(getDataFolder().getAbsolutePath() + "/Essence.db");
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().severe("Failed to initialize database!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static EssencePlayerDB getDatabase() {
        return database;
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
