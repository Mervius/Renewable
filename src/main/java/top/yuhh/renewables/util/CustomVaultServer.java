package top.yuhh.renewables.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultConfig;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import net.minecraft.world.level.block.entity.vault.VaultSharedData;
import net.minecraft.world.level.block.state.BlockState;

public final class CustomVaultServer {
    public static void customTick(ServerLevel level, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, CustomVaultServerData customServerData, VaultSharedData sharedData) {
        VaultBlockEntity.Server.tick(level, pos, state, config, serverData, sharedData);
        long moon = ((level.getDayTime() - 18000) / 24000L % 8L + 4L) % 8;
        long day = (level.getDayTime() - 18000) / 24000L;
        if ((day - moon) != customServerData.getLastResetTime()) {
            customServerData.setLastResetTime(level);
            ((resetPlayers)serverData).renewable$resetPlayersMethod();
        }
    }
}