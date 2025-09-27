package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import dev.chemthunder.item.*;
import dev.chemthunder.item.effigy.AbstinentEffigyItem;
import dev.chemthunder.item.effigy.ChippedEffigyItem;
import dev.chemthunder.item.effigy.EngravedEffigyItem;
import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import javax.accessibility.AccessibleRelation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface ClairItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();


    Item SPECTRAL_MIRROR = create("spectral_mirror", new SpectralMirrorItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item CASTING_STONE = create("casting_stone", new CastingStoneItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item EFFIGY_VESSEL = create("effigy_vessel", new EmptyEffigyItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item TEST_MASK = create("cursed_mask", new DemonsMaskItem(ClairArmorMaterials.ACCURSED, ArmorItem.Type.HELMET, new AcornItemSettings()
            .maxCount(1)
    ));

    // effigies

    Item ABSTINENT_EFFIGY = create("abstinent_effigy", new AbstinentEffigyItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item ENGRAVED_EFFIGY = create("engraved_effigy", new EngravedEffigyItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item CHIPPED_EFFIGY = create("chipped_effigy", new ChippedEffigyItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item SOUL_BOTTLE = create("soul_bottle", new SoulBottleItem(new AcornItemSettings()
            .maxCount(1)
            .fireproof()
    ));


    static void init() {
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));

        modifyItemNameColor(SPECTRAL_MIRROR, 0x1c1c21);
        modifyItemNameColor(TEST_MASK, 0x9e1830);
        modifyItemNameColor(SOUL_BOTTLE, 0x00ffed);

        modifyItemNameColor(ABSTINENT_EFFIGY, 0x9c3933);
        modifyItemNameColor(ENGRAVED_EFFIGY, 0x5afab7);
        modifyItemNameColor(CHIPPED_EFFIGY, 0xfff569);
    }



    private static Item create(String name, Item item) {
        ITEMS.put(item, Clairvoyance.id(name));
        return item;
    }

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Clairvoyance.id(name), item);
    }
}
