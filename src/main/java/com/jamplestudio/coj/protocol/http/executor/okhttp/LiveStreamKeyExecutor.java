package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.LiveStreamKeyResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveStreamKeyExecutor implements HttpRequestExecutor<Void, LiveStreamKeyResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<LiveStreamKeyResponse> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull Void unused) {
        return Optional.empty();
    }

}
