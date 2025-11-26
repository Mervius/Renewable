package top.yuhh.renewables.vault;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;

public class CustomVaultServerData {
    public static Codec<CustomVaultServerData> CODEC = RecordCodecBuilder.create(
            p_338073_ -> p_338073_.group(
                            Codec.LONG.lenientOptionalFieldOf("last_reset_time", 0L).forGetter(data -> data.lastResetTime)
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
        long moon = (level.getMoonPhase() + 4) % 8;
        long day = level.getDayTime() / 24000L;
        this.lastResetTime = day - moon;
    }

    public long getLastResetTime() {
        return this.lastResetTime;
    }
}
