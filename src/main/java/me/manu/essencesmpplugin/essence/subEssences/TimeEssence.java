package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TimeEssence extends Essence {
    public TimeEssence() {
        super("Time Essence", createLore(), new ItemStack(Material.BROWN_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Feel the time flow in you.");
        lore.add(ChatColor.YELLOW + "Grants double xp, haste 2, regeneration 1, hero of the village 3");
        lore.add(ChatColor.YELLOW + "Grow nearby crops around you when sneaking.");
        lore.add(ChatColor.YELLOW + "Shift + Right click to activate " + ChatColor.GRAY + ChatColor.BOLD + "Time Bender" + ChatColor.RESET + "" + ChatColor.YELLOW + ".");
        lore.add(ChatColor.GRAY + "Time Bender: " + ChatColor.YELLOW + "Give nearby players slow falling and slowness for 10 seconds.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "cooldown of 90 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getChatColor() + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    public static ChatColor getChatColor() {
        return ChatColor.YELLOW;
    }
}
