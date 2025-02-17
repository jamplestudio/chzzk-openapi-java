package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.LiveStreamKeyResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveStreamKeyExecutor implements HttpRequestExecutor<Void, LiveStreamKeyResponse> {

    @Override
    public @NotNull Optional<LiveStreamKeyResponse> execute(@NotNull OkHttpClient client, @NotNull Void unused) {
        return Optional.empty();
    }

}
