package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record AuthorizationCodeResponse(
        @NotNull String code,
        @NotNull String state
) {
}
