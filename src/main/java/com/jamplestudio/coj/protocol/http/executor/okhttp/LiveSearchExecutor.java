package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.LiveSearchRequest;
import com.jamplestudio.coj.protocol.data.LiveSearchResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LiveSearchExecutor implements HttpRequestExecutor<LiveSearchRequest, LiveSearchResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<LiveSearchResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveSearchRequest request) {
        return Optional.empty();
    }

}
