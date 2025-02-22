package com.jamplestudio.coj.chzzk;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ChzzkChatMessageSendResultImpl implements ChzzkChatMessageSendResult {

    private final @NotNull String id;
    private final @NotNull String message;

    ChzzkChatMessageSendResultImpl(@NotNull String id, @NotNull String message) {
        this.id = id;
        this.message = message;
    }

}
