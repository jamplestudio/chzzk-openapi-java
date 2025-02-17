package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AccessTokenRefreshRequest(
        @NotNull String refreshToken,
        @NotNull String clientId,
        @NotNull String clientSecret
) {

    public @NotNull String grantType() {
        return "refresh_token";
    }

}
