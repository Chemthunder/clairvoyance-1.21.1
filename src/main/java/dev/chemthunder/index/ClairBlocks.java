package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.acoyt.acornlib.impl.item.TranslationBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public interface ClairBlocks {


    static Block create(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, Clairvoyance.id(name), block);
    }

    static Block createWithItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = create(name, factory, settings);
        ClairItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), new Item.Settings());
        return block;
    }

    static void init() {
    }
}
