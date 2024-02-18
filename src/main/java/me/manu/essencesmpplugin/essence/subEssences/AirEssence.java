package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.CooldownManager;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class AirEssence extends Essence {
    public AirEssence() {
        super("Air Essence", createLore(), new ItemStack(Material.LIGHT_BLUE_DYE));
        configureItemMeta();
    }

    private static List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "LIGHT ON THE FEET");
        lore.add(ChatColor.BLUE + "Grants speed 2, double jump and no fall damage.");
        lore.add(ChatColor.BLUE + "Right click to activate " + ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "SUGAR RUSH" + ChatColor.BLUE + "!");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Sugar Rush: Hit faster, Speed boost for 10 seconds.");
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
    public void handleEvent(PlayerEvent e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerToggleFlightEvent) {
            e.getPlayer().sendMessage("TEST1");
            doubleJump((PlayerToggleFlightEvent) e, essencePlayer);
        } else if (e instanceof PlayerInteractEvent) {
            System.out.println("TEST2");
            e.getPlayer().sendMessage("TEST2");
            EssenceSMPPlugin.getGeneralMethods().onRightClickWithEssence((PlayerInteractEvent) e, EssenceCreator.getAirEssence());
        }
    }

    @Override
    public PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2);
    }

    private void doubleJump(PlayerToggleFlightEvent event, EssencePlayer essencePlayer) {
        event.getPlayer().sendMessage("Double jump");
        if (essencePlayer.hasEssenceActive(EssenceCreator.getAirEssence())) {
            Player player = Bukkit.getPlayer(essencePlayer.getUuid());
            if (player == null || !player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) {
                return;
            }

            String action = "double_jump";
            CooldownManager cooldownManager = EssenceSMPPlugin.getCooldownManager();

            if (cooldownManager.isOnCooldown(player, action)) {
                player.setAllowFlight(false); // Prevent flying if on cooldown
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            Vector jump = player.getLocation().getDirection().multiply(0.5).setY(1);
            player.setVelocity(player.getVelocity().add(jump));

            // Setting cooldown for double jump to 10 seconds
            cooldownManager.setCooldown(player, action, 10);
            // Re-enable double jump after cooldown expires
            Bukkit.getScheduler().runTaskLater(EssenceSMPPlugin.getInstance(), () -> player.setAllowFlight(true), 20L * 10);
        }
    }
}
