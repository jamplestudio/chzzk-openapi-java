package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkLiveStreamKeyImpl implements ChzzkLiveStreamKey {

    private final @NotNull String streamKey;

    ChzzkLiveStreamKeyImpl(@NotNull String streamKey) {
        this.streamKey = streamKey;
    }

}
