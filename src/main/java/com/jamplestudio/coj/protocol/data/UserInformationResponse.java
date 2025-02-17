package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record UserInformationResponse(
        @NotNull String channelId,
        @NotNull String channelName
) {
}
