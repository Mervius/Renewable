package top.yuhh.renewables.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import top.yuhh.renewables.vault.CustomVaultServer;
import top.yuhh.renewables.vault.customVault;

import javax.annotation.Nullable;

@Mixin(VaultBlock.class)
public abstract class VaultBlockMixin extends BaseEntityBlock{
    protected VaultBlockMixin(Properties properties) {
        super(properties);
    }

    /**
     * @author Daniel Hememeier
     * @reason Calling customTick to add additional method to tick constructor
     */
    @Nullable
    @Overwrite
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_323525_, @NotNull BlockState p_324070_, @NotNull BlockEntityType<T> p_323541_) {
        return p_323525_ instanceof ServerLevel serverlevel
                ? createTickerHelper (
                p_323541_,
                BlockEntityType.VAULT,
                (p_323957_, p_324322_, p_323828_, p_323769_) -> CustomVaultServer.customTick(
                        serverlevel, p_324322_, p_323828_, p_323769_.getConfig(), p_323769_.getServerData(), ((customVault)p_323769_).renewable$getCustomServerData(), p_323769_.getSharedData()
                )
        )
                : createTickerHelper(
                p_323541_,
                BlockEntityType.VAULT,
                (p_324290_, p_323926_, p_323941_, p_323489_) -> VaultBlockEntity.Client.tick(
                        p_324290_, p_323926_, p_323941_, p_323489_.getClientData(), p_323489_.getSharedData()
                )
        );
    }
}

