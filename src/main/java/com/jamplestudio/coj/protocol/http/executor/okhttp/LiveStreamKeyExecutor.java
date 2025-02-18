package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.LiveStreamKeyRequest;
import com.jamplestudio.coj.protocol.data.LiveStreamKeyResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class LiveStreamKeyExecutor implements HttpRequestExecutor<LiveStreamKeyRequest, LiveStreamKeyResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/streams/key";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<LiveStreamKeyResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull LiveStreamKeyRequest requestInst) {

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                LiveStreamKeyResponse responseInst = GSON.fromJson(response.body().string(), LiveStreamKeyResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
