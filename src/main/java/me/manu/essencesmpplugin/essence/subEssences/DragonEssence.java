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

public class DragonEssence extends Essence {
    public DragonEssence() {
        super("Dragon Essence", new ItemStack(Material.WHITE_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "ROAR");
        lore.add(getEssenceLoreColor() + "Permanent strength 1");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Shift + right click: " + getEssenceColor() + "" + ChatColor.BOLD + "Activate Dragon's Breath");
        lore.add(getEssenceColor() + "Dragon's Breath: " + getEssenceLoreColor() + "Shoot a lazer beam at a player which deals immense damaage.");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Shift + left click: " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Activate Dragon summoner");
        lore.add(ChatColor.LIGHT_PURPLE + "Dragon Summoner: " + getEssenceLoreColor() + "Summon a dragon whom you may ride for 10 seconds");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Cooldown of 200 seconds.");
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
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.GRAY;
    }
}
