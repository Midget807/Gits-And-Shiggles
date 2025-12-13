package net.midget807.gitsnshiggles.item;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.midget807.gitsnshiggles.registry.ModEffects;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.util.DiceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class DiceItem extends Item {
    private int timer = 0;
    private boolean isShaking = false;

    public DiceItem(Settings settings) {
        super(settings);
    }

    public boolean isShaking() {
        return this.isShaking;
    }

    public void setShaking(boolean shaking) {
        isShaking = shaking;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isShaking && entity instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(ModItems.DICE)) {
            player.getItemCooldownManager().set(ModItems.DICE, 10/* todo * 60 * 20*/);
            int gamba = player.getWorld().random.nextBetween(1, 6);
            switch (gamba) {
                case 1: this.executeEvent1(world, player);
                case 2: this.executeEvent2(world, player);
                case 3: this.executeEvent3(world, player);
                case 4: this.executeEvent4(world, player);
                case 5: this.executeEvent5(world, player);
                case 6: this.executeEvent6(world, player);
            }
            this.isShaking = false;
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
        double base = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).getBaseValue();
        if (this.timer < 30 * 20) {
            /*ServerTickEvents.START_SERVER_TICK.register(server1 -> {
                this.timer++;
                player.getAbilities().allowFlying = true;
                player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5);
            });*/
        } else {
            player.getAbilities().allowFlying = false;
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(base);
            this.timer = 0;
        }

    }

    private void executeEvent5(World world, PlayerEntity player) {

    }

    private void executeEvent4(World world, PlayerEntity player) {

    }

    private void executeEvent3(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 8 * 60 * 20, 2, false, false));
    }

    private void executeEvent2(World world, PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, 30 * 20, 0, false, false));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30 * 20, 9, false, false));

    }

    private void executeEvent1(World world, PlayerEntity player) {
        player.kill();
        //unluckyPlayer.addStatusEffect(new StatusEffectInstance(ModEffects.STEPHEN_HAWKING, 30 * 20, 0, false, false));
        //unluckyPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30 * 20, 9, false, false));
    }
}
