package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class BruteEssence extends Essence {
    public BruteEssence() {
        super("Brute Essence", new ItemStack(Material.LIME_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "FIGHT!");
        lore.add(getEssenceLoreColor() + "Piglins + Piglin Brutes will not attack you.");
        lore.add(getEssenceLoreColor() + "Increased axe damage and disables enemies shields.");
        lore.add(ChatColor.GRAY + "Right click with axe to actiavte " + getEssenceColor() + "" + ChatColor.BOLD + "FULL HOG" + ChatColor.GRAY + "!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Full Hog: resistance 2, slowness 1, + 4 hearts for 15 seconds.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 160 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getEssenceColor() + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(Event e, EssencePlayer essencePlayer) {
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        return new ArrayList<>();
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.DARK_GREEN;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.GREEN;
    }
}
