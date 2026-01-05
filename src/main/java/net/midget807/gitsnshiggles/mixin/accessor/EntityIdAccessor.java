package net.midget807.gitsnshiggles.mixin.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(Entity.class)
public interface EntityIdAccessor {
    @Accessor("CURRENT_ID")
    static AtomicInteger getCurrentId() {
        throw new AssertionError();
    }
}
