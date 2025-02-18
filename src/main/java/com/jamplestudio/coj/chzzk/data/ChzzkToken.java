package com.jamplestudio.coj.chzzk.data;

import org.jetbrains.annotations.NotNull;

public record ChzzkToken(
        @NotNull String accessToken,
        @NotNull String refreshToken
) {
}
