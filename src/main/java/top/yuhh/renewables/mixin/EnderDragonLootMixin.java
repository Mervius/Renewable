package top.yuhh.renewables.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//  I don't think anyone will ever do this because this is incredibly stupid, but this is in line with other mob heads... so...

@Mixin(Mob.class)
public class EnderDragonLootMixin {
    @Inject(method = "dropCustomDeathLoot", at = @At("HEAD"))
    protected void dropCustomLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit, CallbackInfo ci) {
        if ((Object) this.getClass() == EnderDragon.class) {
            if (damageSource.getEntity() instanceof Creeper creeper && creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                ((Mob) (Object) this).spawnAtLocation(Items.DRAGON_HEAD);
            }
        }
    }
}