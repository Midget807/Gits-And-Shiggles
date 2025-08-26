package net.midget807.gitsnshiggles.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleEffect;

public class EmptyEffect extends StatusEffect {
    public EmptyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public EmptyEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
        super(category, color, particleEffect);
    }
}
