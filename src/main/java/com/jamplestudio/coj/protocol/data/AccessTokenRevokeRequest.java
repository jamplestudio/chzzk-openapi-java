package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AccessTokenRevokeRequest(
        @NotNull String clientId,
        @NotNull String clientSecret,
        @NotNull String token,
        @NotNull TokenTypeHint tokenTypeHint
) {

    public enum TokenTypeHint {
        ACCESS_TOKEN, REFRESH_TOKEN;

        public @NotNull String getAsString() {
            return this.toString().toLowerCase();
        }

    }

}
