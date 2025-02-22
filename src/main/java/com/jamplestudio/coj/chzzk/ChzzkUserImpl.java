package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkUserImpl implements ChzzkUser {

    private final @NotNull String channelId;
    private final @NotNull String channelName;

    ChzzkUserImpl(@NotNull String channelId, @NotNull String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
    }

}
