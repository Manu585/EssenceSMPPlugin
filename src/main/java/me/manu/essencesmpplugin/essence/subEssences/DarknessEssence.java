package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.util.SkinChanger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DarknessEssence extends Essence {
    public DarknessEssence() {
        super("Darkness Essence", new ItemStack(Material.BLACK_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "FEAR THE DARKNESS");
        lore.add(getEssenceLoreColor() + "10% chance to blind players when hitting them.");
        lore.add(getEssenceLoreColor() + "can't receive blindness.");
        lore.add(getEssenceLoreColor() + "right click with a sword to activate " + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "CONSUMPTION");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Consumption: Give all nearby players blindness and weakness for 6 seoncds.");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
        lore.add(getEssenceLoreColor() + "Shift + Right click activates " + getEssenceColor() + "" + ChatColor.BOLD + "DARKNESS OVERLORD");
        lore.add(getEssenceLoreColor() + "" + ChatColor.ITALIC + "Darkness Overlord: Turn into a random player online for 120 seconds.");
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
            applyBlindness((EntityDamageByEntityEvent) e);
        }
        if (e instanceof PlayerInteractEvent) {
            activateConsumption((PlayerInteractEvent) e);
            activateDarknessOverlord((PlayerInteractEvent) e);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        return new ArrayList<>();
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.BLACK;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.GRAY;
    }

    public void applyBlindness(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player damager = (Player) e.getDamager();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(damager.getUniqueId());
        if (essencePlayer == null || !essencePlayer.hasEssenceActive(this)) return;

        if (!(e.getEntity() instanceof Player)) return;

        if (chance(10)) {
            if (EssenceSMPPlugin.getGeneralMethods().isHoldingSword(damager)) {
                ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1));
            }
        }
    }

    public void activateConsumption(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (EssenceSMPPlugin.getGeneralMethods().isHoldingSword(player)) {
                    if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(player, "consumption")) {
                        EssenceSMPPlugin.getCooldownManager().setCooldown(player, "consumption", 120);
                        player.getNearbyEntities(5, 5, 5).forEach(entity -> {
                            if (entity instanceof Player) {
                                Player nearbyPlayer = (Player) entity;
                                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 2));
                                nearbyPlayer.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 120, 2));
                            }
                        });
                        player.sendMessage(ChatColor.GRAY + "You have activated Consumption.");
                    } else {
                        player.sendMessage(ChatColor.RED + "Consumption still on cooldown!");
                    }
                }
            }
        }
    }

    public void activateDarknessOverlord(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if (player.isSneaking() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
                if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(player, "darkness_overlord")) {
                    EssenceSMPPlugin.getCooldownManager().setCooldown(player, "darkness_overlord", 300);
                    SkinChanger changer = new SkinChanger(player.getUniqueId(), player.getName(), player.getPlayerProfile().getTextures());
                    player.sendMessage("Old skin: " + player.getPlayerProfile().getTextures().toString());
                    changer.changePlayerSkin(player);
                    player.sendMessage("New skin: " + changer.getPlayer_new_texture());
                } else {
                    player.sendMessage(ChatColor.RED + "Darkness Overlord is on cooldown!");
                }
            }
        }
    }


    public boolean chance(int percentage) {
        Random random = new Random();
        return random.nextInt(100) < percentage;
    }
}
