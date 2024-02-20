package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class StrengthEssence extends Essence {
    public StrengthEssence() {
        super("Strength Essence", new ItemStack(Material.RED_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "STRONG LIKE A BULL");
        lore.add(getEssenceLoreColor() + "Start with strength 1");
        lore.add(getEssenceLoreColor() + "Upon 5 kills, get strength 2");
        lore.add(getEssenceLoreColor() + "Reset strength on death");
        lore.add(getEssenceLoreColor() + "Shift + right click to get strength 3 for 10 seconds");
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
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
        return effects;
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.DARK_RED;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.RED;
    }
}
