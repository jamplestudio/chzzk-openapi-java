package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.ChatSettingsChangeRequest;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ChatSettingsChangeExecutor implements HttpRequestExecutor<ChatSettingsChangeRequest, Void> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull OkHttpClient client, @NotNull ChatSettingsChangeRequest request) {
        return Optional.empty();
    }

}
