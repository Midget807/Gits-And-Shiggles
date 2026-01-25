package net.midget807.gitsnshiggles.entity.client;

import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.TronDiscEntity;
import net.midget807.gitsnshiggles.registry.ModItems;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.midget807.gitsnshiggles.util.ColoredItemUtil;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.joml.Vector3fc;

public class TronDiscEntityRenderer extends EntityRenderer<TronDiscEntity> {
    public static final Identifier DEFAULT_TEXTURE = GitsAndShigglesMain.id("textures/item/tron_disc_full.png");
    public static final Identifier TEXTURE = GitsAndShigglesMain.id("textures/item/tron_disc.png");
    private ItemStack stack = ModItems.TRON_DISC.getDefaultStack();
    private final ItemRenderer itemRenderer;

    public TronDiscEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(TronDiscEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.getItemStack() != null) {
            stack = entity.getItemStack();
        }
        matrices.push();
        matrices.multiply(new Quaternionf().rotateLocalY((float) (Math.PI + MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()))));
        matrices.multiply(new Quaternionf().rotateLocalX((float) (Math.PI / 2 + MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()))));
        matrices.multiply(new Quaternionf().rotateLocalZ((float) ((entity.age + tickDelta) * -(Math.PI * 75 / 180))));
        matrices.scale(1.0f, 1.0f, 1.0f);
        this.itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(TronDiscEntity entity) {
        return entity.getColor() > 0 ? TEXTURE : DEFAULT_TEXTURE;
    }
}
