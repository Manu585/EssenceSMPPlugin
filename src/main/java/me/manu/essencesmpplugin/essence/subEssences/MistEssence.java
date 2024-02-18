package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MistEssence extends Essence {
    public MistEssence() {
        super("Mist Essence", createLore(), new ItemStack(Material.BLUE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Feel the strength of the ocean.");
        lore.add(ChatColor.BLUE + "Grants Dolphin's Grace, strength 2, resistance and +4 hearts when in water.");
        lore.add(ChatColor.BLUE + "Reverse players breathing when right clicking them.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Lasts 40 seconds with a cooldown of 240 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_BLUE + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }
}
