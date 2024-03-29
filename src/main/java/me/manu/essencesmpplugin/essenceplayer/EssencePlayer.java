package me.manu.essencesmpplugin.essenceplayer;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.sql.SQLException;
import java.util.*;

public class EssencePlayer {
    private static final Map<UUID, EssencePlayer> ESSENCE_PLAYERS = new HashMap<>();
    private final UUID uuid;
    private final Set<Essence> activeEssences = new HashSet<>();
    private boolean isInWater = false;

    public EssencePlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void activateEssence(Essence essence) {
        Player player = getPlayer();
        if (player == null) {
            return;
        }
        deactivateCurrentEssence();
        activeEssences.add(essence);
        try {
            EssenceSMPPlugin.getDatabase().updateEssence(player, essence);
        } catch (SQLException e) { e.printStackTrace(); }

        List<PotionEffect> effects = essence.getPassivePotionEffect();
        for (PotionEffect effect : effects) {
            EssenceSMPPlugin.getPotionEffectManager().applyPermanentEffect(player, effect);
        }
    }

    public void activateEssenceByName(String name) {
        Essence essence = EssenceCreator.getEssenceByName(name);
        if (essence != null) {
            activateEssence(essence);
        }
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



    //ESSENCE RELATED
    public boolean isInWater() {
        return isInWater;
    }

    public void setInWater(boolean isInWater) {
        this.isInWater = isInWater;
    }
}
