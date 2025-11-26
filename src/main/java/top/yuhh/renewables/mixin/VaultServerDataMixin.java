package top.yuhh.renewables.mixin;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import top.yuhh.renewables.util.CustomVaultServer;
import top.yuhh.renewables.util.customVault;
import top.yuhh.renewables.util.resetPlayers;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public abstract class VaultServerDataMixin implements resetPlayers {
    
    @Shadow
    public final Set<UUID> rewardedPlayers = new ObjectLinkedOpenHashSet<>();
    
    @Override
    public void resetPlayersMethod() {
        rewardedPlayers.clear();
    }
    
}

