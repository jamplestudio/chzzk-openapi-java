package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record CategorySearchRequest(
        @Range(from = 1, to = 50) int size,
        @NotNull String query,
        @NotNull String accessToken
) {
}
