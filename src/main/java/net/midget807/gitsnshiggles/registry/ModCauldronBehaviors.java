package net.midget807.gitsnshiggles.registry;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.minecraft.block.Blocks;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class ModCauldronBehaviors {
    public static final Map<Item, Item> QUENCHING_RECIPE = Map.of(
            ModItems.RED_HOT_IRON_INGOT, Items.IRON_INGOT,
            ModItems.RED_HOT_KATANA_BLADE, ModItems.KATANA_BLADE,
            ModItems.RED_HOT_NETHERITE_INGOT, Items.NETHERITE_INGOT,
            ModItems.RED_HOT_GOLD_INGOT, Items.GOLD_INGOT,
            ModItems.RED_HOT_GOLD_ALLOY_INGOT, ModItems.GOLD_ALLOY_INGOT,
            ModItems.RED_HOT_GOLD_ALLOY_PLATE, ModItems.GOLD_ALLOY_PLATE
    );

    public static void registerModCauldronBehaviors() {
        addQuenching();
        GitsAndShigglesMain.LOGGER.info("Registering Mod Cauldron Behaviors");
    }

    private static void addQuenching() {
        Map<Item, CauldronBehavior> map = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
        ModCauldronBehaviors.QUENCHING_RECIPE.forEach((inputItem, outputItem) -> {
            map.put(
                    inputItem,
                    (CauldronBehavior) (state, world, pos, player, hand, stack) -> {
                        if (!world.isClient) {
                            Item item = stack.getItem();
                            player.setStackInHand(hand, ModUtil.exchangeWholeStack(stack, player, new ItemStack(outputItem)));
                            player.incrementStat(Stats.USE_CAULDRON);
                            player.incrementStat(Stats.USED.getOrCreateStat(item));
                            emptyCauldron(world, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH);
                        }
                        return ItemActionResult.success(world.isClient);
                    }
            );
        });
    }

    private static void emptyCauldron(World world, BlockPos pos, SoundEvent soundEvent) {
        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
        world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}
