package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ClairDataComponents {
    Map<ComponentType<?>, Identifier> COMPONENTS = new LinkedHashMap<>();

//    ComponentType<Integer> COOLDOWN_TIME = create("cooldown_time", new ComponentType.Builder<Integer>()
//            .codec(Codec.INT)
//            .build());

    ComponentType<BlockPos> WAYPOINT = create("waypoint", new ComponentType.Builder<BlockPos>()
            .codec(BlockPos.CODEC)
            .build()
    );

    static <T extends ComponentType<?>> T create(String name, T component) {
        COMPONENTS.put(component, Clairvoyance.id(name));
        return component;
    }

    static void init() {
        COMPONENTS.keySet().forEach((component) -> {
            Registry.register(Registries.DATA_COMPONENT_TYPE, COMPONENTS.get(component), component);
        });
    }
}
