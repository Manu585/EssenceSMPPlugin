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

public class MobEssence extends Essence {
    public MobEssence() {
        super("Mob Essence", new ItemStack(Material.GREEN_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "BECOME WHAT YOU HATE");
        lore.add(getEssenceLoreColor() + "Everytime you kill a mob, you get a special ability depending on the mob you killed.");
        lore.add(ChatColor.BLACK + "Spider: " + getEssenceLoreColor() + "be able to craft cobwebs with forming a x with string.");
        lore.add(ChatColor.DARK_GREEN + "Creeper: " + getEssenceLoreColor() + "Be able to explode by right clicking with a flint and steel. " + ChatColor.GRAY + "(Cooldown of 60 seconds)");
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
