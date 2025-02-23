package com.jamplestudio.coj.net.data;

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
