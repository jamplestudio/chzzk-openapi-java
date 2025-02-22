package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record UserInformationResponse(
        @NotNull String channelId,
        @NotNull String channelName
) {
}
