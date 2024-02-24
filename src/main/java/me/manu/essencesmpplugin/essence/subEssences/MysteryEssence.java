package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MysteryEssence extends Essence {
    private List<PotionEffect> negativeEffects = Arrays.asList(
            new PotionEffect(PotionEffectType.BLINDNESS, 20, 2),
            new PotionEffect(PotionEffectType.SLOW, 20, 2),
            new PotionEffect(PotionEffectType.WEAKNESS, 20, 2)
    );
    private List<PotionEffect> positiveEffects = Arrays.asList(
            new PotionEffect(PotionEffectType.REGENERATION, 20, 2),
            new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 2)
    );

    public MysteryEssence() {
        super("Mystery Essence", new ItemStack(Material.PINK_DYE));
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "" + ChatColor.BOLD + "How?!.");
        lore.add(getEssenceLoreColor() + "Give other players random effects when hitting them with a sword." + ChatColor.GRAY + " (50% chance of happening)");
        lore.add(getEssenceLoreColor() + "Grants you potion effects, when getting hit by other players.");
        lore.add(getEssenceLoreColor() + "Bows shoot random tipped arrows instead of normal arrows.");
        lore.add(getEssenceLoreColor() + "Right clicking while holding a sword to explode like a creeper.");
        lore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cooldown of 120 seconds.");
        return lore;
    }

    private void configureItemMeta() {
        ItemStack item = getEssenceItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getEssenceColor() + getEssenceName());
            meta.setLore(getEssenceLore());
            item.setItemMeta(meta);
        }
    }

    @Override
    public void handleEvent(Event e, EssencePlayer essencePlayer) {
        if (e instanceof EntityDamageByEntityEvent) {
            applyEffectOnAttack((EntityDamageByEntityEvent) e);
            applyEffectOnBeingAttacked((EntityDamageByEntityEvent) e);
        }
        if (e instanceof PlayerInteractEvent) {
            creeperExplosion((PlayerInteractEvent) e);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        return new ArrayList<>();
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    public void applyEffectOnAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player damager = (Player) e.getDamager();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(damager.getUniqueId());
        if (essencePlayer == null || !essencePlayer.hasEssenceActive(this)) return;

        if (!(e.getEntity() instanceof Player)) return;

        Random random = new Random();
        if (random.nextBoolean()) { // 50% chance
            if (EssenceSMPPlugin.getGeneralMethods().isHoldingSword(damager)) {
                PotionEffect effectToApply = negativeEffects.get(random.nextInt(negativeEffects.size()));
                ((Player) e.getEntity()).addPotionEffect(new PotionEffect(effectToApply.getType(), 80, effectToApply.getAmplifier()));
                damager.sendMessage("You have applied " + effectToApply.getType().getName() + " effect to the attacked player.");
            }
        }
    }

    public void applyEffectOnBeingAttacked(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player victim = (Player) e.getEntity();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(victim.getUniqueId());
        if (essencePlayer == null || !essencePlayer.hasEssenceActive(this)) return;

        Random random = new Random();
        PotionEffect effectToApply = positiveEffects.get(random.nextInt(positiveEffects.size()));
        victim.addPotionEffect(new PotionEffect(effectToApply.getType(), 80, effectToApply.getAmplifier()));
        victim.sendMessage("You have received a positive effect.");
    }

    public void creeperExplosion(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(player.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && EssenceSMPPlugin.getGeneralMethods().isHoldingSword(player)) {
                if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(player, "creeper_explosion")) {
                    EssenceSMPPlugin.getCooldownManager().setCooldown(player, "creeper_explosion", 120);

                    player.getWorld().getEntitiesByClass(Player.class).forEach(otherPlayer -> {
                        if (otherPlayer != player && otherPlayer.getLocation().distance(player.getLocation()) <= 4) {
                            otherPlayer.damage(12);
                        }
                    });
                    player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 20, 0.5, 0.5, 0.5, 0);
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);

                    player.sendMessage(ChatColor.GREEN + "You've used creeper explosion!");
                } else {
                    long timeLeft = EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(player, "creeper_explosion");
                    player.sendMessage(ChatColor.RED + "Creeper explosion is on cooldown. Please wait " + timeLeft + " seconds.");
                }
            }
        }
    }
}
