package net.midget807.gitsnshiggles.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;

public class FlyEffect extends EmptyEffect {
    public FlyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public FlyEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
        super(category, color, particleEffect);
    }
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            player.getAbilities().allowFlying = true;
        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
