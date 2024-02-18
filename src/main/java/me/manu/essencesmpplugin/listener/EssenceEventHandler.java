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
        // Check if the action is a right-click action.
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
                        // Deactivate any currently active essence.
                        essencePlayer.deactivateCurrentEssence();

                        // Activate the new essence.
                        essencePlayer.activateEssence(essence);
                        e.getPlayer().sendMessage("Activated " + essence.getEssenceName() + ".");

                        // Optionally, consume the essence item or perform additional logic as needed.
                    }
                }
            }
        }
    }

    private Essence getEssenceForItem(ItemStack item) {
        // This method should check if the given item matches any known essence items
        // and return the corresponding Essence instance if found.
        // This is a placeholder for the actual implementation.
        return EssenceCreator.getEssenceByItemStack(item); // Example method call, implement accordingly.
    }

    private void activateEssence(EssencePlayer essencePlayer, Essence essence, PlayerInteractEvent e) {
        // Deactivate all currently active essences
        essencePlayer.getActiveEssences().forEach(activeEssence -> essencePlayer.deactivateEssence(activeEssence));

        // Activate the new essence
        essencePlayer.activateEssence(essence);
        e.getPlayer().sendMessage("Activated " + essence.getEssenceName());

        // Handle item removal or any other activation logic here.
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
