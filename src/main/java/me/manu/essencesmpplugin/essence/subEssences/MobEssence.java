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

public class MobEssence extends Essence {
    public MobEssence() {
        super("Mob Essence", createLore(), new ItemStack(Material.GREEN_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BECOME WHAT YOU HATE");
        lore.add(ChatColor.GREEN + "Everytime you kill a mob, you get a special ability depending on the mob you killed.");
        lore.add(ChatColor.BLACK + "Spider: " + ChatColor.GREEN + "be able to craft cobwebs with forming a x with string.");
        lore.add(ChatColor.DARK_GREEN + "Creeper: " + ChatColor.GREEN + "Be able to explode by right clicking with a flint and steel. " + ChatColor.GRAY + "(Cooldown of 60 seconds)");
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

    @Override
    public void handleEvent(PlayerEvent e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerInteractEvent) {
            EssenceSMPPlugin.getGeneralMethods().onRightClickWithEssence((PlayerInteractEvent) e, EssenceCreator.getMobEssence());
        }
    }
}
