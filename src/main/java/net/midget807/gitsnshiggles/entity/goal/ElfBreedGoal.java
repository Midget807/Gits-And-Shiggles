package net.midget807.gitsnshiggles.entity.goal;

import net.midget807.gitsnshiggles.entity.ElfEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class ElfBreedGoal extends Goal {
    protected final ElfEntity elf;
    protected AnimalEntity target;

    public ElfBreedGoal(ElfEntity elf) {
        this.elf = elf;
    }

    @Override
    public boolean canStart() {
        return !this.elf.getWorld().getEntitiesByClass(AnimalEntity.class, this.elf.getBoundingBox().expand(16, 4, 16), animalEntity -> animalEntity.getBreedingAge() == 0 && animalEntity.canEat()).isEmpty();
    }

    @Override
    public void start() {
        if (!this.elf.getWorld().getEntitiesByClass(AnimalEntity.class, this.elf.getBoundingBox().expand(16, 4, 16), animalEntity -> !this.elf.getWorld().isClient() && animalEntity.getBreedingAge() == 0 && animalEntity.canEat()).isEmpty()) {
            List<AnimalEntity> animalList = this.elf.getWorld().getEntitiesByClass(AnimalEntity.class, this.elf.getBoundingBox().expand(16, 4, 16), animalEntity -> animalEntity.getBreedingAge() == 0 && animalEntity.canEat());
            AnimalEntity closestAnimal = animalList.get(0);
            for (AnimalEntity animalEntity : animalList) {
                if (animalEntity.squaredDistanceTo(this.elf) < closestAnimal.squaredDistanceTo(this.elf)) {
                    closestAnimal = animalEntity;
                }
            }

            this.target = closestAnimal;
        }
    }

    @Override
    public void tick() {
        if (this.elf.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ()) <= 5) {
            if (this.elf.getOwner() instanceof PlayerEntity) {
                this.target.lovePlayer((PlayerEntity) this.elf.getOwner());
            }

            this.target = null;
        } else {
            this.elf.getNavigation().startMovingTo(this.target, 1D);
        }
    }
    @Override
    public boolean shouldContinue() {
        return this.target != null && this.target.getBreedingAge() == 0 && this.target.canEat();
    }
}
