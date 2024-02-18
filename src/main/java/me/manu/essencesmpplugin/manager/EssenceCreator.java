package me.manu.essencesmpplugin.manager;

import me.manu.essencesmpplugin.essence.Essence;
import me.manu.essencesmpplugin.essence.subEssences.*;
import org.bukkit.inventory.ItemStack;

public class EssenceCreator {
    private static Essence airEssence;
    private static Essence bruteEssence;
    private static Essence darknessEssence;
    private static Essence dragonEssence;
    private static Essence gravityEssence;
    private static Essence mistEssence;
    private static Essence mobEssence;
    private static Essence mysteryEssence;
    private static Essence strengthEssence;
    private static Essence sunsetEssence;
    private static Essence timeEssence;


    public static void initEssences() {
        airEssence = new AirEssence();
        bruteEssence = new BruteEssence();
        darknessEssence = new DarknessEssence();
        dragonEssence = new DragonEssence();
        gravityEssence = new GravityEssence();
        mistEssence = new MistEssence();
        mobEssence = new MobEssence();
        mysteryEssence = new MysteryEssence();
        strengthEssence = new StrengthEssence();
        sunsetEssence = new SunsetEssence();
        timeEssence = new TimeEssence();
    }


    /**
     * Getters for the essences
     * @return the essence
     */
    public static Essence getAirEssence() { return airEssence; }
    public static Essence getBruteEssence() { return bruteEssence; }
    public static Essence getDarknessEssence() { return darknessEssence; }
    public static Essence getDragonEssence() { return dragonEssence; }
    public static Essence getGravityEssence() { return gravityEssence; }
    public static Essence getMistEssence() { return mistEssence; }
    public static Essence getMobEssence() { return mobEssence; }
    public static Essence getMysteryEssence() { return mysteryEssence; }
    public static Essence getStrengthEssence() { return strengthEssence; }
    public static Essence getSunsetEssence() { return sunsetEssence; }
    public static Essence getTimeEssence() { return timeEssence; }

    /**
     * Getters for the essence items
     * @return the essence item
     */
    public static ItemStack getAirEssenceItem() { return airEssence.getEssenceItem(); }
    public static ItemStack getBruteEssenceItem() { return bruteEssence.getEssenceItem(); }
    public static ItemStack getDarknessEssenceItem() { return darknessEssence.getEssenceItem(); }
    public static ItemStack getDragonEssenceItem() { return dragonEssence.getEssenceItem(); }
    public static ItemStack getGravityEssenceItem() { return gravityEssence.getEssenceItem(); }
    public static ItemStack getMistEssenceItem() { return mistEssence.getEssenceItem(); }
    public static ItemStack getMobEssenceItem() { return mobEssence.getEssenceItem(); }
    public static ItemStack getMysteryEssenceItem() { return mysteryEssence.getEssenceItem(); }
    public static ItemStack getStrengthEssenceItem() { return strengthEssence.getEssenceItem(); }
    public static ItemStack getSunsetEssenceItem() { return sunsetEssence.getEssenceItem(); }
    public static ItemStack getTimeEssenceItem() { return timeEssence.getEssenceItem(); }
}
