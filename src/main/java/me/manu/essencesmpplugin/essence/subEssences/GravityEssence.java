package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GravityEssence extends Essence {
    public GravityEssence() {
        super("Gravity Essence", createLore(), new ItemStack(Material.PURPLE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "DEFINE GRAVITY");
        lore.add(ChatColor.LIGHT_PURPLE + "Shift + right click a player to stop their movement for 5 seconds");
        lore.add(ChatColor.LIGHT_PURPLE + "Shift + left click gives everyone in a 5 block radius levitation for 4 seconds");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_PURPLE + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }
}
