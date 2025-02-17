package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.AccessTokenRevokeRequest;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AccessTokenRevokeExecutor implements HttpRequestExecutor<AccessTokenRevokeRequest, Void> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull OkHttpClient client, @NotNull AccessTokenRevokeRequest request) {
        return Optional.empty();
    }

}
