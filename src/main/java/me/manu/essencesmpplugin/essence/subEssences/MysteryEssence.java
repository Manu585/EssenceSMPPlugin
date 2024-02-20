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

public class MysteryEssence extends Essence {
    public MysteryEssence() {
        super("Mystery Essence", new ItemStack(Material.PINK_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "How?!.");
        lore.add(getEssenceLoreColor() + "Give other players random effects when hitting them with a sword." + ChatColor.GRAY + " (50% chance of happening)");
        lore.add(getEssenceLoreColor() + "Grants you potion effects, when getting hit by other players.");
        lore.add(getEssenceLoreColor() + "Bows shoot random tipped arrows instead of normal arrows.");
        lore.add(getEssenceLoreColor() + "Right clicking while holding a sword to explode like a creeper.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
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
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.LIGHT_PURPLE;
    }
}
