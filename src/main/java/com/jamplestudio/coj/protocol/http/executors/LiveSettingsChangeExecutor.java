package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.LiveSettingsChangeRequest;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveSettingsChangeExecutor implements HttpRequestExecutor<LiveSettingsChangeRequest, Void> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull OkHttpClient client, @NotNull LiveSettingsChangeRequest request) {
        return Optional.empty();
    }

}
