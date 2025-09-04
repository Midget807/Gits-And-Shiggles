package net.midget807.gitsnshiggles.item;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.midget807.gitsnshiggles.util.ModTextureIds;
import net.midget807.gitsnshiggles.util.inject.WizardGamba;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebuggerItem extends Item {

    public DebuggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.createExplosion(user, user.getX(), user.getY(), user.getZ(), 20, World.ExplosionSourceType.TNT);
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
