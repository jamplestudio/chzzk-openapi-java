package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record LiveStreamKeyRequest(
        @NotNull String accessToken
) {
}
