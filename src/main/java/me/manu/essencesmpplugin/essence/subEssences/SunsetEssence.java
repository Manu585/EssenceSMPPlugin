package me.manu.essencesmpplugin.essence.subEssences;

import me.manu.essencesmpplugin.EssenceSMPPlugin;
import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essenceplayer.EssencePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SunsetEssence extends Essence {
    private final Map<Material, Material> smeltMap = new HashMap<>();
    public SunsetEssence() {
        super("Sunset Essence", new ItemStack(Material.ORANGE_DYE));
        smeltMap.put(Material.IRON_ORE, Material.IRON_INGOT);
        smeltMap.put(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT);
        smeltMap.put(Material.GOLD_ORE, Material.GOLD_INGOT);
        smeltMap.put(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT);
        smeltMap.put(Material.COPPER_ORE, Material.COPPER_INGOT);
        smeltMap.put(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT);
    }

    @Override
    protected void initialize() {
        this.setEssenceLore(createLore());
        configureItemMeta();
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();
        lore.add(getEssenceColor() + "This essence radiates warmth and energy.");
        lore.add(getEssenceColor() + "Right clicking while holding a sword will shoot out a Fireball.");
        lore.add(ChatColor.GRAY + "Grants Fire Resistance and extra power in the Nether.");
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
        if (e instanceof PlayerInteractEvent) {
            ShootFireBall((PlayerInteractEvent) e);
        }
        if (e instanceof EntityDamageByEntityEvent) {
            FireAspectHands((EntityDamageByEntityEvent) e);
            onFireballHit((EntityDamageByEntityEvent) e);
            RegisterFireballDamage((EntityDamageByEntityEvent) e);
        }
        if (e instanceof BlockBreakEvent) {
            AutoSmeltOres((BlockBreakEvent) e);
        }
    }

    @Override
    public List<PotionEffect> getPassivePotionEffect() {
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        return effects;
    }

    @Override
    public ChatColor getEssenceColor() {
        return ChatColor.GOLD;
    }

    @Override
    public ChatColor getEssenceLoreColor() {
        return ChatColor.YELLOW;
    }

    public void FireAspectHands(EntityDamageByEntityEvent e) {
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(e.getDamager().getUniqueId());
        if (essencePlayer.hasEssenceActive(this)) {
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                //if (e.getEntity() instanceof Player) {
                    Entity attacked = e.getEntity();
                    if (damager.getInventory().getItemInMainHand().getType().isAir()) {
                        attacked.setFireTicks(40);
                    }
                //}
            }
        }
    }

    public void RegisterFireballDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Fireball && e.getEntity() instanceof LivingEntity) {
            Fireball fireball = (Fireball) e.getDamager();
            if (fireball.hasMetadata("fireballDamage")) {
                for (MetadataValue value : fireball.getMetadata("fireballDamage")) {
                    if (value.getOwningPlugin().getDescription().getName().equals(EssenceSMPPlugin.getInstance().getDescription().getName())) {
                        int damage = value.asInt();
                        ((LivingEntity) e.getEntity()).damage(damage);
                        break;
                    }
                }
            }
        }
    }

    public void AutoSmeltOres(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());

        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            Material smeltResult = smeltMap.get(b.getType());
            if (smeltResult != null) {
                e.setDropItems(false);
                b.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(smeltResult, 1));
                b.getWorld().spawnParticle(Particle.FLAME, b.getLocation(), 20);
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.7f, 1.2f);
            }
        }
    }

    public void ShootFireBall(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        EssencePlayer essencePlayer = EssencePlayer.getEssencePlayer(p.getUniqueId());
        if (essencePlayer != null && essencePlayer.hasEssenceActive(this)) {
            if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && isHoldingSword(p)) {
                if (!EssenceSMPPlugin.getCooldownManager().isOnCooldown(p, "fireball")) {
                    EssenceSMPPlugin.getCooldownManager().setCooldown(p, "fireball", 160); // Cooldown in seconds

                    Fireball fireball = p.launchProjectile(Fireball.class);
                    fireball.setMetadata("fireballDamage", new FixedMetadataValue(EssenceSMPPlugin.getInstance(), 10)); // 10 damage equals 5 hearts

                    p.sendMessage("You've launched a fireball!");
                } else {
                    long cooldownTime = EssenceSMPPlugin.getCooldownManager().getRemainingCooldown(p, "fireball");
                    p.sendMessage("Fireball ability on cooldown. " + cooldownTime + " seconds remaining!");
                }
            }
        }
    }

    private boolean isHoldingSword(Player player) {
        Material heldItemMaterial = player.getInventory().getItemInMainHand().getType();
        return heldItemMaterial == Material.WOODEN_SWORD || heldItemMaterial == Material.STONE_SWORD ||
                heldItemMaterial == Material.IRON_SWORD || heldItemMaterial == Material.GOLDEN_SWORD ||
                heldItemMaterial == Material.DIAMOND_SWORD || heldItemMaterial == Material.NETHERITE_SWORD;
    }

    public void onFireballHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Fireball && e.getEntity() instanceof LivingEntity) {
            Fireball fireball = (Fireball) e.getDamager();
            if (fireball.hasMetadata("fireballDamage")) {
                for (MetadataValue value : fireball.getMetadata("fireballDamage")) {
                    if (value.getOwningPlugin().getDescription().getName().equals(EssenceSMPPlugin.getInstance().getDescription().getName())) {
                        int damage = value.asInt();
                        ((LivingEntity) e.getEntity()).damage(damage);
                        break;
                    }
                }
            }
        }
    }
}
