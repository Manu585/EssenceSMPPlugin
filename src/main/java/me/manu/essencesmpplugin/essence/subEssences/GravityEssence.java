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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GravityEssence extends Essence {
    public GravityEssence() {
        super("Gravity Essence", new ItemStack(Material.PURPLE_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "DEFINE GRAVITY");
        lore.add(getEssenceLoreColor() + "Shift + right click a player to stop their movement for 5 seconds");
        lore.add(getEssenceLoreColor() + "Shift + left click gives everyone in a 5 block radius levitation for 4 seconds");
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
        if (e instanceof PlayerInteractEvent) {
            stopPlayerMovement((PlayerInteractEvent) e);
            levitatePlayers((PlayerInteractEvent) e);
        }
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

    public void stopPlayerMovement(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this) && player.isSneaking() &&
                (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(player, "stop_movement")) {
                int radius = 5;
                List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

                nearbyEntities.stream()
                        .filter(entity -> entity instanceof Player && entity != player)
                        .map(entity -> (Player) entity)
                        .forEach(targetPlayer -> {
                            // Apply both slowness and jump reduction effects
                            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 255, false, true));
                            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5 * 20, -255, false, true));
                        });

                EssenceSMPPlugin.getCooldownManager().setCooldown(player, "stop_movement", 60);
                player.sendMessage(ChatColor.GREEN + "Nearby players have been immobilized.");
            } else {
                long timeLeft = EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(player, "stop_movement");
                player.sendMessage(ChatColor.RED + "Ability on cooldown. Please wait " + timeLeft + " seconds.");
            }
        }
    }

    public void levitatePlayers(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this) && player.isSneaking() &&
                (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)) {

            if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(player, "levitate_players")) {
                int radius = 5;
                List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);

                nearbyEntities.stream()
                        .filter(entity -> entity instanceof Player && entity != player)
                        .map(entity -> (Player) entity)
                        .forEach(targetPlayer -> {
                            targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5 * 20, 2, false, false));
                        });

                EssenceSMPPlugin.getCooldownManager().setCooldown(player, "levitate_players", 60);
                player.sendMessage(ChatColor.GREEN + "Nearby players are levitating.");
            } else {
                long timeLeft = EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(player, "levitate_players");
                player.sendMessage(ChatColor.RED + "Ability on cooldown. Please wait " + timeLeft + " seconds.");
            }
        }
    }
}
