package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.LiveSearchRequest;
import com.jamplestudio.coj.protocol.data.LiveSearchResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveSearchExecutor implements HttpRequestExecutor<LiveSearchRequest, LiveSearchResponse> {

    @Override
    public @NotNull Optional<LiveSearchResponse> execute(
            @NotNull OkHttpClient client, @NotNull LiveSearchRequest request) {
        return Optional.empty();
    }

}
