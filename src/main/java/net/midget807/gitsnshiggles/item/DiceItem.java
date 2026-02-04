package net.midget807.gitsnshiggles.item;

import net.midget807.gitsnshiggles.cca.DiceRollComponent;
import net.midget807.gitsnshiggles.registry.ModDataComponentTypes;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.midget807.gitsnshiggles.util.state.UnluckyPlayerState;
import net.midget807.gitsnshiggles.util.state.VeryUnluckyPlayerState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class DiceItem extends Item {
    private int flyTimer = 0;
    private boolean shouldFly = false;

    public DiceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        DiceRollComponent diceRollComponent = DiceRollComponent.get(player);
        if (!world.isClient) {
            diceRollComponent.setDoubleIntValue1(world.random.nextBetween(1, 6));
        }
        player.getItemCooldownManager().set(ModItems.DICE, player.getAbilities().creativeMode ? 10 : 12000 /*10 min*/);
        int roll = diceRollComponent.getDoubleIntValue1();
        itemStack.set(ModDataComponentTypes.DICE_ROLL, roll);

        switch (roll) {
            case 1:
                executeEvent1(world, player);
                break;
            case 2:
                executeEvent2(world, player);
                break;
            case 3:
                executeEvent3(world, player);
                break;
            case 4:
                executeEvent4(world, player);
                break;
            case 5:
                executeEvent5(world, player);
                break;
            case 6:
                executeEvent6(world, player);
                break;
        }
        return TypedActionResult.success(itemStack);
    }

    private void playBadSound(World world, PlayerEntity player) {
        //world.playSound(null, player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ(), SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.RECORDS, 3.0F, NoteBlock.getNotePitch(14), world.random.nextLong());
        world.playSound(null, player.getBlockPos().getX(), player.getBlockPos().getY(), player.getBlockPos().getZ(), SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.RECORDS, 3.0F, NoteBlock.getNotePitch(8), world.random.nextLong());
    }

    @Override
    public void inventoryTick (ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (this.flyTimer > 0) {
            this.flyTimer--;
        }
        if (entity instanceof PlayerEntity player) {
            DiceRollComponent diceRollComponent = DiceRollComponent.get(player);
            int roll = diceRollComponent.getDoubleIntValue1();
            if (world.isClient) {
                if (roll == 6) {
                    player.sendMessage(Text.literal("You rolled a: " + roll).formatted(Formatting.YELLOW), true);
                } else if (roll <= 2) {
                    player.sendMessage(Text.literal("You rolled a: " + roll).formatted(Formatting.RED), true);
                } else {
                    player.sendMessage(Text.literal("You rolled a: " + roll), true);
                }
            }
        }
    }

    private static ServerPlayerEntity getRandomPlayer(MinecraftServer server, PlayerEntity excepts, World world) {
        List<ServerPlayerEntity> playerList = server.getPlayerManager().getPlayerList();
        ServerPlayerEntity owner = server.getPlayerManager().getPlayer(excepts.getUuid());
        if (owner != null) {
            playerList.remove(owner);
        }
        int randomPlayerIndex = world.random.nextBetween(0, !playerList.isEmpty() ? playerList.size() - 1 : 0);
        return randomPlayerIndex >= 0 && !playerList.isEmpty() ? playerList.get(randomPlayerIndex) : null;
    }

    private ServerPlayerEntity getVeryUnluckyPlayer(@Nullable VeryUnluckyPlayerState veryUnluckyPlayerState, World world) {
        if (world.getServer() == null || world.getServer().getPlayerManager() == null || veryUnluckyPlayerState == null || veryUnluckyPlayerState.veryUnluckyPlayerUuidString.isEmpty()) {
            return null;
        }
        return world.getServer().getPlayerManager().getPlayer(UUID.fromString(veryUnluckyPlayerState.veryUnluckyPlayerUuidString));
    }

    private ServerPlayerEntity getUnluckyPlayer(@Nullable UnluckyPlayerState unluckyPlayerState, World world) {
        if (world.getServer() == null || world.getServer().getPlayerManager() == null || unluckyPlayerState == null || unluckyPlayerState.unluckyPlayerUuidString.isEmpty()) {
            return null;
        }
        return world.getServer().getPlayerManager().getPlayer(UUID.fromString(unluckyPlayerState.unluckyPlayerUuidString));
    }

    private void executeEvent6(World world, PlayerEntity player) {
        DiceRollComponent diceRollComponent = DiceRollComponent.get(player);
        diceRollComponent.setDoubleIntValue2(DiceRollComponent.FLY_DURATION);
        MinecraftServer server = world.getServer();
        if (!world.isClient) player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2, false, false));
        if (server != null) {
            VeryUnluckyPlayerState veryUnluckyPlayerState = VeryUnluckyPlayerState.getServerState(server);

            ServerPlayerEntity target;
            int duration = 600;
            target = getVeryUnluckyPlayer(veryUnluckyPlayerState, world);

            if (target == null) {
                target = getRandomPlayer(server, player, world);
            } else {
                duration = 1200;
            }

            if (!world.isClient && target != null) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, duration, 0, false, false));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, duration, 9, false, false));
            }
        }
    }

    private void executeEvent5(World world, PlayerEntity player) {
        if (!world.isClient) player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, 9, false, false));
    }

    private void executeEvent4(World world, PlayerEntity player) {
        if (!world.isClient) player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 8 * 60 * 20, 19, false, false));
    }

    private void executeEvent3(World world, PlayerEntity player) {
        if (!world.isClient) player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 8 * 60 * 20, 2, false, false));
        MinecraftServer server = world.getServer();
        if (server != null) {
            UnluckyPlayerState unluckyPlayerState = UnluckyPlayerState.getServerState(server);

            ServerPlayerEntity target;
            int duration = 600;
            target = getUnluckyPlayer(unluckyPlayerState, world);

            if (target == null) {
                target = getRandomPlayer(server, player, world);
            } else {
                duration = 1200;
            }

            if (!world.isClient && target != null) {
                target.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, duration, 0, false, false));
            }
        }
    }

    private void executeEvent2(World world, PlayerEntity player) {
        if (!world.isClient) {
            player.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, 10 * 20, 0, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 10 * 20, 9, false, false));
        }
    }

    private void executeEvent1(World world, PlayerEntity player) {
        MinecraftServer server = world.getServer();
        if (server != null) {
            VeryUnluckyPlayerState state = VeryUnluckyPlayerState.getServerState(server);
            ServerPlayerEntity target = getRandomPlayer(server, player, world);
            int duration = 600;
            if (target != null) {
                if (world.getPlayerByUuid(UUID.fromString(state.veryUnluckyPlayerUuidString)) != null && target.getUuid() == UUID.fromString(state.veryUnluckyPlayerUuidString)) {
                    duration = 1200;
                }
                target.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, duration, 0 , false, false));
            }
        }
        player.kill();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int roll = stack.getOrDefault(ModDataComponentTypes.DICE_ROLL, 0);
        if (roll == 6) {
            tooltip.add(Text.literal("Roll: ").formatted(Formatting.GRAY).append(Text.literal("" + roll).formatted(Formatting.YELLOW)));
        } else if (roll <= 2) {
            tooltip.add(Text.literal("Roll: ").formatted(Formatting.GRAY).append(Text.literal("" + roll).formatted(Formatting.RED)));
        } else {
            tooltip.add(Text.literal("Roll: ").formatted(Formatting.GRAY).append(Text.literal("" + roll).formatted(Formatting.GRAY)));
        }
        if (stack.getHolder() instanceof PlayerEntity player) {
            int duration = (int) Math.ceil(player.getItemCooldownManager().getCooldownProgress(ModItems.DICE, 0) * 12000 /*10 min*/);
            tooltip.add(Text.literal("Cooldown: ").append(ModUtil.getDurationText(duration)));
        }

        super.appendTooltip(stack, context, tooltip, type);
    }
}
