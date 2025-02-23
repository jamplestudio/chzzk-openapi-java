package com.jamplestudio.coj.net.data;

import org.jetbrains.annotations.NotNull;

public record ChatEventUnsubscribeRequest(
        @NotNull String sessionKey,
        @NotNull String accessToken
) {
}
