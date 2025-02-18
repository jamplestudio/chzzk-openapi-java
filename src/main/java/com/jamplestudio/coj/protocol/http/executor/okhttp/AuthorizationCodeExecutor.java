package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.AuthorizationCodeRequest;
import com.jamplestudio.coj.protocol.data.AuthorizationCodeResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class AuthorizationCodeExecutor implements HttpRequestExecutor<AuthorizationCodeRequest, AuthorizationCodeResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://chzzk.naver.com/account-interlock";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<AuthorizationCodeResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull AuthorizationCodeRequest requestInst) {

        HttpUrl url = HttpUrl.get(URL).newBuilder()
                .addQueryParameter("clientId", requestInst.clientId())
                .addQueryParameter("redirectUri", requestInst.redirectUri())
                .addQueryParameter("state", requestInst.state())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                AuthorizationCodeResponse responseInst = GSON.fromJson(response.body().string(), AuthorizationCodeResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
