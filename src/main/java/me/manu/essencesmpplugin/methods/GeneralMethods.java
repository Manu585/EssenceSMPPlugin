package me.manu.essencesmpplugin.methods;

import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

public class GeneralMethods {

    public static void registerEssencePlayerOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer == null) {
            EssencePlayer newPlayer = new EssencePlayer(p.getUniqueId());
            EssencePlayer.registerEssencePlayer(newPlayer);
        }
    }

//    public void onRightClickWithEssence(PlayerInteractEvent e) {
//        e.getPlayer().sendMessage("HEYHHEYHYEHYEHHYEHEYH");
//        if (e.getAction().isRightClick()) {
//            e.getPlayer().sendMessage("RIGHT CLICKED");
//            ItemStack item = e.getItem();
//            if (item != null) {
//                Essence essence = EssenceCreator.getEssenceByItemStack(item);
//                e.getPlayer().sendMessage("Essence: " + essence.getEssenceName());
//                if (essence != null) {
//                    EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());
//
//                    // Check if this essence is already active, if so, inform the player.
//                    if (essencePlayer.hasEssenceActive(essence)) {
//                        e.getPlayer().sendMessage(essence.getEssenceName() + " is already active.");
//                    } else {
//                        // Deactivate any currently active essence.
//                        essencePlayer.deactivateCurrentEssence();
//
//                        // Activate the new essence.
//                        essencePlayer.activateEssence(essence);
//                        e.getPlayer().sendMessage("Activated " + essence.getEssenceName() + ".");
//
//                        // Optionally, consume the essence item or perform additional logic as needed.
//                    }
//                }
//            }
//        }
//    }

    public void removeAllPotionEffects(Player player) {
        for (PotionEffectType effect : PotionEffectType.values()) {
            if (player.hasPotionEffect(effect)) {
                player.removePotionEffect(effect);
            }
        }
    }
}
