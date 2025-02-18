package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.ChannelInformationRequest;
import com.jamplestudio.coj.protocol.data.ChannelInformationResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChannelInformationExecutor implements HttpRequestExecutor<ChannelInformationRequest, ChannelInformationResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/channels";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<ChannelInformationResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChannelInformationRequest requestInst) {

        HttpUrl.Builder builder = HttpUrl.get(URL).newBuilder();
        requestInst.channelIds().forEach(id -> builder.addQueryParameter("channelIds", id));

        HttpUrl url = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                ChannelInformationResponse responseInst = GSON.fromJson(response.body().string(), ChannelInformationResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
