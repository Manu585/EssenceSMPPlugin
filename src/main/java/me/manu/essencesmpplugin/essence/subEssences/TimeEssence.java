package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TimeEssence extends Essence {

    public TimeEssence() {
        super("Time Essence", new ItemStack(Material.BROWN_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "Feel the time flow in you.");
        lore.add(getEssenceColor() + "Grants double xp, haste 2, regeneration 1, hero of the village 3");
        lore.add(getEssenceColor() + "Grow nearby crops around you when sneaking.");
        lore.add(getEssenceColor() + "Shift + Right click to activate " + ChatColor.GRAY + ChatColor.BOLD + "Time Bender" + ChatColor.RESET + "" + getEssenceColor() + ".");
        lore.add(ChatColor.GRAY + "Time Bender: " + getEssenceColor() + "Give nearby players slow falling and slowness for 10 seconds.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "cooldown of 90 seconds.");
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
        if (e instanceof PlayerExpChangeEvent) {
            DoubleXP((PlayerExpChangeEvent) e);
        }
        if (e instanceof PlayerInteractEvent) {
            TimeBender((PlayerInteractEvent) e);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 2));
        effects.add(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
        return effects;
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.YELLOW;
    }

    public void DoubleXP(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        int gatheredXP = e.getAmount();

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            e.setAmount(gatheredXP * 2);
        }
    }

    public void TimeBender(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer != null && essencePlayer.hasEssenceActive(this) && p.isSneaking() && e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(p, "time_bender")) {
                int radius = 10;
                List<Entity> nearbyEntities = p.getNearbyEntities(radius, radius, radius);
                for (Entity entity : nearbyEntities) {
                    if (entity instanceof Player) {
                        Player target = (Player) entity;
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 200, 0));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
                    }
                }
                EssenceSMPPlugin.getCooldownManager().setCooldown(p, "time_bender", 90);
                p.sendMessage(ChatColor.GREEN + "Time Bender activated!");
            } else {
                p.sendMessage(ChatColor.RED + "Time Bender is on cooldown.");
            }
        }
    }
}
