package net.midget807.gitsnshiggles.cca;

import net.midget807.gitsnshiggles.cca.primative.BoolComponent;
import net.midget807.gitsnshiggles.registry.ModCCAComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class WizardVanishComponent implements BoolComponent, AutoSyncedComponent {
    public static final String KEY = "WizardVanish";
    private final PlayerEntity player;
    private boolean vanish = false;

    public WizardVanishComponent(PlayerEntity player) {
        this.player = player;
    }

    public static WizardVanishComponent get(@NotNull PlayerEntity player) {
        return ModCCAComponents.WIZARD_VANISH.get(player);
    }

    private void sync() {
        ModCCAComponents.WIZARD_VANISH.sync(player);
    }

    @Override
    public boolean getValue() {
        return this.vanish;
    }

    @Override
    public void setValue(boolean value) {
        this.vanish = value;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.vanish = nbtCompound.getBoolean(KEY);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean(KEY, this.vanish);
    }
}
