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

public class DarknessEssence extends Essence {
    public DarknessEssence() {
        super("Darkness Essence", createLore(), new ItemStack(Material.BLACK_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.BLACK + "" + ChatColor.BOLD + "FEAR THE DARKNESS");
        lore.add(ChatColor.GRAY + "10% chance to blind players when hitting them.");
        lore.add(ChatColor.GRAY + "can't receive blindness.");
        lore.add(ChatColor.GRAY + "right click with a sword to activate " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "CONSUMPTION");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Consumption: Give all nearby players blindness and weakness for 6 seoncds.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
        lore.add(ChatColor.GRAY + "Shift + Right click activates " + ChatColor.BLACK + "" + ChatColor.BOLD + "DARKNESS OVERLORD");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Darkness Overlord: Turn into a random player online for 120 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.BLACK + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(PlayerEvent e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerInteractEvent) {
            EssenceSMPPlugin.getGeneralMethods().onRightClickWithEssence((PlayerInteractEvent) e, EssenceCreator.getDarknessEssence());
        }
    }
}
