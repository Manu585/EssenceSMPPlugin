package me.manu.essencesmpplugin.listener;

import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SunsetEssenceListener implements Listener {

    @EventHandler
    public void registerEssencePlayerOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer == null) {
            EssencePlayer newPlayer = new EssencePlayer(p.getUniqueId());
            EssencePlayer.registerEssencePlayer(newPlayer);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        assert essencePlayer != null;
        p.getInventory().addItem(EssenceCreator.getAirEssenceItem());
        p.getInventory().addItem(EssenceCreator.getBruteEssenceItem());
        p.getInventory().addItem(EssenceCreator.getDarknessEssenceItem());
        p.getInventory().addItem(EssenceCreator.getDragonEssenceItem());
        p.getInventory().addItem(EssenceCreator.getGravityEssenceItem());
        p.getInventory().addItem(EssenceCreator.getMistEssenceItem());
        p.getInventory().addItem(EssenceCreator.getMobEssenceItem());
        p.getInventory().addItem(EssenceCreator.getMysteryEssenceItem());
        p.getInventory().addItem(EssenceCreator.getStrengthEssenceItem());
        p.getInventory().addItem(EssenceCreator.getSunsetEssenceItem());
        p.getInventory().addItem(EssenceCreator.getMistEssenceItem());
        p.getInventory().addItem(EssenceCreator.getTimeEssenceItem());
    }

    @EventHandler
    public void onRightClickWithOrangeEssence(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer == null) {
            EssencePlayer newPlayer = new EssencePlayer(p.getUniqueId());
            EssencePlayer.registerEssencePlayer(newPlayer);
        }
        if (e.getItem() != null) {
            if (e.getItem().isSimilar(EssenceCreator.getSunsetEssenceItem())) {
                if (essencePlayer.hasEssenceActive(essencePlayer, EssenceCreator.getSunsetEssence())) {
                    essencePlayer.deactivateEssence(essencePlayer, EssenceCreator.getSunsetEssence());
                    p.sendMessage("Sunset Essence deactivated!");
                } else {
                    essencePlayer.activateEssence(essencePlayer, EssenceCreator.getSunsetEssence());
                    p.sendMessage("Sunset Essence activated!");
                }
            }
        }
    }
}