package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.AccessTokenRevokeRequest;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AccessTokenRevokeExecutor implements HttpRequestExecutor<AccessTokenRevokeRequest, Void, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/auth/v1/token/revoke";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<Void> execute(@NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenRevokeRequest requestInst) {
        RequestBody formBody = new FormBody.Builder()
                .add("clientId", requestInst.clientId())
                .add("clientSecret", requestInst.clientSecret())
                .add("token", requestInst.token())
                .add("tokenTypeHint", requestInst.tokenTypeHint().getAsString())
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        try (Response ignored = client.getNativeHttpClient().newCall(request).execute()) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
