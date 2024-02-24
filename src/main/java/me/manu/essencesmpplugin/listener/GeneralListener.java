package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class GeneralListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void registerEssencePlayerOnJoin(PlayerJoinEvent e) {
        try {
            EssenceSMPPlugin.getDatabase().addPlayer(e.getPlayer());

            EssencePlayer essencePlayer = new EssencePlayer(e.getPlayer().getUniqueId());
            EssencePlayer.registerEssencePlayer(essencePlayer);
            String essenceName = EssenceSMPPlugin.getDatabase().getPlayerEssence(e.getPlayer());
            essencePlayer.activateEssenceByName(essenceName);
        } catch (SQLException ex) {
            ex.printStackTrace();


            e.getPlayer().getInventory().addItem(EssenceCreator.getTimeEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getMobEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getGravityEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getMysteryEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getSunsetEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getMistEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getAirEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getBruteEssenceItem());
            e.getPlayer().getInventory().addItem(EssenceCreator.getDarknessEssenceItem());
        }
    }
}
