package net.midget807.gitsnshiggles.cca;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.midget807.gitsnshiggles.cca.primative.DoubleIntComponent;
import net.midget807.gitsnshiggles.network.S2C.payload.AddSchizophreniaEntityPayload;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class SchizophreniaComponent implements DoubleIntComponent, AutoSyncedComponent, CommonTickingComponent {
    public static final String SLEEP_TIMER_KEY = "SchizoSleepTimer";
    public static final String COOLDOWN_KEY = "SchizoSpawnCooldown";
    private final PlayerEntity player;
    private int sleepTimer = 0;
    private int cooldown = 0;

    public SchizophreniaComponent(PlayerEntity player) {
        this.player = player;
    }

    public static SchizophreniaComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.SCHIZOPHRENIA_COMPONENT.get(player);
    }

    private void sync() {
        ModCCAComponents.SCHIZOPHRENIA_COMPONENT.sync(player);
    }

    @Override
    public int getDoubleIntValue1() {
        return this.sleepTimer;
    }

    @Override
    public int getDoubleIntValue2() {
        return this.cooldown;
    }

    @Override
    public void setDoubleIntValue1(int value) {
        this.sleepTimer = value;
        this.sync();
    }

    @Override
    public void setDoubleIntValue2(int value) {
        this.cooldown = value;
        this.sync();
    }

    @Override
    public void addToDoubleIntValue1(int count) {
        this.sleepTimer += count;
        this.sync();
    }

    @Override
    public void addToDoubleIntValue2(int count) {
        this.cooldown += count;
        this.sync();
    }

    @Override
    public void incrementDoubleIntValue1() {
        this.sleepTimer++;
        this.sync();
    }

    @Override
    public void incrementDoubleIntValue2() {
        this.cooldown++;
        this.sync();
    }

    @Override
    public void decrementDoubleIntValue1() {
        this.sleepTimer--;
        this.sync();
    }

    @Override
    public void decrementDoubleIntValue2() {
        this.cooldown--;
        this.sync();
    }

    @Override
    public void tick() {
        if (this.cooldown > 0) {
            this.decrementDoubleIntValue2();
        } else if (this.cooldown < 0) {
            this.setDoubleIntValue2(0);
        }
        if (this.sleepTimer > 24000 * 5 && this.cooldown <= 0) {
            int cooldown = player.getWorld().random.nextBetween(1, 300) * 20;
            this.setDoubleIntValue2(cooldown);
            if (player instanceof ServerPlayerEntity serverPlayerEntity) {
                ServerPlayNetworking.send(serverPlayerEntity, new AddSchizophreniaEntityPayload(5000));
            }
        }

        if (player instanceof ServerPlayerEntity serverPlayerEntity) {
            int i = serverPlayerEntity.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
            this.setDoubleIntValue1(i);
        }
    }


    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.sleepTimer = tag.getInt(SLEEP_TIMER_KEY);
        this.cooldown = tag.getInt(COOLDOWN_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt(SLEEP_TIMER_KEY, this.sleepTimer);
        tag.putInt(COOLDOWN_KEY, this.cooldown);
    }
}
