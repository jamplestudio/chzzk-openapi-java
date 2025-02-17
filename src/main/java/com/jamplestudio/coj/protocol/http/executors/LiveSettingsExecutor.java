package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.LiveSettingsResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveSettingsExecutor implements HttpRequestExecutor<Void, LiveSettingsResponse> {

    @Override
    public @NotNull Optional<LiveSettingsResponse> execute(@NotNull OkHttpClient client, @NotNull Void unused) {
        return Optional.empty();
    }

}
