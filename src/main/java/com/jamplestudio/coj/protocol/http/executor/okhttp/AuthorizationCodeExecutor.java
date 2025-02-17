package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.jamplestudio.coj.protocol.data.AuthorizationCodeRequest;
import com.jamplestudio.coj.protocol.data.AuthorizationCodeResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AuthorizationCodeExecutor implements HttpRequestExecutor<AuthorizationCodeRequest, AuthorizationCodeResponse, OkHttpClient> {

    @Override
    public @NotNull Optional<AuthorizationCodeResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AuthorizationCodeRequest request) {
        return Optional.empty();
    }

}
