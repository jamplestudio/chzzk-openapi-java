package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.AccessTokenRevokeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AccessTokenRevokeExecutor implements HttpRequestExecutor<AccessTokenRevokeRequest, Void, OkHttpClient> {

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenRevokeRequest request) {
        return Optional.empty();
    }

}
