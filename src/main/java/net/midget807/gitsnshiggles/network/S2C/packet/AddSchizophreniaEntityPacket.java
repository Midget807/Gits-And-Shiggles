package net.midget807.gitsnshiggles.network.S2C.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaEntity;
import net.midget807.gitsnshiggles.entity.client.SchizophreniaManager;
import net.midget807.gitsnshiggles.network.S2C.payload.AddSchizophreniaEntityPayload;
import net.midget807.gitsnshiggles.util.ModUtil;
import net.midget807.gitsnshiggles.util.inject.Schizophrenia;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AddSchizophreniaEntityPacket {
    public static void receive(AddSchizophreniaEntityPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientPlayerEntity player = context.player();
            World world = player.getWorld();
            int i = world.getRandom().nextBetween(0, ModUtil.getSpawnableMobs().size() - 1);
            SchizophreniaManager schizophreniaManager = ((Schizophrenia) player).getSchizophreniaManager();
            Vec3d rotVec = player.getRotationVector().normalize();
            Vec3d pos = player.getPos().add(rotVec.negate().multiply(2));
            schizophreniaManager.add(new SchizophreniaEntity(
                    ModUtil.getSpawnableMobs().get(i),
                    pos,
                    (float) world.random.nextBetween(-180, 180),
                    payload.maxAge()
            ));
        });
    }
}
