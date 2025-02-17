package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.ChatSettingsChangeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatSettingsChangeExecutor implements HttpRequestExecutor<ChatSettingsChangeRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatSettingsChangeRequest request) {
        return Optional.empty();
    }

}
