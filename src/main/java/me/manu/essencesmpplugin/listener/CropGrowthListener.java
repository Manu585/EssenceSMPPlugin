package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.manager.SneakCropGrowthManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class CropGrowthListener implements Listener {
    private final SneakCropGrowthManager manager;

    public CropGrowthListener(SneakCropGrowthManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer != null && essencePlayer.hasEssenceActive(EssenceCreator.getTimeEssence())) {
            if (event.isSneaking()) {
                p.sendMessage("YAYAYAY");
                manager.addSneakingPlayer(event.getPlayer());
            } else {
                manager.removeSneakingPlayer(event.getPlayer());
            }
        }
    }
}
