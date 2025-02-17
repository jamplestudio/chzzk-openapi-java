package com.jamplestudio.coj.protocol.data;

import org.jetbrains.annotations.NotNull;

public record ChatMessageSendResponse(
        @NotNull String messageId
) {
}
