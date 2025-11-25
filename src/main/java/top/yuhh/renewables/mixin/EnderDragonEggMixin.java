package top.yuhh.renewables.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

//  I don't think anyone will ever do this because this is incredibly stupid, but this is in line with other mob heads... so...

@Mixin(EndDragonFight.class)
public class EnderDragonEggMixin {

    @Shadow
    private UUID dragonUUID;

    @Final
    @Shadow
    private ServerBossEvent dragonEvent;

    @Shadow
    private boolean previouslyKilled;

    @Final
    @Shadow
    private ServerLevel level;

    @Final
    @Shadow
    private BlockPos origin;

    @Shadow
    private boolean dragonKilled;

    @Shadow
    private void spawnExitPortal(boolean active) {
    }

    @Shadow
    private void spawnNewGateway() {
    }

    /**
     * @author Daniel Hagemeier
     * @reason Removing previously killed check
     */
    @Overwrite
    public void setDragonKilled(EnderDragon dragon) {
        if (dragon.getUUID().equals(this.dragonUUID)) {
            this.dragonEvent.setProgress(0.0F);
            this.dragonEvent.setVisible(false);
            this.spawnExitPortal(true);
            this.spawnNewGateway();
            this.level
                    .setBlockAndUpdate(
                            this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(this.origin)),
                            Blocks.DRAGON_EGG.defaultBlockState()
                    );

            this.previouslyKilled = true;
            this.dragonKilled = true;
        }
    }
}