package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.util.EssenceChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class EssenceEventHandler implements Listener {

    // HAS TO BE THERE
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().isRightClick()) {
            ItemStack item = e.getItem();
            if (item != null) {
                Essence essence = EssenceCreator.getEssenceByItemStack(item);
                if (essence != null) {
                    EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());

                    // Check if this essence is already active, if so, inform the player.
                    if (essencePlayer.hasEssenceActive(essence)) {
                        e.getPlayer().sendMessage(essence.getEssenceName() + " is already active.");
                    } else {
                        // Activate the new essence.
                        essencePlayer.activateEssence(essence);
                        e.getPlayer().sendMessage(EssenceChatColor.activatedEssenceMessage(essence));
                        e.getPlayer().getInventory().removeItem(e.getItem().subtract(1));
                    }
                }
            }
        }
    }

    // PERFORM SUB ESSENCES METHODS
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onRightClickAtEntity(PlayerInteractAtEntityEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        Player player = (Player) e.getDamager();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        Player p = (Player) e.getTarget();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(e, essencePlayer));
        }
    }
}
