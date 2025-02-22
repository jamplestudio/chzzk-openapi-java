package com.jamplestudio.coj.chzzk;

import com.jamplestudio.coj.protocol.data.LiveStreamKeyResponse;
import org.jetbrains.annotations.NotNull;

public interface ChzzkLiveStreamKey {

    static @NotNull ChzzkLiveStreamKey of(@NotNull LiveStreamKeyResponse response) {
        return of(response.streamKey());
    }

    static @NotNull ChzzkLiveStreamKey of(@NotNull String key) {
        return new ChzzkLiveStreamKeyImpl(key);
    }

    @NotNull String getStreamKey();

}
