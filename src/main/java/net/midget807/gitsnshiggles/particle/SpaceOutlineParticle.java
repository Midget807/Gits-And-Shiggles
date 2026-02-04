package net.midget807.gitsnshiggles.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.util.ModParticleUtil;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.joml.Vector3f;

public class SpaceOutlineParticle extends SpriteBillboardParticle {
    public SpaceOutlineParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, g, h, i);
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.scale = 0.25f;
        this.maxAge = 40;
        Vector3f vector3f = ModParticleUtil.colorToVec3f(0x1B1B22);
        this.setColor(vector3f.x, vector3f.y, vector3f.z);
        this.setSprite(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new SpaceOutlineParticle(clientWorld, d, e, f, g, h, i, spriteProvider);
        }
    }
}
