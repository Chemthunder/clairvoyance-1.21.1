package dev.chemthunder.index;

import dev.chemthunder.Clairvoyance;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ClairArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> ACCURSED = registerMaterial("accursed",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 1,
                    ArmorItem.Type.CHESTPLATE, 0,
                    ArmorItem.Type.LEGGINGS, 0,
                    ArmorItem.Type.BOOTS, 0
            ),
            // Enchantability. For reference, leather has 15, iron has 9, and diamond has 10.
            0,
            // The sound played when the armor is equipped.
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            // The ingredient(s) used to repair the armor.
            () -> Ingredient.ofItems(Items.AIR),
            0.0F,
            0.0F,
            // Guidite is NOT dyeable, so we will pass false.
            false);

    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Identifier.of(Clairvoyance.MOD_ID, id), "", dyeable)
        );
        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(Clairvoyance.MOD_ID, id), material);
        return RegistryEntry.of(material);
    }
}
