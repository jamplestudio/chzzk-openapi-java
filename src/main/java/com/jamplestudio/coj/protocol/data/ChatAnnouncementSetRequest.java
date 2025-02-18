package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record ChatAnnouncementSetRequest(
        @NotNull String message,
        @NotNull String messageId,
        @NotNull String accessToken
) {
}
