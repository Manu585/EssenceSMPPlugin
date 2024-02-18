package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BruteEssence extends Essence {
    public BruteEssence() {
        super("Brute Essence", createLore(), new ItemStack(Material.LIME_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "FIGHT!");
        lore.add(ChatColor.GREEN + "Piglins + Piglin Brutes will not attack you.");
        lore.add(ChatColor.GREEN + "Increased axe damage and disables enemies shields.");
        lore.add(ChatColor.GRAY + "Right click with axe to actiavte " + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "FULL HOG" + ChatColor.GRAY + "!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Full Hog: resistance 2, slowness 1, + 4 hearts for 15 seconds.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 160 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_GREEN + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }
}
