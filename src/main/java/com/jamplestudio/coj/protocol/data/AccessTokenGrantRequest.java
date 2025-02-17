package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AccessTokenGrantRequest(
        @NotNull String clientId,
        @NotNull String clientSecret,
        @NotNull String code,
        @NotNull String state
) {

    public @NotNull String grantType() {
        return "authorization_code";
    }

}
