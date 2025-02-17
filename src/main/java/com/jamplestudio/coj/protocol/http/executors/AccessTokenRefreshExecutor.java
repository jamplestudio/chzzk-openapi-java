package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.AccessTokenRefreshRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenRefreshResponse;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AccessTokenRefreshExecutor implements HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse> {

    @Override
    public @NotNull Optional<AccessTokenRefreshResponse> execute(
            @NotNull OkHttpClient client, @NotNull AccessTokenRefreshRequest request) {
        return Optional.empty();
    }

}
