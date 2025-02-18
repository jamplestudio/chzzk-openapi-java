package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record ChatMessageSendRequest(
        @NotNull String message,
        @NotNull String accessToken
) {
}
