package com.jamplestudio.coj.protocol.http.executor.okhttp;

import com.google.gson.Gson;
import com.jamplestudio.coj.protocol.data.ChatSettingsRequest;
import com.jamplestudio.coj.protocol.data.ChatSettingsResponse;
import com.jamplestudio.coj.protocol.http.client.ChzzkHttpClient;
import com.jamplestudio.coj.protocol.http.executor.HttpRequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class ChatSettingsExecutor implements HttpRequestExecutor<ChatSettingsRequest, ChatSettingsResponse, OkHttpClient> {

    private static final @NotNull String URL = "https://openapi.chzzk.naver.com/open/v1/chats/settings";
    private static final @NotNull Gson GSON = new Gson();

    @Override
    public @NotNull Optional<ChatSettingsResponse> execute(
            @NotNull ChzzkHttpClient<OkHttpClient> client, @NotNull ChatSettingsRequest requestInst) {

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("Authorization", "Bearer " + requestInst.accessToken())
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.getNativeHttpClient().newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                ChatSettingsResponse responseInst = GSON.fromJson(response.body().string(), ChatSettingsResponse.class);
                return Optional.of(responseInst);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}
