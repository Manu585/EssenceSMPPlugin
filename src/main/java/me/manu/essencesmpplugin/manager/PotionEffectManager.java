package me.manu.essencesmpplugin.manager;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PotionEffectManager {
    private final Map<UUID, Set<PotionEffect>> permanentEffects = new HashMap<>();

    /**
     * Applies a potion effect to a player and makes it "permanent" until explicitly removed.
     * @param player The player to apply the effect to.
     * @param effect The potion effect to apply.
     */
    public void applyPermanentEffect(Player player, PotionEffect effect) {
        permanentEffects.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>()).add(effect);
        player.addPotionEffect(effect, true);
    }

    /**
     * Removes a specific permanent potion effect from a player.
     * @param player The player to remove the effect from.
     * @param effectType The type of potion effect to remove.
     */
    public void removePermanentEffect(Player player, PotionEffectType effectType) {
        Set<PotionEffect> effects = permanentEffects.get(player.getUniqueId());
        if (effects != null) {
            effects.removeIf(effect -> effect.getType().equals(effectType));
            player.removePotionEffect(effectType);
        }
    }

    /**
     * Reapplies all permanent effects to a player. Useful for ensuring effects persist across events
     * like relogging or world changes.
     * @param player The player to reapply the effects to.
     */
    public void reapplyPermanentEffects(Player player) {
        Set<PotionEffect> effects = permanentEffects.get(player.getUniqueId());
        if (effects != null) {
            for (PotionEffect effect : effects) {
                player.addPotionEffect(effect, true);
            }
        }
    }

    /**
     * Clears all permanent effects from a player.
     * @param player The player to clear the effects from.
     */
    public void clearPermanentEffects(Player player) {
        Set<PotionEffect> effects = permanentEffects.remove(player.getUniqueId());
        if (effects != null) {
            for (PotionEffect effect : effects) {
                player.removePotionEffect(effect.getType());
            }
        }
    }
}
