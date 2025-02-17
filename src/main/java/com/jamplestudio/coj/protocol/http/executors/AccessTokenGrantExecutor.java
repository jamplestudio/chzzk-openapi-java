package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.AccessTokenGrantRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenGrantResponse;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AccessTokenGrantExecutor implements HttpRequestExecutor<AccessTokenGrantRequest, AccessTokenGrantResponse> {

    @Override
    public @NotNull Optional<AccessTokenGrantResponse> execute(
            @NotNull OkHttpClient client, @NotNull AccessTokenGrantRequest request) {
        return Optional.empty();
    }

}
