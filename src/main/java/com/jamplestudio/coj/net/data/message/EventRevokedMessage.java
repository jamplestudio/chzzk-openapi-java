package com.jamplestudio.coj.net.data.message;

import org.jetbrains.annotations.NotNull;

public record EventRevokedMessage(
        @NotNull String type,
        @NotNull Data data
) {

    public record Data(
            @NotNull String eventType,
            @NotNull String channelId
    ) {}

}
