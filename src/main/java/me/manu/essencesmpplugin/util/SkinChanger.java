package me.manu.essencesmpplugin.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerTextures;

import java.util.UUID;

public class SkinChanger {
    private final UUID player_uuid;
    private final String player_original_name;
    private final PlayerTextures player_original_textures;
    private PlayerTextures player_new_texture;

    public SkinChanger(UUID playerUuid, String playerOriginalName, PlayerTextures playerOriginalTextures) {
        player_uuid = playerUuid;
        player_original_name = playerOriginalName;
        player_original_textures = playerOriginalTextures;
    }

    public void changePlayerSkin(Player playerToChange) {
        PlayerProfile playerProfile = playerToChange.getPlayerProfile();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            player_new_texture = p.getPlayerProfile().getTextures();
            playerProfile.setTextures(player_new_texture);
        }
    }

    public static void reloadPlayer(Player player) {

    }

    public String getPlayer_original_name() {
        return this.player_original_name;
    }

    public PlayerTextures getPlayer_original_textures() {
        return this.player_original_textures;
    }

    public PlayerTextures getPlayer_new_texture() {
        return this.player_new_texture;
    }

    public UUID getPlayer_uuid() {
        return player_uuid;
    }
}
