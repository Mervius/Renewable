//package top.yuhh.renewables.mixin;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.state.BlockState;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//
//@Mixin(BlockBehaviour.class)
//public class TestMixin {
//    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
//    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
//        if (state.is(Blocks.DIRT)) {
//            System.out.println("hi");
//        }
//    }
//
//    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
//    protected void isRandomlyTicking(BlockState state, CallbackInfoReturnable<Boolean> cir) {
//        if (state.is(Blocks.DIRT)) {
//            cir.setReturnValue(true);
//        }
//    }
//}
