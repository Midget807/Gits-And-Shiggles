package net.midget807.gitsnshiggles.entity.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.midget807.gitsnshiggles.GitsAndShigglesMain;
import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.midget807.gitsnshiggles.registry.client.ModEntityModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ElfEntityRenderer extends MobEntityRenderer<ElfEntity, ElfEntityModel> {
    public static final Identifier TEXTURE = GitsAndShigglesMain.id("textures/entity/elf.png");
    public static final Identifier ANGRY_TEXTURE = GitsAndShigglesMain.id("textures/entity/elf_angry.png");
    public ElfEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElfEntityModel(context.getPart(ModEntityModelLayers.ELF_MODEL_LAYER)), 0.5f);
    }

    @Override
    public void render(ElfEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.setModelPose(livingEntity);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Vec3d getPositionOffset(ElfEntity entity, float tickDelta) {
        return entity.isInSneakingPose()
                ? new Vec3d(0.0, entity.getScale() * -2.0 / 16.0, 0.0)
                : super.getPositionOffset(entity, tickDelta);
    }

    private void setModelPose(ElfEntity livingEntity) {
        ElfEntityModel playerEntityModel = this.getModel();
        if (livingEntity.isSpectator()) {
            playerEntityModel.setVisible(false);
            playerEntityModel.head.visible = true;
            playerEntityModel.hat.visible = true;
        } else {
            playerEntityModel.setVisible(true);
            playerEntityModel.sneaking = livingEntity.isInSneakingPose();
            ElfEntityModel.ArmPose armPose = getArmPose(livingEntity, Hand.MAIN_HAND);
            ElfEntityModel.ArmPose armPose2 = getArmPose(livingEntity, Hand.OFF_HAND);
            if (armPose.isTwoHanded()) {
                armPose2 = livingEntity.getOffHandStack().isEmpty() ? ElfEntityModel.ArmPose.EMPTY : ElfEntityModel.ArmPose.ITEM;
            }

            if (livingEntity.getMainArm() == Arm.RIGHT) {
                playerEntityModel.rightArmPose = armPose;
                playerEntityModel.leftArmPose = armPose2;
            } else {
                playerEntityModel.rightArmPose = armPose2;
                playerEntityModel.leftArmPose = armPose;
            }
        }
    }

    private ElfEntityModel.ArmPose getArmPose(ElfEntity livingEntity, Hand hand) {
        ItemStack itemStack = livingEntity.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            return ElfEntityModel.ArmPose.EMPTY;
        } else {
            if (livingEntity.getActiveHand() == hand && livingEntity.getItemUseTimeLeft() > 0) {
                UseAction useAction = itemStack.getUseAction();
                if (useAction == UseAction.BLOCK) {
                    return ElfEntityModel.ArmPose.BLOCK;
                }

                if (useAction == UseAction.BOW) {
                    return ElfEntityModel.ArmPose.BOW_AND_ARROW;
                }

                if (useAction == UseAction.SPEAR) {
                    return ElfEntityModel.ArmPose.THROW_SPEAR;
                }

                if (useAction == UseAction.CROSSBOW && hand == livingEntity.getActiveHand()) {
                    return ElfEntityModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useAction == UseAction.SPYGLASS) {
                    return ElfEntityModel.ArmPose.SPYGLASS;
                }

                if (useAction == UseAction.TOOT_HORN) {
                    return ElfEntityModel.ArmPose.TOOT_HORN;
                }

                if (useAction == UseAction.BRUSH) {
                    return ElfEntityModel.ArmPose.BRUSH;
                }
            } else if (!livingEntity.handSwinging && itemStack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack)) {
                return ElfEntityModel.ArmPose.CROSSBOW_HOLD;
            }

            return ElfEntityModel.ArmPose.ITEM;
        }
    }

    @Override
    public Identifier getTexture(ElfEntity entity) {
        return /*entity.hasAngerTime() ? ANGRY_TEXTURE :*/ TEXTURE;
    }
}
