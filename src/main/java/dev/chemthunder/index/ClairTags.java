package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface ClairTags {
 //   TagKey<Item> TAG_NAME = create("tag_name");


    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, Clairvoyance.id(id));
    }
}
