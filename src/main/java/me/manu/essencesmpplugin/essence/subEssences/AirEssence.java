package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import me.manu.essencesmpplugin.manager.CooldownManager;
import me.manu.essencesmpplugin.manager.EssenceCreator;
import me.manu.essencesmpplugin.util.EssenceChatColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

import static me.manu.essencesmpplugin.config.ConfigManager.getConfig;

public class AirEssence extends Essence {
    public AirEssence() {
        super("Air Essence", new ItemStack(Material.LIGHT_BLUE_DYE));
    }

    @Override
    protected void initialize() {
        List<String> formattedLore = new ArrayList<>();
        for (String line : getConfig().getStringList("essence.airessence.essencelore")) {
            formattedLore.add(EssenceChatColor.format(line));
        }
        this.setEssenceLore(formattedLore);
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "LIGHT ON THE FEET");
        lore.add(getEssenceLoreColor() + "Grants speed 2, double jump and no fall damage.");
        lore.add(getEssenceLoreColor() + "Right click to activate " + getEssenceColor() + "" + ChatColor.BOLD + "SUGAR RUSH" + getEssenceLoreColor() + "!");
        lore.add(getEssenceCooldownLoreColor() + "" + ChatColor.ITALIC + "Sugar Rush: Hit faster, Speed boost for 10 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getConfig().getString("essence.airessence.essencename"));
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(Event e, EssencePlayer essencePlayer) {
        if (e instanceof PlayerToggleFlightEvent) {
            doubleJump((PlayerToggleFlightEvent) e, essencePlayer);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        List<PotionEffect> effect = new ArrayList<PotionEffect>();
        effect.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, getConfig().getInt("essence.airessence.passivepotioneffect.speed")));
        return effect;
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.DARK_BLUE;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.BLUE;
    }

    private void doubleJump(PlayerToggleFlightEvent event, EssencePlayer essencePlayer) {
        if (essencePlayer.hasEssenceActive(EssenceCreator.getAirEssence())) {
            Player player = Bukkit.getPlayer(essencePlayer.getUuid());
            if (player == null || !player.getGameMode().equals(GameMode.SURVIVAL) && !player.getGameMode().equals(GameMode.ADVENTURE)) {
                return;
            }

            String action = "double_jump";
            CooldownManager cooldownManager = EssenceSMPPlugin.getCooldownManager();

            if (cooldownManager.isOnCooldown(player, action)) {
                player.setAllowFlight(false); // Prevent flying if on cooldown
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);

            Vector jump = player.getLocation().getDirection().multiply(0.5).setY(1);
            player.setVelocity(player.getVelocity().add(jump));

            // Setting cooldown for double jump to 10 seconds
            cooldownManager.setCooldown(player, action, 10);
            // Re-enable double jump after cooldown expires
            Bukkit.getScheduler().runTaskLater(EssenceSMPPlugin.getInstance(), () -> player.setAllowFlight(true), 20L * 10);
        }
    }
}
