package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import dev.chemthunder.item.CallingStoneItem;
import dev.chemthunder.item.DemonsMaskItem;
import dev.chemthunder.item.EmptyEffigyItem;
import dev.chemthunder.item.SpectralMirrorItem;
import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface ClairItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();


    Item SPECTRAL_MIRROR = create("spectral_mirror", new SpectralMirrorItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item CALLING_STONE = create("calling_stone", new CallingStoneItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item EFFIGY_VESSEL = create("effigy_vessel", new EmptyEffigyItem(new AcornItemSettings()
            .maxCount(1)
    ));

    Item TEST_MASK = create("cursed_mask", new DemonsMaskItem(ClairArmorMaterials.ACCURSED, ArmorItem.Type.HELMET, new AcornItemSettings()
            .maxCount(1)
    ));



   static void init() {
       ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));

       modifyItemNameColor(SPECTRAL_MIRROR, 0x1c1c21);
       modifyItemNameColor(TEST_MASK, 0x9e1830);
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
