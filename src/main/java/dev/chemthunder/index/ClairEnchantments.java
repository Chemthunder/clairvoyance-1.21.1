package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public interface ClairEnchantments {
    Map<ComponentType<?>, Identifier> ENCHANTMENT_EFFECTS = new LinkedHashMap<>();

    ComponentType<Unit> WAYPOINT = create("waypoint", builder -> builder.codec(Unit.CODEC));

    ComponentType<Unit> OBSCURE = create("obscure", builder -> builder.codec(Unit.CODEC));

    ComponentType<Unit> MATERIALIZE = create("materialize", builder -> builder.codec(Unit.CODEC));

    ComponentType<Unit> CLOAKED = create("cloaked", builder -> builder.codec(Unit.CODEC));

    ComponentType<Unit> IMPERSONATE = create("impersonate", builder -> builder.codec(Unit.CODEC));

    private static <T> ComponentType<T> create(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        ComponentType<T> componentType = builderOperator.apply(ComponentType.builder()).build();
        ENCHANTMENT_EFFECTS.put(componentType, Clairvoyance.id(id));
        return componentType;
    }

    static void init() {
        ENCHANTMENT_EFFECTS.keySet().forEach(effect -> {
            Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, ENCHANTMENT_EFFECTS.get(effect), effect);
        });
    }
}
