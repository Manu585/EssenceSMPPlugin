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

public class DarknessEssence extends Essence {
    public DarknessEssence() {
        super("Darkness Essence", new ItemStack(Material.BLACK_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "FEAR THE DARKNESS");
        lore.add(getEssenceLoreColor() + "10% chance to blind players when hitting them.");
        lore.add(getEssenceLoreColor() + "can't receive blindness.");
        lore.add(getEssenceLoreColor() + "right click with a sword to activate " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "CONSUMPTION");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Consumption: Give all nearby players blindness and weakness for 6 seoncds.");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
        lore.add(getEssenceLoreColor() + "Shift + Right click activates " + getEssenceColor() + "" + ChatColor.BOLD + "DARKNESS OVERLORD");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Darkness Overlord: Turn into a random player online for 120 seconds.");
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
        return ChatColor.BLACK;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.GRAY;
    }
}
