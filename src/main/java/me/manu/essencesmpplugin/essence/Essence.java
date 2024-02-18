package me.manu.essencesmpplugin.essence;

import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public abstract class Essence {
    private String essenceName;
    private List<String> essenceLore;
    private ItemStack essenceItem;


    public Essence(String name, List<String> lore, ItemStack item) {
        this.essenceName = name;
        this.essenceLore = lore;
        this.essenceItem = item;
    }

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

    public abstract void handleEvent(PlayerEvent e, EssencePlayer essencePlayer);

    public abstract PotionEffect getPotionEffect();
}
