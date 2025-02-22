package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record AuthorizationCodeResponse(
        @NotNull String code,
        @NotNull String state
) {
}
