package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AuthorizationCodeRequest(
        @NotNull String clientId,
        @NotNull String redirectUri,
        @NotNull String state
) {
}
