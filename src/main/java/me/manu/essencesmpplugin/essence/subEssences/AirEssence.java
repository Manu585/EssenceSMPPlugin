package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AirEssence extends Essence {
    public AirEssence() {
        super("Air Essence", createLore(), new ItemStack(Material.LIGHT_BLUE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "LIGHT ON THE FEET");
        lore.add(ChatColor.BLUE + "Grants speed 2, double jump and no fall damage.");
        lore.add(ChatColor.BLUE + "Right click to activate " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "SUGAR RUSH" + ChatColor.BLUE + "!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Sugar Rush: Hit faster, Speed boost for 10 seconds.");
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
