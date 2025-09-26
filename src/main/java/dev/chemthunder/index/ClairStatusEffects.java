package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import dev.chemthunder.effect.Poltergeist;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface ClairStatusEffects {
    RegistryEntry<StatusEffect> POLTERGEIST = create("fractured", new Poltergeist());



    private static RegistryEntry<StatusEffect> create(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Clairvoyance.id(name), statusEffect);
    }

    static void init() {
    }
}
