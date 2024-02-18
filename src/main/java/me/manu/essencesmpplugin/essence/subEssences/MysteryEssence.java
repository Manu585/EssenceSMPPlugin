package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MysteryEssence extends Essence {
    public MysteryEssence() {
        super("Mystery Essence", createLore(), new ItemStack(Material.PINK_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "How?!.");
        lore.add(ChatColor.LIGHT_PURPLE + "Give other players random effects when hitting them with a sword." + ChatColor.GRAY + " (50% chance of happening)");
        lore.add(ChatColor.LIGHT_PURPLE + "Grants you potion effects, when getting hit by other players.");
        lore.add(ChatColor.LIGHT_PURPLE + "Bows shoot random tipped arrows instead of normal arrows.");
        lore.add(ChatColor.LIGHT_PURPLE + "Right clicking while holding a sword to explode like a creeper.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
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
