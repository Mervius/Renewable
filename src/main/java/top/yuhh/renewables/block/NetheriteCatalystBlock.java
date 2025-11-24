package top.yuhh.renewables.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class NetheriteCatalystBlock extends Block {
    public static final MapCodec<NetheriteCatalystBlock> CODEC = simpleCodec(NetheriteCatalystBlock::new);

    @Override
    public @NotNull MapCodec<NetheriteCatalystBlock> codec() { return CODEC; }

    public NetheriteCatalystBlock(Properties properties) {
        super(properties);
    }
}
