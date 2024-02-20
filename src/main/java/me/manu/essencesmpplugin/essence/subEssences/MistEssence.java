package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MistEssence extends Essence {
    public MistEssence() {
        super("Mist Essence", new ItemStack(Material.BLUE_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Feel the strength of the ocean.");
        lore.add(ChatColor.BLUE + "Grants Dolphin's Grace, strength 2, resistance and +4 hearts when in water.");
        lore.add(ChatColor.BLUE + "Reverse players breathing when right clicking them.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Lasts 40 seconds with a cooldown of 240 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.DARK_BLUE + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(Event e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerMoveEvent) {
            onPlayerMove((PlayerMoveEvent) e);
        }
        if (e instanceof PlayerInteractAtEntityEvent) {
            reverseBreathing((PlayerInteractAtEntityEvent) e);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        return new ArrayList<>(); // NO PASSIVE POTION EFFECTS
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.DARK_BLUE;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.BLUE;
    }

    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer.hasEssenceActive(this)) {
            boolean isInLiquid = p.getLocation().getBlock().isLiquid();
            if (isInLiquid && !essencePlayer.isInWater()) {
                EssenceSMPPlugin.getPotionEffectManager().applyPermanentEffect(p, new PotionEffect(PotionEffectType.DOLPHINS_GRACE, Integer.MAX_VALUE, 1));
                EssenceSMPPlugin.getPotionEffectManager().applyPermanentEffect(p, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
                EssenceSMPPlugin.getPotionEffectManager().applyPermanentEffect(p, new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                essencePlayer.setInWater(true);
            } else if (!isInLiquid && essencePlayer.isInWater()) {
                EssenceSMPPlugin.getPotionEffectManager().removePermanentEffect(p, PotionEffectType.DOLPHINS_GRACE);
                EssenceSMPPlugin.getPotionEffectManager().removePermanentEffect(p, PotionEffectType.INCREASE_DAMAGE);
                EssenceSMPPlugin.getPotionEffectManager().removePermanentEffect(p, PotionEffectType.HEALTH_BOOST);
                essencePlayer.setInWater(false);
            }
        }
    }

    public void reverseBreathing(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity rce = e.getRightClicked();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (rce instanceof Player) {
            if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(p, "reverse_breathing")) {
                if (essencePlayer.hasEssenceActive(this)) {
                    Player rcp = (Player) rce;
                    EssenceSMPPlugin.getCooldownManager().setCooldown(p, "reverse_breathing", 240);
                    p.sendMessage(ChatColor.GREEN + "Reverse Breathing activated!");
                    new BukkitRunnable() {
                        int count = 0;

                        @Override
                        public void run() {
                            if (count >= 40) {
                                this.cancel();
                                return;
                            }
                            rcp.setRemainingAir(rcp.getMaximumAir());
                            count++;
                        }
                    }.runTaskTimer(EssenceSMPPlugin.getInstance(), 0, 20);
                }
            } else {
                p.sendMessage(ChatColor.RED + "Reverse Breathing still on cooldown for " + EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(p, "reverse_breathing") + " seconds!");
            }
        }
    }
}
