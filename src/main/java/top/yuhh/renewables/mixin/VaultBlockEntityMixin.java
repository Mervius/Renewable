package top.yuhh.renewables.mixin;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.yuhh.renewables.vault.CustomVaultServerData;
import top.yuhh.renewables.vault.customVault;

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
    public CustomVaultServerData renewable$getCustomServerData() {
        return ((BlockEntity) (Object) this).getLevel() != null && !((BlockEntity) (Object) this).getLevel().isClientSide ? this.renewable$customServerData : null;
    }
}
