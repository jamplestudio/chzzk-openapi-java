package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.ChatMessageSendRequest;
import com.jamplestudio.coj.protocol.data.ChatMessageSendResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatMessageSendExecutor implements HttpRequestExecutor<ChatMessageSendRequest, ChatMessageSendResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChatMessageSendResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatMessageSendRequest request) {
        return Optional.empty();
    }

}
