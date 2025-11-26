package top.yuhh.renewables.util;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static java.lang.Math.floor;

public class CustomVaultServerData {
    static final String TAG_NAME = "server_data";
    public static Codec<CustomVaultServerData> CODEC = RecordCodecBuilder.create(
            p_338073_ -> p_338073_.group(
                            Codec.LONG.lenientOptionalFieldOf("last_reset_time", Long.valueOf(0L)).forGetter(data -> data.lastResetTime)
                    )
                    .apply(p_338073_, CustomVaultServerData::new)
    );
    long lastResetTime;

    public CustomVaultServerData(long lastReset) {
        this.lastResetTime = lastReset;
    }

    public CustomVaultServerData() {
    }

    public void set(CustomVaultServerData other) {
        this.lastResetTime = other.lastResetTime;
    }

    public void setLastResetTime(ServerLevel level) {
        System.out.println("new moon");
        long moon = (level.getMoonPhase() + 4) % 8;
        long day = level.getDayTime() / 24000L;
        long lastmoon = day - moon;
        this.lastResetTime = lastmoon;
    }

    public long getLastResetTime() {
        return this.lastResetTime;
    }
}
