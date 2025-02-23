package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record SessionUrlRequest(
        @NotNull String accessToken
) {
}
