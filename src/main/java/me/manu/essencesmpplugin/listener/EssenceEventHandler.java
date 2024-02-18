package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

public class EssenceEventHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().isRightClick()) {
            ItemStack item = e.getItem();
            if (item != null) {
                Essence essence = EssenceCreator.getEssenceByItemStack(item);
                if (essence != null) {
                    EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());

                    if (essencePlayer.hasEssenceActive(essence)) {
                        e.getPlayer().sendMessage(essence.getEssenceName() + " is already active.");
                    } else {
                        essencePlayer.deactivateCurrentEssence();

                        essencePlayer.activateEssence(essence);
                        e.getPlayer().sendMessage("Activated " + essence.getEssenceName() + ".");
                    }
                }
            }
        }
    }

    private Essence getEssenceForItem(ItemStack item) {
        return EssenceCreator.getEssenceByItemStack(item);
    }

    private void activateEssence(EssencePlayer essencePlayer, Essence essence, PlayerInteractEvent e) {
        essencePlayer.getActiveEssences().forEach(activeEssence -> essencePlayer.deactivateEssence(activeEssence));

        essencePlayer.activateEssence(essence);
        e.getPlayer().sendMessage("Activated " + essence.getEssenceName());
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());
        if (essencePlayer != null) {
            essencePlayer.getActiveEssences().forEach(essence -> essence.handleEvent(event, essencePlayer));
        }
    }
}
