package top.yuhh.renewables.mixin;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.vault.*;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.yuhh.renewables.util.CustomVaultServer;
import top.yuhh.renewables.util.CustomVaultServerData;
import top.yuhh.renewables.util.customVault;

//@Mixin(VaultBlockEntity.class)
//public class VaultBlockEntityMixin {
//
//    @Mixin(VaultBlockEntity.Server.class)
//    public static final class Server {
//
//        @Inject(method = "tick", at = @At("HEAD"),cancellable = true)
//        private static void onTick(ServerLevel level, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, CallbackInfo ci) {
//            long moon = (level.getMoonPhase() + 4) % 8;
//            long day = level.getDayTime()/24000L;
//
//        }
//    }
//}


@Mixin(VaultBlockEntity.class)
public class VaultBlockEntityMixin implements customVault {

    @Shadow
    private static final Logger LOGGER = LogUtils.getLogger();


    @Unique
    private final CustomVaultServerData renewable$customServerData = new CustomVaultServerData();


    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void onSaveAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        tag.put("custom_server_data", encode(CustomVaultServerData.CODEC, this.renewable$customServerData, registries));
    }

    @Shadow
    private static <T> Tag encode(Codec<T> codec, T value, HolderLookup.Provider levelRegistry) {
        return codec.encodeStart(levelRegistry.createSerializationContext(NbtOps.INSTANCE), value).getOrThrow();
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    protected void onLoadAdditional(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        DynamicOps<Tag> dynamicops = registries.createSerializationContext(NbtOps.INSTANCE);
        if (tag.contains("custom_server_data")) {
            CustomVaultServerData.CODEC.parse(dynamicops, tag.get("custom_server_data")).resultOrPartial(LOGGER::error).ifPresent(this.renewable$customServerData::set);
        }
    }

    @Override
    public CustomVaultServerData getCustomServerData() {
        return ((BlockEntity) (Object) this).getLevel() != null && !((BlockEntity) (Object) this).getLevel().isClientSide ? this.renewable$customServerData : null;
    }
}
