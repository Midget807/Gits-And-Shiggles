package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final RegistryEntry<ArmorMaterial> WIZARD_CLOTH = registerArmorMaterial("wizard_cloth",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.BODY, 3);
            }), 20, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, () -> Ingredient.ofItems(Items.LEATHER),
                    List.of(new ArmorMaterial.Layer(GitsAndShigglesMain.id("wizard_cloth"), "", true)), 0.0f, 0.00f)
    );

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> materialSupplier) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, GitsAndShigglesMain.id(name), materialSupplier.get());
    }
}
