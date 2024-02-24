package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if (e instanceof EntityDamageByEntityEvent) {
            increaseAxeDamage((EntityDamageByEntityEvent) e);
        }
        if (e instanceof PlayerInteractEvent) {
            fullHog((PlayerInteractEvent) e);
        }
        if (e instanceof EntityTargetEvent) {
            piglinsIgnore((EntityTargetEvent) e);
        }
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

    public void increaseAxeDamage(EntityDamageByEntityEvent e) {
        Player attacker = (Player) e.getDamager();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(attacker.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if (EssenceSMPPlugin.getGeneralMethods().isHoldingAxe(attacker)) {
                double damageDealt = e.getDamage();
                e.setDamage(damageDealt + 2);
            }
        }
    }

    @EventHandler
    public void piglinsIgnore(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

            if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
                if (event.getEntityType() == EntityType.PIGLIN || event.getEntityType() == EntityType.PIGLIN_BRUTE) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public void fullHog(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (EssenceSMPPlugin.getGeneralMethods().isHoldingAxe(p)) {
                    if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(p, "full_hog")) {
                        EssenceSMPPlugin.getCooldownManager().setCooldown(p, "full_hog", 160);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 2));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 300, 3));
                    } else {
                        p.sendMessage("FullHog is still on cooldown for " + EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(p, "full_hog") + " seconds!");
                    }
                }
            }
        }
    }
}
