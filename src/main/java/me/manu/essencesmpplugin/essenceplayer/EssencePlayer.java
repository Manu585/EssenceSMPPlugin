package me.manu.essencesmpplugin.essenceplayer;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class EssencePlayer {
    private static final Map<UUID, EssencePlayer> ESSENCE_PLAYERS = new HashMap<>();
    private final UUID uuid;
    private final Set<Essence> activeEssences = new HashSet<>();

    public EssencePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void activateEssence(Essence essence) {
        deactivateCurrentEssence();
        activeEssences.add(essence);

        // Apply essence effects or logic
        // Example: essence.applyEffects(this.player);
        //EssenceSMPPlugin.getPotionEffectManager().applyPermanentEffect(Bukkit.getPlayer(this.uuid), essence.getPotionEffect());
    }

    // Deactivates any currently active essences.
    public void deactivateCurrentEssence() {
        activeEssences.forEach(essence -> {
            EssenceSMPPlugin.getGeneralMethods().removeAllPotionEffects(Bukkit.getPlayer(this.uuid));
        });
        activeEssences.clear();
    }

    // Deactivates a specific essence
    public void deactivateEssence(Essence essence) {
        this.activeEssences.remove(essence);
    }

    public boolean hasEssenceActive(Essence essence) {
        if (activeEssences.isEmpty()) {
            return false;
        } else if (activeEssences.contains(essence)) {
            return true;
        } else
            return false;
    }

    public Set<Essence> getActiveEssences() {
        EssencePlayer essencePlayer = ESSENCE_PLAYERS.get(this.uuid);
        if (essencePlayer == null) {
            return Collections.emptySet(); // Returns an empty set if the player has no active essences
        }
        return new HashSet<>(essencePlayer.activeEssences);
    }


    // Gets the EssencePlayer instance for the specified UUID
    public static EssencePlayer getEssencePlayer(UUID uuid) {
        return ESSENCE_PLAYERS.get(uuid);
    }

    // Gets the Player object associated with this EssencePlayer
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    // Registers a new EssencePlayer
    public static void registerEssencePlayer(EssencePlayer player) {
        ESSENCE_PLAYERS.put(player.getUuid(), player);
    }

    // Returns all registered EssencePlayers
    public static Map<UUID, EssencePlayer> getPlayers() {
        return new HashMap<>(ESSENCE_PLAYERS);
    }
}
