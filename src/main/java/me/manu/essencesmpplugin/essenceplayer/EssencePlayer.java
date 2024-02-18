package me.manu.essencesmpplugin.essenceplayer;

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

    public void activateEssence(EssencePlayer player, Essence essence) {
        EssencePlayer essencePlayer = ESSENCE_PLAYERS.computeIfAbsent(player.getUuid(), EssencePlayer::new);
        essencePlayer.activeEssences.add(essence);
    }

    public void deactivateEssence(EssencePlayer player, Essence essence) {
        EssencePlayer essencePlayer = ESSENCE_PLAYERS.get(player.getUuid());
        if (essencePlayer != null) {
            essencePlayer.activeEssences.remove(essence);
        }
    }

    public boolean hasEssenceActive(EssencePlayer player, Essence essence) {
        if (ESSENCE_PLAYERS.get(player.getUuid()) == null) {
            return false;
        } else if (activeEssences.isEmpty()) {
            return false;
        } else if (activeEssences.contains(essence)) {
            return true;
        } else
            return false;
    }

    public String getActiveEssences(EssencePlayer player) {
        if (ESSENCE_PLAYERS.get(player.getUuid()) == null) {
            return "No active essences";
        } else if (activeEssences.isEmpty()) {
            return "No active essences";
        } else {
            return activeEssences.toString();
        }
    }

    // Gets the EssencePlayer instance for the specified UUID
    public static EssencePlayer getEssencePlayer(UUID uuid) {
        return ESSENCE_PLAYERS.get(uuid);
    }

    // Gets the Player object associated with this EssencePlayer
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public static void registerEssencePlayer(EssencePlayer player) {
        ESSENCE_PLAYERS.put(player.getUuid(), player);
    }

    // Returns all registered EssencePlayers
    public static Map<UUID, EssencePlayer> getPlayers() {
        return new HashMap<>(ESSENCE_PLAYERS);
    }
}
