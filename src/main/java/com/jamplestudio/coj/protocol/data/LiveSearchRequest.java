package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record LiveSearchRequest(
        @Range(from = 1, to = 20) int size,
        @NotNull String next
) {
}
