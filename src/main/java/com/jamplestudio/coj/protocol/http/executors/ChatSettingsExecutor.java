package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.ChatSettingsResponse;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatSettingsExecutor implements HttpRequestExecutor<Void, ChatSettingsResponse> {

    @Override
    public @NotNull Optional<ChatSettingsResponse> execute(@NotNull OkHttpClient client, @NotNull Void unused) {
        return Optional.empty();
    }

}
