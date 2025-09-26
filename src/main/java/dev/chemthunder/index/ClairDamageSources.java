package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface ClairDamageSources {
    RegistryKey<DamageType> WAYPOINT = of("waypoint");

    static DamageSource waypoint(LivingEntity entity) {
        return entity.getDamageSources().create(WAYPOINT); }


    private static RegistryKey<DamageType> of(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Clairvoyance.id(name));
    }
}
