package com.jamplestudio.coj.chzzk;

import org.jetbrains.annotations.NotNull;

public interface ChzzkChatMessageSendResult {

    static @NotNull ChzzkChatMessageSendResult of(@NotNull String id, @NotNull String message) {
        return new ChzzkChatMessageSendResultImpl(id, message);
    }

    @NotNull String getId();

    @NotNull String getMessage();

}
