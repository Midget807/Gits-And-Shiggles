package net.midget807.gitsnshiggles.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InfinityGauntletItem extends Item {
    public int powerStoneCD = 0;
    public int spaceStoneCD = 0;
    public int realityStoneCD = 0;
    public int soulStoneCD = 0;
    public int timeStoneCD = 0;
    public int mindStoneCD = 0;
    public int realityStoneTimer = 0;

    public InfinityGauntletItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (powerStoneCD > 0) {
            powerStoneCD--;
        } else if (powerStoneCD < 0) {
            powerStoneCD = 0;
        }
        if (spaceStoneCD > 0) {
            spaceStoneCD--;
        } else if (spaceStoneCD < 0) {
            spaceStoneCD = 0;
        }
        if (realityStoneCD > 0) {
            realityStoneCD--;
        } else if (realityStoneCD < 0) {
            realityStoneCD = 0;
        }
        if (soulStoneCD > 0) {
            soulStoneCD--;
        } else if (soulStoneCD < 0) {
            soulStoneCD = 0;
        }
        if (timeStoneCD > 0) {
            timeStoneCD--;
        } else if (timeStoneCD < 0) {
            timeStoneCD = 0;
        }
        if (mindStoneCD > 0) {
            mindStoneCD--;
        } else if (mindStoneCD < 0) {
            mindStoneCD = 0;
        }
        if (realityStoneTimer > 0) {
            realityStoneTimer--;
        } else if (realityStoneTimer < 0) {
            realityStoneTimer = 0;
        }
    }
}
