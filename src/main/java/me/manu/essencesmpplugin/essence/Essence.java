package me.manu.essencesmpplugin.essence;

import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public abstract class Essence {
    private String essenceName;
    private List<String> essenceLore = new ArrayList<>();
    private ItemStack essenceItem;

    public Essence(String name, ItemStack item) {
        this.essenceName = name;
        this.essenceItem = item;
        initialize();
    }

    protected abstract void initialize();

    public String getEssenceName() {
        return essenceName;
    }

    public List<String> getEssenceLore() {
        return essenceLore;
    }

    public ItemStack getEssenceItem() {
        return essenceItem;
    }

    public void setEssenceName(String essenceName) {
        this.essenceName = essenceName;
    }

    public void setEssenceLore(List<String> essenceLore) {
        this.essenceLore = essenceLore;
    }

    public void setEssenceItem(ItemStack essenceItem) {
        this.essenceItem = essenceItem;
    }

    public abstract void handleEvent(Event e, EssencePlayer essencePlayer);

    public abstract List<PotionEffect> getPassivePotionEffect();

    public abstract ChatColor getEssenceColor();

    public abstract ChatColor getEssenceLoreColor();

    public ChatColor getEssenceCooldownLoreColor() {
        return ChatColor.GRAY;
    }
}
