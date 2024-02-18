package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.methods.GeneralMethods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GeneralListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void registerEssencePlayerOnJoin(PlayerJoinEvent e) {
        GeneralMethods.registerEssencePlayerOnJoin(e);
        e.getPlayer().getInventory().addItem(EssenceCreator.getTimeEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getMobEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getGravityEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getMysteryEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getSunsetEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getMistEssenceItem());
        e.getPlayer().getInventory().addItem(EssenceCreator.getAirEssenceItem());
    }
}