package me.manu.essencesmpplugin.methods;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class GeneralMethods {
    public boolean isHoldingSword(Player player) {
        Material heldItemMaterial = player.getInventory().getItemInMainHand().getType();
        return heldItemMaterial == Material.WOODEN_SWORD || heldItemMaterial == Material.STONE_SWORD ||
                heldItemMaterial == Material.IRON_SWORD || heldItemMaterial == Material.GOLDEN_SWORD ||
                heldItemMaterial == Material.DIAMOND_SWORD || heldItemMaterial == Material.NETHERITE_SWORD;
    }

    public boolean isHoldingAxe(Player player) {
        Material heldItemMaterial = player.getInventory().getItemInMainHand().getType();
        return heldItemMaterial == Material.WOODEN_AXE || heldItemMaterial == Material.STONE_AXE ||
                heldItemMaterial == Material.IRON_AXE || heldItemMaterial == Material.GOLDEN_AXE ||
                heldItemMaterial == Material.DIAMOND_AXE || heldItemMaterial == Material.NETHERITE_AXE;
    }

    public void removeAllPotionEffects(Player player) {
        for (PotionEffectType effect : PotionEffectType.values()) {
            if (player.hasPotionEffect(effect)) {
                player.removePotionEffect(effect);
            }
        }
    }
}
