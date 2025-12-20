package net.midget807.gitsnshiggles.item;

import com.mojang.datafixers.kinds.IdF;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.entity.goal.ElfBreedGoal;
import net.midget807.gitsnshiggles.entity.goal.ElfHarvestGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import java.util.List;

public class SackItem extends Item {
    public static final List<Mode> modes = List.of(Mode.SKIRMISH, Mode.GATHER, Mode.HARVEST, Mode.BREED);
    public Mode selectedMode;

    public SackItem(Settings settings) {
        super(settings);
        this.selectedMode = modes.getFirst();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            if (player.isSneaking()) {
                final List<ElfEntity> elfEntities = world.getEntitiesByClass(ElfEntity.class, player.getBoundingBox().expand(32f), entity -> entity.isTamed() && entity.getOwner() != null && entity.getOwner().equals(player));
                elfEntities.forEach(entity -> {
                    Goal goal = null;
                    switch (selectedMode) {
                        case SKIRMISH -> goal = new ActiveTargetGoal<>(entity, HostileEntity.class, 10, true, false, livingEntity -> true);
                        case GATHER -> entity.removeCurrentGoal();
                        case HARVEST -> goal = new ElfHarvestGoal(entity);
                        case BREED -> goal = new ElfBreedGoal(entity);
                    }
                    if (goal != null) {
                        entity.setGoal(goal);
                        world.playSoundFromEntity(null, player, SoundEvents.ITEM_BUNDLE_INSERT, player.getSoundCategory(), 1.0f, 0.5f);
                    }
                });
            } else {
                this.cycle();
            }
        }
        if (world.isClient) {
            switch (selectedMode) {
                case SKIRMISH:
                    player.sendMessage(Text.literal("Elves now: ").append(Text.literal("SKIRMISHING").formatted(Formatting.BOLD)), true);
                    break;
                case GATHER:
                    player.sendMessage(Text.literal("Elves now: ").append(Text.literal("GATHERING").formatted(Formatting.BOLD)), true);
                    break;

                case HARVEST:
                    player.sendMessage(Text.literal("Elves now: ").append(Text.literal("HARVESTING").formatted(Formatting.BOLD)), true);
                    break;

                case BREED:
                    player.sendMessage(Text.literal("Elves now: ").append(Text.literal("BREEDING").formatted(Formatting.BOLD)), true);
                    break;
            }
        }
        return TypedActionResult.success(stack);
    }

    private void cycle() {
        int index = modes.indexOf(selectedMode);
        if (index >= modes.size()) {
            this.selectedMode = modes.getLast();
        }
        if (index < modes.size() && selectedMode != modes.getLast()) {
            this.selectedMode = modes.get(index + 1);
        } else {
            this.selectedMode = modes.getFirst();
        }
    }

    public enum Mode {
        SKIRMISH,
        GATHER,
        HARVEST,
        BREED;
    }
}
