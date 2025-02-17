package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.LiveSettingsResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveSettingsExecutor implements HttpRequestExecutor<Void, LiveSettingsResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<LiveSettingsResponse> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull Void unused) {
        return Optional.empty();
    }

}
