package net.midget807.gitsnshiggles.item;

import com.mojang.datafixers.kinds.IdF;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.DiceUtil;
import net.midget807.gitsnshiggles.util.ModDebugUtil;
import net.midget807.gitsnshiggles.util.ModEffectUtil;
import net.minecraft.block.NoteBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class DiceItem extends Item {
    private int flyTimer = 0;
    private int gamba = 0;
    private int gambaStore = 0;

    public DiceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!world.isClient) {
            this.gambaStore = world.random.nextBetween(1, 6);
        }
        this.gamba = gambaStore;
        if (world.isClient) {
            if (this.gamba == 6) {
                player.sendMessage(Text.literal("You rolled a: " + this.gamba).formatted(Formatting.YELLOW), true);
                world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            } else if (this.gamba == 1 || this.gamba == 2) {
                player.sendMessage(Text.literal("You rolled a: " + this.gamba).formatted(Formatting.RED), true);
                this.playBadSound(world, player);
            } else {
                player.sendMessage(Text.literal("You rolled a: " + this.gamba), true);
                world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
        if (!player.getItemCooldownManager().isCoolingDown(ModItems.DICE)) {
            player.getItemCooldownManager().set(ModItems.DICE, 40/* todo * 60 * 20*/);
            switch (this.gamba) {
                case 1:
                    this.executeEvent1(world, player);
                    break;
                case 2:
                    if (!world.isClient) this.executeEvent2(world, player);
                    break;
                case 3:
                    if (!world.isClient) this.executeEvent3(world, player);
                    break;
                case 4:
                    if (!world.isClient) this.executeEvent4(world, player);
                    break;
                case 5:
                    if (!world.isClient) this.executeEvent5(world, player);
                    break;
                case 6:
                    this.executeEvent6(world, player);
                    break;
            }
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
            if (!player.getAbilities().creativeMode && !player.isSpectator()) {
                if (gamba == 6) {
                    this.flyTimer = 1200;
                    player.getAbilities().allowFlying = true;
                    if (world.isClient) player.getAbilities().allowFlying = true;
                    if (!world.isClient) player.getAbilities().allowFlying = true;
                    this.gamba = 0;
                }
                if (this.flyTimer <= 0) {
                    player.getAbilities().allowFlying = false;
                    if (world.isClient) player.getAbilities().allowFlying = false;
                    if (!world.isClient) player.getAbilities().allowFlying = false;
                    player.getAbilities().flying = false;
                }
            }
        }
    }

    private ServerPlayerEntity getRandomPlayer(MinecraftServer server, PlayerEntity player, World world) {
        List<ServerPlayerEntity> playerList = server.getPlayerManager().getPlayerList();
        ServerPlayerEntity owner = server.getPlayerManager().getPlayer(player.getUuid());
        if (owner != null) {
            playerList.remove(owner);
        }
        int randomPlayerIndex = world.random.nextBetween(0, !playerList.isEmpty() ? playerList.size() - 1 : 0);
        return randomPlayerIndex >= 0 && !playerList.isEmpty() ? playerList.get(randomPlayerIndex) : null;
    }

    private void executeEvent6(World world, PlayerEntity player) {
        MinecraftServer server = world.getServer();
        if (server != null) {
            ServerPlayerEntity target = this.getRandomPlayer(server, player, world);
            if (target == null) {
                return;
            }
            ServerPlayerEntity unluckyPerson = null;
            if (DiceUtil.getUnluckyPersonUuid() != null) {
                unluckyPerson = server.getPlayerManager().getPlayer(DiceUtil.getVeryUnluckyPersonUuid());
            }
            int duration = unluckyPerson != null && target == unluckyPerson ? 30 * 20 * 2 : 30 * 20;
            //target.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, duration, 0, false, false));
            //target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, duration, 9, false, false));
        }
        if (!world.isClient) player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2, false, true));
    }

    private void executeEvent5(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 600, 9, false, true));
    }

    private void executeEvent4(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 8 * 60 * 20, 19, false, true));
    }

    private void executeEvent3(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 8 * 60 * 20, 2, false, true));

    }

    private void executeEvent2(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, 10 * 20, 0, false, true));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 10 * 20, 9, false, true));
    }

    private void executeEvent1(World world, PlayerEntity player) {
        player.kill();
    }
}
