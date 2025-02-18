package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.AccessTokenRefreshRequest;
import com.jamplestudio.coj.protocol.data.AccessTokenRefreshResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AccessTokenRefreshExecutor implements HttpRequestExecutor<AccessTokenRefreshRequest, AccessTokenRefreshResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/auth/v1/token";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<AccessTokenRefreshResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AccessTokenRefreshRequest requestInst) {
        RequestBody formBody = new FormBody.Builder()
                .add("grantType", requestInst.grantType())
                .add("refreshToken", requestInst.refreshToken())
                .add("clientId", requestInst.clientId())
                .add("clientSecret", requestInst.clientSecret())
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                AccessTokenRefreshResponse responseInst = GSON.fromJson(response.body().string(), AccessTokenRefreshResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
