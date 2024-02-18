package me.manu.essencesmpplugin.methods;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
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

    public void onRightClickWithEssence(PlayerInteractEvent e, Essence essence) {
        ItemStack item = essence.getEssenceItem();
        e.getPlayer().sendMessage("BEFORE IF");
        System.out.println("BEFORE IF");
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(item)) {
                e.getPlayer().sendMessage("IF ITEM IN HAND AND RIGHT CLICK");
                System.out.println("IF ITEM IN HAND AND RIGHT CLICK");
                EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getPlayer().getUniqueId());
                if (!essencePlayer.hasEssenceActive(essence)) {
                    // Deactivate all currently active essences
                    essencePlayer.getActiveEssences().forEach(essencePlayer::deactivateEssence);

                    // Activate the new essence
                    essencePlayer.activateEssence(essence);
                    e.getPlayer().getInventory().removeItemAnySlot(item.asOne()); // Ensure only one item is removed
                    e.getPlayer().sendMessage("Activated " + essence.getEssenceName());
                    System.out.println("Activated " + essence.getEssenceName());
                } else {
                    e.getPlayer().sendMessage(essence.getEssenceName() + " is already active.");
                    System.out.println(essence.getEssenceName() + " is already active.");
                }
            }
        }
    }

    public void removeAllPotionEffects(Player player) {
        for (PotionEffectType effect : PotionEffectType.values()) {
            if (effect != null && player.hasPotionEffect(effect)) {
                player.removePotionEffect(effect);
            }
        }
    }
}
