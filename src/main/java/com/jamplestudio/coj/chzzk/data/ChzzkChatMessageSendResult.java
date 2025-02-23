package com.jamplestudio.coj.chzzk.data;

import org.jetbrains.annotations.NotNull;

public record ChzzkChatMessageSendResult(
        @NotNull String id,
        @NotNull String message
) {

    public static @NotNull ChzzkChatMessageSendResult of(@NotNull String id, @NotNull String message) {
        return new ChzzkChatMessageSendResult(id, message);
    }

}
