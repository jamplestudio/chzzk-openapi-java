package com.jamplestudio.coj.protocol.http.executors;

import com.jamplestudio.coj.protocol.data.AuthorizationCodeRequest;
import com.jamplestudio.coj.protocol.data.AuthorizationCodeResponse;
import com.jamplestudio.coj.protocol.http.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AuthorizationCodeExecutor implements HttpRequestExecutor<AuthorizationCodeRequest, AuthorizationCodeResponse> {

    @Override
    public @NotNull Optional<AuthorizationCodeResponse> execute(
            @NotNull OkHttpClient client, @NotNull AuthorizationCodeRequest request) {
        return Optional.empty();
    }

}
