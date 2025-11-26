package top.yuhh.renewables.mixin;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import top.yuhh.renewables.vault.resetPlayers;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public abstract class VaultServerDataMixin implements resetPlayers {
    
    @Shadow
    private final Set<UUID> rewardedPlayers = new ObjectLinkedOpenHashSet<>();
    
    @Override
    public void renewable$resetPlayersMethod() {
        rewardedPlayers.clear();
    }
    
}

