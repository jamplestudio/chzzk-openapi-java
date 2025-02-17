package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.ChatMessageSendRequest;
import com.jamplestudio.coj.protocol.data.ChatMessageSendResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatMessageSendExecutor implements HttpRequestExecutor<ChatMessageSendRequest, ChatMessageSendResponse> {

    @Override
    public @NotNull Optional<ChatMessageSendResponse> execute(
            @NotNull OkHttpClient client, @NotNull ChatMessageSendRequest request) {
        return Optional.empty();
    }

}
