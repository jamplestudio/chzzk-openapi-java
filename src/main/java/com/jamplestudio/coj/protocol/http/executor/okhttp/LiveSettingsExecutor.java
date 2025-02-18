package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.LiveSettingsRequest;
import com.jamplestudio.coj.protocol.data.LiveSettingsResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class LiveSettingsExecutor implements HttpRequestExecutor<LiveSettingsRequest, LiveSettingsResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/lives/setting";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<LiveSettingsResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveSettingsRequest requestInst) {

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                LiveSettingsResponse responseInst = GSON.fromJson(response.body().string(), LiveSettingsResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
