package net.midget807.gitsnshiggles.entity.client;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class SchizophreniaEntity {
    public final EntityType<? extends LivingEntity> type;
    public final Vec3d pos;
    public final float yaw;
    public final float pitch;
    public int age;
    public final int maxAge;

    public SchizophreniaEntity(EntityType<? extends LivingEntity> type, Vec3d pos, float yaw, int maxAge) {
        this.type = type;
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = 0;
        this.age = 0;
        this.maxAge = maxAge;
    }
}
