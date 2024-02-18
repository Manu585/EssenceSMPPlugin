package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StrengthEssence extends Essence {
    public StrengthEssence() {
        super("Strength Essence", createLore(), new ItemStack(Material.RED_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "STRONG LIKE A BULL");
        lore.add(ChatColor.RED + "Start with strength 1");
        lore.add(ChatColor.RED + "Upon 5 kills, get strength 2");
        lore.add(ChatColor.RED + "Reset strength on death");
        lore.add(ChatColor.RED + "Shift + right click to get strength 3 for 10 seconds");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_RED + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(PlayerEvent e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerInteractEvent) {
            EssenceSMPPlugin.getGeneralMethods().onRightClickWithEssence((PlayerInteractEvent) e, EssenceCreator.getStrengthEssence());
        }
    }
}
