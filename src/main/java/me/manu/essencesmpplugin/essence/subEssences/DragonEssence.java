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

public class DragonEssence extends Essence {
    public DragonEssence() {
        super("Dragon Essence", createLore(), new ItemStack(Material.WHITE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "ROAR");
        lore.add(ChatColor.GRAY + "Permanent strength 1");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift + right click: " + ChatColor.WHITE + "" + ChatColor.BOLD + "Activate Dragon's Breath");
        lore.add(ChatColor.WHITE + "Dragon's Breath: " + ChatColor.GRAY + "Shoot a lazer beam at a player which deals immense damaage.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Shift + left click: " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Activate Dragon summoner");
        lore.add(ChatColor.LIGHT_PURPLE + "Dragon Summoner: " + ChatColor.GRAY + "Summon a dragon whom you may ride for 10 seconds");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 200 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.WHITE + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(PlayerEvent e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerInteractEvent) {
            EssenceSMPPlugin.getGeneralMethods().onRightClickWithEssence((PlayerInteractEvent) e, EssenceCreator.getDragonEssence());
        }
    }
}
