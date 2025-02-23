package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record SessionSearchRequest(
        @Range(from = 0, to = 50) int size,
        @NotNull String page,
        @NotNull String accessToken
) {
}
