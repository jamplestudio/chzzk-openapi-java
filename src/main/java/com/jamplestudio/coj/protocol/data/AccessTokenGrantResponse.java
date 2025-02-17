package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AccessTokenGrantResponse(
        @NotNull String accessToken,
        @NotNull String refreshToken,
        @NotNull String tokenType,
        @NotNull String expiresIn
) {
}
