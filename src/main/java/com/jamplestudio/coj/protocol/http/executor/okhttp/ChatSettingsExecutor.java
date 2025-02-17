package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.ChatSettingsResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatSettingsExecutor implements HttpRequestExecutor<Void, ChatSettingsResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<ChatSettingsResponse> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull Void unused) {
        return Optional.empty();
    }

}
