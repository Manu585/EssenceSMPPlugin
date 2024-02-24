package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class DefaultEssence extends Essence {
    public DefaultEssence() {
        super("DefaultEssence", new ItemStack(Material.BIRCH_LOG));
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void handleEvent(Event e, EssencePlayer essencePlayer) {

    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        return null;
    }

    @Override
    public ChatColor getEssenceColor() {
        return null;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return null;
    }
}
