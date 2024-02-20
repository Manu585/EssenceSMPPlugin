package me.manu.essencesmpplugin.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SneakCropGrowthManager {
    private final Set<UUID> sneakingPlayers = ConcurrentHashMap.newKeySet();
    private final Plugin plugin;

    public SneakCropGrowthManager(Plugin plugin) {
        this.plugin = plugin;
        startGlobalSneakingTask();
    }

    private void startGlobalSneakingTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (UUID playerId : sneakingPlayers) {
                Player player = Bukkit.getPlayer(playerId);
                if (player != null && player.isSneaking()) {
                    growCropsAroundPlayer(player);
                } else {
                    sneakingPlayers.remove(playerId);
                }
            }
        }, 0L, 20L); // Adjust the period as needed.
    }

    public void addSneakingPlayer(Player player) {
        sneakingPlayers.add(player.getUniqueId());
    }

    public void removeSneakingPlayer(Player player) {
        sneakingPlayers.remove(player.getUniqueId());
    }

    private void growCropsAroundPlayer(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        int radius = 3;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block block = world.getBlockAt(loc.clone().add(x, y, z));
                    BlockData blockData = block.getBlockData();

                    if (blockData instanceof Ageable) {
                        Ageable ageable = (Ageable) blockData;
                        if (ageable.getAge() < ageable.getMaximumAge()) {
                            ageable.setAge(ageable.getAge() + 1); // Increment age by 1
                            block.setBlockData(ageable);
                        }
                    }
                }
            }
        }
    }
}
