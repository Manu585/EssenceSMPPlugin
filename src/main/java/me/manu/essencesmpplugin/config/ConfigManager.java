package me.manu.essencesmpplugin.config;

import me.manu.essencesmpplugin.util.EssenceChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ConfigManager {
    public static Config defaultConfig;
    public static Config languageConfig;

    public ConfigManager() {
        defaultConfig = new Config(new File("config.yml"));
        languageConfig = new Config(new File("language.yml"));
        configCheck(ConfigType.DEFAULT);
    }

    public static void configCheck(final ConfigType type) {
        FileConfiguration config;
        if (type == ConfigType.DEFAULT) {
            config = defaultConfig.get();
            List<String> defaultLore = Arrays.asList("Line 1", "Line 2");

            config.addDefault("essence.airessence.essencename", EssenceChatColor.format("#6c2cbfAir Essence"));
            config.addDefault("essence.airessence.essencelore", defaultLore);
            config.addDefault("essence.airessence.passivepotioneffect.speed", 2);


            defaultConfig.save();
        } else if (type == ConfigType.LANGUAGE) {
            config = languageConfig.get();
            languageConfig.save();
        }
    }

    public static FileConfiguration getConfig() {
        return ConfigManager.defaultConfig.get();
    }
}
