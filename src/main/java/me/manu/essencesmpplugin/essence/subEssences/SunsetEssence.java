package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SunsetEssence extends Essence {
    public SunsetEssence() {
        super("Sunset Essence", createLore(), new ItemStack(Material.ORANGE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GOLD + "This essence radiates warmth and energy.");
        lore.add(ChatColor.GOLD + "Right clicking while holding a sword will shoot out a Fireball.");
        lore.add(ChatColor.GRAY + "Grants Fire Resistance and extra power in the Nether.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }
}
